package biz.advance_it_group.taxiride_backend.profiles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReferalCodeDTO {
    private String phoneNumber;
    private Long userId; // L'identifiant du propriétaire
    private String referalCode;
}
