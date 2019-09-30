package biz.advance_it_group.taxiride_backend.courses.entities;

import biz.advance_it_group.taxiride_backend.authentification.entities.Users;
import biz.advance_it_group.taxiride_backend.commons.entities.MainEntity;
import biz.advance_it_group.taxiride_backend.geolocalisation.entities.City;
import biz.advance_it_group.taxiride_backend.geolocalisation.entities.Country;
import biz.advance_it_group.taxiride_backend.profiles.entities.PaymentMode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


/**
 * Cette classe représente une course effectuée sur la plateforme TaxiRide.
 * @author KITIO
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Trip extends MainEntity {

    @Id
    @Column(name = "TRIP_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Rider's informations
    @OneToOne(targetEntity = Users.class, fetch = FetchType.LAZY)
    private Users rider;

    private String riderFirstName;
    private String riderLastName;
    private Character riderGenderCode;

    @Column(length = 100)
    private String riderEmail;

    @Column(length = 30)
    private String riderPhone;

    @Temporal(TemporalType.DATE)
    private Date riderBirthDate;

    private String riderAddress;
    private Double notificationDistance;

    @Column(length = 2)
    private String riderLangCode;

    private Long riderLongitude;
    private Long riderLatitude;


    // Driver's informations

    // Rider's informations
    @OneToOne(targetEntity = Users.class, fetch = FetchType.LAZY)
    private Users driver;

    private String driverFirstName;
    private String driverLastName;
    private Character driverGenderCode;

    @Column(length = 100)
    private String driverEmail;

    @Column(length = 30)
    private String driverPhone;

    @Temporal(TemporalType.DATE)
    private Date driverBirthDate;

    private String driverAddress;
    private String driverProfession;

    @Column(length = 2)
    private String driverLangCode;
    private Long driverStartLongitude;
    private Long driverStartLattitude;
    private String driverStartAddress;

    @Temporal(TemporalType.TIMESTAMP)
    private Date driverStartDate;

    private Long pickupLongitude;
    private Long pickupLatitude;
    private String pickupAddress;

    @Temporal(TemporalType.TIMESTAMP)
    private Date pickupArrivalDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date pickupLeaveDate;

    private Long arrivalLongitude;
    private Long arrivalLatitude;
    private String arrivalAddress;

    @Temporal(TemporalType.TIMESTAMP)
    private Date arrivalDate;

    private Double tripCost;
    private Double estimatedTripDistance;
    private Long estimatedTripDuration;
    private String tripCostCalculFormula;
    private String tripCancelCostCalculFormula;
    private String tripReservationCostCalculFormula;

    // Informations sur le mode de paiement
    @OneToOne(targetEntity = PaymentMode.class, fetch = FetchType.LAZY)
    private PaymentMode paymentMode;

    private String payementModeName;
    private String payementModeValue;
    private Integer paymentStatus; // 0 = PAID, 1 = PENDING, 2 = CANCELED, 3 = FAILED


    // L'option de course
    @OneToOne(targetEntity = TripOption.class)
    private TripOption tripOption;

    private String tripOptionCode;
    private String tripOptionName;
    private Double tripOptionBaseFare;
    private Double tripOptionMinuteRate;
    private Double tripOptionKilometerRate;
    private Long tripOptionEstimatedWaitingTime;
    private String tripOptionFeeFormula;
    private String tripOptionCancelFeeFormula;
    private String tripOptionReservationFeeFormula;
    private Double tripOptionCommission;


    // Informations sur le pays
    @OneToOne(targetEntity = Country.class, fetch = FetchType.LAZY)
    private Country country;


    // Informations sur la ville
    @OneToOne(targetEntity = City.class, fetch = FetchType.LAZY)
    private City city;

}
