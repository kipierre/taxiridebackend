package biz.advance_it_group.taxiride_backend.accounts.entities;

import biz.advance_it_group.taxiride_backend.commons.entities.MainEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Cette classe représente une opération que l'on effectue sur un compte disponible sur TaxiRide.
 * @author kitio
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AccountOperation extends MainEntity {

    @Id
    @Column(name = "ACCOUNT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountName;
    private Double amount;
    private Boolean operationType;
    private String operationDescription;


}
