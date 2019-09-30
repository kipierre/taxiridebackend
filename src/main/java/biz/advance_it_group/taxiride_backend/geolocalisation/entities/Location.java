package biz.advance_it_group.taxiride_backend.geolocalisation.entities;

import biz.advance_it_group.taxiride_backend.commons.entities.MainEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Cette classe représente un point de geolocalisation sur le système TaxiRide.
 * @author KITIO
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Location extends MainEntity {

    @Id
    @Column(name = "LOCATION_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String locationName;
    private Double longitude;
    private Double latitude;
    private Double altitude;
    private Double delayPickupTime;

}
