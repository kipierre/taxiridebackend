package biz.advance_it_group.taxiride_backend.geolocalisation.entities;

import biz.advance_it_group.taxiride_backend.commons.entities.MainEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Cette classe représente une ville dans laquelle les activités de TaxiRide ont lieu.
 * @author KITIO
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class City extends MainEntity {

    @Id
    @Column(name = "CITY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cityName;
    private Double signalScope;
    private Double notificationDistance;
    private Double notificationTime;
    private Double tripCancellationTime;

}
