package biz.advance_it_group.taxiride_backend.authentification.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Cette classe représente un rôle que possède un utilisateur sur la plateforme TaxiRide.
 * @author KITIO
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Roles implements Serializable {

    @Id
    @Column(name = "ROLE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "ROLE_NAME")
    private String role;

    private String roleNameFr;
    private String roleNameEn;
    private String roleDescriptionFr;
    private String roleDescriptionEn;


}