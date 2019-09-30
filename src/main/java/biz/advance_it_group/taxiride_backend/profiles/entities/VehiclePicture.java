package biz.advance_it_group.taxiride_backend.profiles.entities;

import biz.advance_it_group.taxiride_backend.commons.entities.MainEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

/**
 * Cette classe représente la photo du véhicule qu'un chauffeur enregistre sur la plateforme TaxiRide.
 * @author KITIO
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class VehiclePicture extends MainEntity {

    @Id
    @Column(name = "VEHICLE_PICTURE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer pictureNumber; // De 1 à 6

    @Column (length = 100)
    private String  pictureMimeType;

    @NaturalId
    @Column(unique = true)
    private String pictureURL;

    @OneToOne(targetEntity = Vehicle.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "VEHICLE_ID")
    private Vehicle vehicle; // Le véhicule possédant l'image en question

    public VehiclePicture(Vehicle vehicle, Integer pictureNumber, String pictureURL, String pictureMimeType) {
        this.vehicle = vehicle;
        this.pictureNumber = pictureNumber;
        this.pictureURL = pictureURL;
        this.pictureMimeType = pictureMimeType;
    }
}
