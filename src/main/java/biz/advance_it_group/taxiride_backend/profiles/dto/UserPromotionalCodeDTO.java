package biz.advance_it_group.taxiride_backend.profiles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPromotionalCodeDTO {

    private Long id;
    private String phoneNumber; // L'utilisateur qui enregistre le code promotionnel sur son profil
    private Long userId; // L'identifiant du propriétaire
    private String codeKey; // La code promotionnel associé
    private Boolean promotionalCodeUsed; // Vérifie si le code promotionnel est déjà utilisé ou pas
}
