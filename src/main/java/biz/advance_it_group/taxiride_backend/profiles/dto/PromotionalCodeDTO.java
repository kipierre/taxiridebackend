package biz.advance_it_group.taxiride_backend.profiles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionalCodeDTO {

    private Long id;
    private String codeKey; // Code aléatoire généré lors de la création du code promotionnel ( 10 caractères alphanumériques)
    private Date startDate;  // Date de début de validité du code
    private Date endDate;  // Date de fin de validité du code
    private Double coast;  // Coût du code
}
