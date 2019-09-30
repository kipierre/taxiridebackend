package biz.advance_it_group.taxiride_backend.profiles.entities;

import biz.advance_it_group.taxiride_backend.commons.entities.MainEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Date;

/**
 * Cette classe représente un code promotionnel enregistré sur le système par un administrateur de la plateforme.
 * Ce code sera diffusé par différents canaux et chaque utilisateur en sa possession pour en ajouter à son profil.
 * @author Simon Ngang
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PromotionalCode extends MainEntity {

    @Id
    @Column(name = "PROMOTIONAL_CODE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Column(unique = true)
    private String codeKey; // Code aléatoire généré lors de la création du code promotionnel ( 10 caractères alphanumériques)

    @Temporal(TemporalType.DATE)
    private Date startDate;  // Date de début de validité du code

    @Temporal(TemporalType.DATE)
    private Date endDate;  // Date de fin de validité du code

    private Double coast;  // Coût du code

    // Méthode permettant de vérifier si le code promotionnel est expiré ou pas
    public Boolean hasExpired(){
        return new Date().compareTo(endDate) > 0; // Retourner vrai si la date actuelle est plus grande
    }


}
