package biz.advance_it_group.taxiride_backend.profiles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {

    private Long id;
    private String firstName;
    private String lastName;

    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Column(length = 30)
    private String phoneNumber;
    private Long userId; // L'identifiant du propriétaire
    private String profession;
    private String language;
    private String Country;
    private Character gender;
    private String email;
    private String referalCode;
    private Boolean referalCodeUsed;
    private Double minimalNotificationDistance;
    private Boolean subscribeToSMS;
    private Boolean subscribeToEmail;
    private Boolean subscribeToPush;
    private String address;
    private String city;
    private String driverOperatingCountryCode;
    private String driverOperatingCityCode;
    private String codeTripOption;
    private Integer status; // 0 = PENDING, 1 = VALIDATED, 2 = REJECTED
    private String externalReferalCode; // Code de parainnage


}
