package biz.advance_it_group.taxiride_backend.profiles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfficialDocumentDTO {

    private Long id;
    private String phoneNumber; // Le propriétaire du document
    private Long userId; // L'identifiant du propriétaire du document
    private String url; // le nom à utiliser pour rechercher le document dans le répertoire de stockage
    private Integer number; // 1 = pièce d'identité, 2 = permis de conduire, 3 = carte grise, 4 = assurance, 5 = visite technique, 6 = carte professionnelle
    private String mimeType; // Type mime du fichier (image/jpg, application/pdf, ...)
}
