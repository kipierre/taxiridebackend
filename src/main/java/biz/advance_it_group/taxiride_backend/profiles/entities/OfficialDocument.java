package biz.advance_it_group.taxiride_backend.profiles.entities;

import biz.advance_it_group.taxiride_backend.authentification.entities.Users;
import biz.advance_it_group.taxiride_backend.commons.entities.MainEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Cette classe représente un document officiel que l'on exige à un chauffeur sur la plateforme TaxiRide
 * afin de pouvoir opérer en toute légalité.
 * @author KITIO
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OfficialDocument extends MainEntity {

    @Id
    @Column(name = "DOCUMENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url; // le nom à utiliser pour rechercher le document dans le répertoire de stockage

    private Integer number; // 1 = pièce d'identité, 2 = permis de conduire, 3 = carte grise, 4 = assurance, 5 = visite technique, 6 = carte professionnelle

    @Column(length = 100)
    private String mimeType; // Type mime du fichier (image/jpg, application/pdf, ...)


    @ManyToOne(targetEntity = Users.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "USER_ID")
    private Users user; // Le propriétaire du document

    public OfficialDocument(Users user, Integer number, String url, String mimeType) {
       this.user = user;
       this.number = number;
       this.url = url;
       this.mimeType = mimeType;
    }
}
