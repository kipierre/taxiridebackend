package biz.advance_it_group.taxiride_backend.profiles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehiclePictureDTO {

    private Long id;
    private Long vehicleId; // Le véhicule possédant l'image en question
    private String matriculationNumber; // Le numéro d'immatriculation du véhicule
    private Integer pictureNumber;
    private String  pictureMimeType;
    private String pictureURL;

}
