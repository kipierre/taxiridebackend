package biz.advance_it_group.taxiride_backend.accounts.entities;

import biz.advance_it_group.taxiride_backend.commons.entities.MainEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Cette classe représente le compte interne de la plateforme TaxiRide.
 * @author ktio
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SystemAccount extends MainEntity {

    @Id
    @Column(name = "SYSTEM_ACCOUNT_ID")
    private Long id;

    private String accountName;
    private Double amount;

}
