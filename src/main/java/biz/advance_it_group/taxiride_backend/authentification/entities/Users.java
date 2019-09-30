package biz.advance_it_group.taxiride_backend.authentification.entities;

import biz.advance_it_group.taxiride_backend.authentification.enums.AuthProvider;
import biz.advance_it_group.taxiride_backend.commons.entities.MainEntity;
import biz.advance_it_group.taxiride_backend.notifications.entities.Token;
import biz.advance_it_group.taxiride_backend.profiles.entities.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Cette classe représente un utilisateur (RIDER, DRIVER, ADMIN) sur la plateforme TaxiRide.
 * @author KITIO
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Users extends MainEntity {

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Column(unique = true)
    @NotBlank(message = "Users email cannot be null")
    private String email;

    @JsonIgnore
    private String password;
    private String firstName;
    private String lastName;
    private Boolean active;

    // Ces variables sont utilisés dans les réseaux sociaux
    private String name;
    private String imageUrl;
    private String providerId;

    @OneToOne(cascade = CascadeType.ALL)
    private Token tokenFCM;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "USERS_ROLES", joinColumns = {
            @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID") }, inverseJoinColumns = {
            @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID") })
    private List<Roles> roles = new ArrayList<>();

    private Boolean isEmailVerified;
    private Boolean isPhoneVerified; // Permet de vérifier le numéro de téléphone de l'utilisateur
    private Boolean isVerified; // Permet de vérifier qu'un utilisateur a été vérifié administrativement pour opérer

    // Les autres propriétés liées au profil de l'utilisateur
    @NaturalId
    @Column(unique = true, length = 30)
    @NotBlank(message = "Users phone number cannot be null")
    private String phoneNumber;

    @Temporal(TemporalType.DATE)
    private Date birthDate;
    private String profession;
    private String address;

    private String language;
    private String Country;
    private String city;
    private String driverOperatingCountryCode;
    private String driverOperatingCityCode;
    private String codeTripOption;
    private Double internalAccountAmount;
    private Character gender;
    private String referalCode;
    private Boolean referalCodeUsed;
    private Double minimalNotificationDistance;
    private Boolean subscribeToSMS;
    private Boolean subscribeToEmail;
    private Boolean subscribeToPush;
    private Integer status; // 0 = PENDING, 1 = VALIDATED, 2 = REJECTED
    private String externalReferalCode; // Code de parainnage
    private String profilePictureMimeType; // Type Mime de la photo de profil

    @JoinColumn(nullable = false, name = "USER_ID")
    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "user")
    private RefreshToken refreshToken;
    private Boolean isRefreshActive;

    // Les modes de paiement d'un utilisateur
    @JsonIgnore
    @OneToMany(targetEntity = UserPaymentMode.class, mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserPaymentMode> userPaymentModes = new ArrayList<>();

    // Les contacts d'urgence d'un utilisateur
    @JsonIgnore
    @OneToMany(targetEntity = EmergencyContact.class, mappedBy = "user", fetch = FetchType.LAZY)
    private List<EmergencyContact> emergencyContacts = new ArrayList<>();

    // Les codes promotionnels d'un utilisateur
    @JsonIgnore
    @OneToMany(targetEntity = UserPromotionalCode.class, mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserPromotionalCode> userPromotionalCodes = new ArrayList<>();

    // Les documents officiels d'un utilisateur
    @JsonIgnore
    @OneToMany(targetEntity = OfficialDocument.class, mappedBy = "user", fetch = FetchType.LAZY)
    private List<OfficialDocument> officialDocuments = new ArrayList<>();

    private Integer defaultPaymentmode;

    public Boolean getRefreshActive() {
        return isRefreshActive;
    }

    public void setRefreshActive(Boolean refreshActive) {
        isRefreshActive = refreshActive;
    }

    public RefreshToken getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(RefreshToken refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void confirmEmailVerification() {
        setIsEmailVerified(true);
    }

    public void confirmPhoneVerification() {
        setIsPhoneVerified(true);
    }

    public void setUser(Users user) {
        id = user.getId();
        password = user.getPassword();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        email = user.getEmail();
        active = user.getActive();
        roles = user.getRoles();
    }

    public Users(String email, @NotNull(message = "Password cannot be null")
            String password, String firstName, String lastName, Boolean active, List<Roles> roles,
                 Boolean isEmailVerified, RefreshToken refreshToken, Boolean isRefreshActive) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.active = active;
        this.roles = roles;
        this.isEmailVerified = isEmailVerified;
        this.refreshToken = refreshToken;
        this.isRefreshActive = isRefreshActive;
    }

    // Gestion de l'ajout et du retrait des rôles d'un utilisateur
    public void addRole(Roles role) {
        getRoles().add(role);
    }
    public void removeRole(Roles role) {
        getRoles().remove(role);
    }


}
