package biz.advance_it_group.taxiride_backend.courses.entities;

import biz.advance_it_group.taxiride_backend.commons.entities.MainEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Cette classe représente la photo d'une option de course sur la plateforme TaxiRide.
 * @author KTIO
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TripOptionPicture extends MainEntity {

    @Id
    @Column(name = "TRIP_OPTION_PICTURE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer pictureNumber;

    @Column(length = 100)
    private String pictureMimeType;

    private String pictureURL;

    @OneToOne(targetEntity = TripOption.class, fetch = FetchType.LAZY)
    private TripOption tripOption;

}
