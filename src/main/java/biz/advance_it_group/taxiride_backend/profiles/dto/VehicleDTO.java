package biz.advance_it_group.taxiride_backend.profiles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {

    private Long id; // L'identifiant du véhicule

    private String phoneNumber; // Le numéro de téléphone du propriétaire du véhicule
    private Long userId; // L'identifiant du propriétaire

    @Column(length = 50)
    private String vehicleType;
    private Integer numberOfSeat; // Nombre de places assises dans le véhicule
    private String brand;
    private String model;
    private String matriculationNumber; // Numéro d'imatriculation du véhicule

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date firstUseDate;

    @Column(length = 10)
    private String color; // Code de couleur en hexadecimal (#ffbf00 par exemple qui représente la couleur jaune du taxi)

    private Integer numbersWheel; // Le nombre de roues du véhicule

    private String description;
}
