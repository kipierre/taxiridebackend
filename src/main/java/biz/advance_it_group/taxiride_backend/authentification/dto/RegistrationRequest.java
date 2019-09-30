package biz.advance_it_group.taxiride_backend.authentification.dto;

import biz.advance_it_group.taxiride_backend.authentification.annotations.NullOrNotBlank;
import biz.advance_it_group.taxiride_backend.authentification.annotations.ValidEmail;
import biz.advance_it_group.taxiride_backend.authentification.annotations.ValidPassword;
import biz.advance_it_group.taxiride_backend.authentification.enums.AuthProvider;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Registration Request", description = "The registration request dto")
public class RegistrationRequest {

	@NullOrNotBlank(message = "Registration phone number can be null but not blank")
	@ApiModelProperty(value = "A valid phone number", allowableValues = "NonEmpty String")
	private String phoneNumber;

	@ValidEmail
	@ApiModelProperty(value = "A valid email", required = true, allowableValues = "NonEmpty String")
	private String email;

	@ValidPassword
	@ApiModelProperty(value = "A valid password string", required = true, allowableValues = "NonEmpty String")
	private String password;

	private AuthProvider provider ;

}
