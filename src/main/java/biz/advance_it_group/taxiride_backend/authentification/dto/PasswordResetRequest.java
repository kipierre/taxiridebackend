package biz.advance_it_group.taxiride_backend.authentification.dto;

import biz.advance_it_group.taxiride_backend.authentification.annotations.MatchPassword;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MatchPassword
@ApiModel(value = "Password reset Request", description = "The password reset request dto")
public class PasswordResetRequest {

	@NotBlank(message = "Password cannot be blank")
	@ApiModelProperty(value = "New user password", required = true, allowableValues = "NonEmpty String")
	private String password;

	@NotBlank(message = "Confirm Password cannot be blank")
	@ApiModelProperty(value = "Must match the new user password. Else exception will be thrown", required = true,
			allowableValues = "NonEmpty String matching the password")
	private String confirmPassword;

	@NotBlank(message = "Token has to be supplied along with a password reset request")
	@ApiModelProperty(value = "Reset token received in mail", required = true, allowableValues = "NonEmpty String")
	private String token;

}
