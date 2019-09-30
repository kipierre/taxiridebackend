package biz.advance_it_group.taxiride_backend.profiles.entities;

import biz.advance_it_group.taxiride_backend.commons.entities.MainEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Cette classe représente un type de mode de paiement qu'un administrateur ajoute à la plateforme TaxiRide.
 * C'est à partir de ces type de mode de paiement que les utilisateurs de la plateforme enregistrent leurs
 * différents moyens de paiement.
 * @author KITIO
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PaymentMode extends MainEntity {

    @Id
    @Column(name = "PAYMENT_MODE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Integer paymentType; // 0 = MOBILE MONEY, 1 = CASH, 2 = WALLET, 3 = CREDIT CARD

    private String nameFr;
    private String nameEn;

    private String descriptionFr;
    private String descriptionEn;

}
