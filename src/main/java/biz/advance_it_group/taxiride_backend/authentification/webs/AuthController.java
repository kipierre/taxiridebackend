package biz.advance_it_group.taxiride_backend.authentification.webs;

import biz.advance_it_group.taxiride_backend.authentification.annotations.CurrentUser;
import biz.advance_it_group.taxiride_backend.authentification.dto.*;
import biz.advance_it_group.taxiride_backend.authentification.entities.EmailVerificationToken;
import biz.advance_it_group.taxiride_backend.authentification.entities.PasswordResetToken;
import biz.advance_it_group.taxiride_backend.authentification.entities.RefreshToken;
import biz.advance_it_group.taxiride_backend.authentification.entities.Users;
import biz.advance_it_group.taxiride_backend.authentification.events.OnGenerateResetLinkEvent;
import biz.advance_it_group.taxiride_backend.authentification.events.OnRegenerateEmailVerificationEvent;
import biz.advance_it_group.taxiride_backend.authentification.events.OnUserAccountChangeEvent;
import biz.advance_it_group.taxiride_backend.authentification.events.OnUserRegistrationCompleteEvent;
import biz.advance_it_group.taxiride_backend.authentification.exceptions.*;
import biz.advance_it_group.taxiride_backend.authentification.securities.JwtTokenProvider;
import biz.advance_it_group.taxiride_backend.authentification.securities.oauth2.user.CustomUserDetails;
import biz.advance_it_group.taxiride_backend.authentification.services.impl.AuthService;
import biz.advance_it_group.taxiride_backend.authentification.services.interfaces.UserService;
import biz.advance_it_group.taxiride_backend.commons.entities.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@Api(value = "Authentication Rest API", description = "Définition des API d'authentification.")

public class AuthController {

	@Autowired
    AuthenticationManager authenticationManager;

	@Autowired
	private AuthService authService;

	@Autowired
	private UserService userService;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Value("${app.client.reset.password.path}")
	private String clientResetPasswordPath;


	@ApiOperation(value = "Vérifie si une adresse mail est déjà utilisée.")
	@GetMapping("/checkEmailInUse")
	public ResponseEntity<?> checkEmailInUse(@ApiParam(value = "Email id to check against") @RequestParam("email") String email) {
		Boolean emailExists = authService.emailAlreadyExists(email);
		return ResponseEntity.ok(new ApiResponse(emailExists.toString(), true));
	}

	@ApiOperation(value = "Vérifie que le login (adresse mail ou numéro de téléphone) n'est pas encore utilisé.")
	@GetMapping("/checkUsernameInUse")
	public ResponseEntity<?> checkUsernameInUse(@ApiParam(value = "Username to check against") @RequestParam(
			"username") String username) {
		Boolean usernameExists = authService.emailAlreadyExists(username) || authService.phoneAlreadyExists(username);
		return ResponseEntity.ok(new ApiResponse(usernameExists.toString(), true));
	}

