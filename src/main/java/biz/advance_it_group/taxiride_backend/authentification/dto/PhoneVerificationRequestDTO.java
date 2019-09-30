package biz.advance_it_group.taxiride_backend.authentification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneVerificationRequestDTO {
    private String phoneNumber;
    private String code;
}
