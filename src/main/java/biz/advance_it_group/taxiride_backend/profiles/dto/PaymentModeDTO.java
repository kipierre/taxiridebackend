package biz.advance_it_group.taxiride_backend.profiles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentModeDTO {

    private Long id;
    private Integer paymentType; // 0 = MOBILE MONEY, 1 = CASH, 2 = WALLET, 3 = CREDIT CARD

    private String nameFr;
    private String nameEn;

    private String descriptionFr;
    private String descriptionEn;

}