	@ApiOperation(value = "Connecte l'utilisateur sur le système et retrourne les informations sur la connexion y compris le token et les paramètres de base de l'utilisateur.")
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@ApiParam(value = "The LoginRequest dto") @Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request) {

		Optional<Authentication> authenticationOpt = authService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
		authenticationOpt.orElseThrow(() -> new UserLoginException("Couldn't login user [" + loginRequest + "]"));
		Authentication authentication = authenticationOpt.get();
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

		SecurityContextHolder.getContext().setAuthentication(authentication);
		Optional<RefreshToken> refreshTokenOpt = authService.createAndPersistRefreshTokenUser(authentication,customUserDetails);

		refreshTokenOpt.orElseThrow(() -> new UserLoginException("Couldn't create refresh token for: [" + loginRequest + "]"));
		String refreshToken = refreshTokenOpt.map(RefreshToken::getToken).get();
		String jwtToken = authService.generateToken(customUserDetails);

		return ResponseEntity.ok(new JwtAuthenticationResponse(jwtToken, refreshToken,
				tokenProvider.getExpiryDuration(), customUserDetails.getUsername(), customUserDetails.getAuthorities()));
	}

	@ApiOperation(value = "Enregistre un utilisateur et déclenche l'évènement d'envoi des codes de vérification par mail et par téléphone.")
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@ApiParam(value = "The RegistrationRequest dto") @Valid @RequestBody RegistrationRequest registrationRequest) {

		Optional<Users> registeredUserOpt = authService.registerUser(registrationRequest);

		// Génération et envoi du code d'activation au mail de l'utilisateur au cas où le mail est fourni
		registeredUserOpt.orElseThrow(() -> new UserRegistrationException(registrationRequest.getEmail(),
				"Missing user object in database"));
		UriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/auth" +
				"/registerconfirmationbyemail");

		// Déclenchement de l'évènement d'envoi des codes de vérification par mail et par téléphone
		OnUserRegistrationCompleteEvent onUserRegistrationCompleteEvent =
				new OnUserRegistrationCompleteEvent(registeredUserOpt.get(), urlBuilder);
		applicationEventPublisher.publishEvent(onUserRegistrationCompleteEvent);

		return ResponseEntity.ok(new ApiResponse("Users registered successfully. Check your email" +
				" for verification", true));
	}


	@ApiOperation(value = "Génère le lien de réinitialisation du mot de passe et déclenche l'évènement de l'envoi de mail contenant le lien " +
			"de réinitialisation du mot de passe.")
	@PostMapping("/password/resetlink")
	public ResponseEntity<?> resetLink(@ApiParam(value = "The PasswordResetLinkRequest dto") @Valid @RequestBody PasswordResetLinkRequest passwordResetLinkRequest, HttpServletRequest request) {

		// Récupérer l'url d'origine avec laquelle construire le lien de réinitialisation du mot de passe
		String clientUrl = request.getHeader("origin");

		Optional<PasswordResetToken> passwordResetTokenOpt = authService
				.generatePasswordResetToken(passwordResetLinkRequest);
		passwordResetTokenOpt.orElseThrow(() -> new PasswordResetLinkException(passwordResetLinkRequest.getEmail(),
				"Couldn't create a valid token"));
		PasswordResetToken passwordResetToken = passwordResetTokenOpt.get();
		UriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromHttpUrl(clientUrl + clientResetPasswordPath);
		OnGenerateResetLinkEvent generateResetLinkMailEvent = new OnGenerateResetLinkEvent(passwordResetToken,
				urlBuilder);
		applicationEventPublisher.publishEvent(generateResetLinkMailEvent);
		return ResponseEntity.ok(new ApiResponse("Password reset link sent successfully", true));
	}

	@ApiOperation(value = "Reçois un code d'initialisation du mot de passe, réinitialise le mot de passe puis déclenche l'envoi" +
			"de mail de notifications de mise à jour du mot de passe.")
	@PostMapping("/password/reset")
	public ResponseEntity<?> resetPassword(@ApiParam(value = "The PasswordResetRequest dto") @Valid @RequestBody PasswordResetRequest passwordResetRequest) {
		Optional<Users> userOpt = authService.resetPassword(passwordResetRequest);
		userOpt.orElseThrow(() -> new PasswordResetException(passwordResetRequest.getToken(), "Error in resetting " +
				"password"));
		Users changedUser = userOpt.get();
		OnUserAccountChangeEvent onPasswordChangeEvent = new OnUserAccountChangeEvent(changedUser, "Reset Password",
				"Changed Successfully");
		applicationEventPublisher.publishEvent(onPasswordChangeEvent);
		return ResponseEntity.ok(new ApiResponse("Password changed successfully", true));
	}


	@ApiOperation(value = "Permet à un utilisateur de modifier son mot passe une fois connecté.")
	@PostMapping("/password/update")
	public ResponseEntity<?> updateUserPassword(@CurrentUser CustomUserDetails customUserDetails,
												@ApiParam(value = "The UpdatePasswordRequest dto") @Valid @RequestBody UpdatePasswordRequest updatePasswordRequest) {
		Users updatedUser = authService.updatePassword(customUserDetails, updatePasswordRequest)
				.orElseThrow(() -> new UpdatePasswordException("--Empty--", "No such user present."));

		OnUserAccountChangeEvent onUserPasswordChangeEvent =
				new OnUserAccountChangeEvent(updatedUser, "Update Password", "Change successful");
		applicationEventPublisher.publishEvent(onUserPasswordChangeEvent);

		return ResponseEntity.ok(new ApiResponse("Password changed successfully", true));
	}


	@ApiOperation(value = "Déconnecte l'utilisateur connecté en supprimant le refresh token associé à sa connexion.")
	@PostMapping("/logout")
	public ResponseEntity<?> logoutUser(@CurrentUser CustomUserDetails customUserDetails, long id) {
		userService.logoutUser(customUserDetails, id);
		return ResponseEntity.ok(new ApiResponse("Log out successful", true));
	}

	@ApiOperation(value = "Permet à l'utilisateur de vérifier son adresse mail à partir d'un code qui a été généré à cet effet.")
	@GetMapping("/registerconfirmationbyemail")
	public ResponseEntity<?> confirmRegistration(@ApiParam(value = "the token that was sent to the user email") @RequestParam("token") String token) {
		Optional<Users> verifiedUserOpt = authService.confirmEmailRegistration(token);
		verifiedUserOpt.orElseThrow(() -> new InvalidTokenRequestException("Email Verification Token", token,
				"Failed to confirm. Please generate a new email verification request"));
		return ResponseEntity.ok(new ApiResponse("Email verified successfully", true));
	}

	@ApiOperation(value = "Permet à l'utilisateur de vérifier son mot de passe grâce à un code qui a été généré à cet effet.")
	@PostMapping("/registerconfirmationbyphone")
	public ResponseEntity<?> registerConfirmationByPhone(@Valid @RequestBody PhoneVerificationRequestDTO phoneVerificationRequestDTO) {
		Optional<Users> verifiedUserOpt = authService.confirmPhoneRegistration(phoneVerificationRequestDTO.getCode());
		verifiedUserOpt.orElseThrow(() -> new InvalidTokenRequestException("Phone Verification Token", phoneVerificationRequestDTO.getCode(),
				"Failed to confirm. Please generate a new email verification request"));
		return ResponseEntity.ok(new ApiResponse("Phone number verified successfully", true));
	}

	@ApiOperation(value = "Regénération du token d'activation de vérification du mail.")
	@GetMapping("/resendRegistrationToken")
	public ResponseEntity<?> resendRegistrationToken(@ApiParam(value = "the initial token that was sent to the user " +
			" email after registration") @RequestParam("token") String existingToken) {
		Optional<EmailVerificationToken> newEmailTokenOpt = authService.recreateRegistrationToken(existingToken);
		newEmailTokenOpt.orElseThrow(() -> new InvalidTokenRequestException("Email Verification Token", existingToken,
				"Users is already registered. No need to re-generate token"));

		Users registeredUser = newEmailTokenOpt.map(EmailVerificationToken::getUser)
				.orElseThrow(() -> new InvalidTokenRequestException("Email Verification Token", existingToken,
						"No user associated with this request. Re-verification denied"));

		UriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/api/auth" + "/registrationConfirmation");
		OnRegenerateEmailVerificationEvent regenerateEmailVerificationEvent = new OnRegenerateEmailVerificationEvent(
				registeredUser, urlBuilder, newEmailTokenOpt.get());
		applicationEventPublisher.publishEvent(regenerateEmailVerificationEvent);

		return ResponseEntity.ok(new ApiResponse("Email verification resent successfully", true));
	}


	@ApiOperation(value = "Regénération du token en se servant du token de rafraissement.")
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshJwtToken(@ApiParam(value = "The TokenRefreshRequest dto") @Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) {
		Optional<String> updatedJwtToken = authService.refreshJwtToken(tokenRefreshRequest);
		updatedJwtToken.orElseThrow(() -> new TokenRefreshException(tokenRefreshRequest.getRefreshToken(),
				"Unexpected error during token refresh. Please logout and login again."));
		String refreshToken = tokenRefreshRequest.getRefreshToken();

		return ResponseEntity.ok(new JwtAuthenticationResponse(updatedJwtToken.get(), refreshToken,
				tokenProvider.getExpiryDuration()));
	}
}
