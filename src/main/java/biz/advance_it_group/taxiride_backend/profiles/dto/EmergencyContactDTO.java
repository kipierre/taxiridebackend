package biz.advance_it_group.taxiride_backend.profiles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmergencyContactDTO {

    private Long id; // Identifiant du contact
    private String phoneNumber;
    private Long userId; // L'identifiant du propriétaire du contact
    private String name; // Nom du contact
    private String phone; // Le numéro de téléphone du contact
    private String contactType; // 0 = Emmergency, 1= Confidence

}
