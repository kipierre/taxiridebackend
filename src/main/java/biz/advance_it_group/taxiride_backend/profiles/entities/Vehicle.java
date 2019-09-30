package biz.advance_it_group.taxiride_backend.profiles.entities;

import biz.advance_it_group.taxiride_backend.authentification.entities.Users;
import biz.advance_it_group.taxiride_backend.commons.entities.MainEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Date;


/**
 * Cette classe représente le véhicule qu'un chauffeur enregistre sur la plateforme TaxiRide.
 * Ce véhicule est celui utilisé par le chauffeur pour effectuer les courses.
 * @author KITIO
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Vehicle extends MainEntity {

    @Id
    @Column(name = "VEHICLE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String vehicleType;

    private Integer numberOfSeat; // Nombre de places assises dans le véhicule

    private String brand;
    private String model;

    @NaturalId
    @Column(unique = true, length = 100)
    private String matriculationNumber; // Numéro d'imatriculation du véhicule

    @Temporal(TemporalType.DATE)
    private Date firstUseDate;

    @Column(length = 10)
    private String color; // Code de couleur en hexadecimal (#ffbf00 par exemple qui représente la couleur jaune du taxi)

    private Integer numbersWheel; // Le nombre de roues du véhicule

    private String description;

    @OneToOne(targetEntity = Users.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "USER_ID")
    private Users user; // Le propriétaire du véhicule


}
