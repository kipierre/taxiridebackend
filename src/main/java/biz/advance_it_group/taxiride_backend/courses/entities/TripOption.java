package biz.advance_it_group.taxiride_backend.courses.entities;

import biz.advance_it_group.taxiride_backend.commons.entities.MainEntity;
import biz.advance_it_group.taxiride_backend.geolocalisation.entities.City;
import biz.advance_it_group.taxiride_backend.geolocalisation.entities.Country;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Cette classe représente une option de course que l'on définit sur la plateforme TaxiRide.
 * @author KITIO
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TripOption extends MainEntity {

    @Id
    @Column(name = "TRIP_OPTION_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String optionCode;
    private String optionName;
    private Double baseFare;
    private Double minuteRate;
    private Double kilometerRate;
    private String optionDescription;
    private Long estimatedWaitingTime;
    private String tripFeeFormula;
    private String cancelFeeFormula;
    private String reservationFeeFormula;
    private Double tripCommission;

    @Column(length = 100)
    private String mapCabImageMimeType;
    private String mapCabImageUrl;

    @OneToOne(targetEntity = Country.class, fetch = FetchType.LAZY)
    private Country country;

    @ManyToOne(targetEntity = City.class, fetch = FetchType.LAZY)
    private City city;

}
