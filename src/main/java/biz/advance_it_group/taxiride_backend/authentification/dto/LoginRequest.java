package biz.advance_it_group.taxiride_backend.authentification.dto;

import biz.advance_it_group.taxiride_backend.authentification.annotations.NullOrNotBlank;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Login Request", description = "The login request dto")
public class LoginRequest {

	@NullOrNotBlank(message = "Login Username can be null but not blank")
	@ApiModelProperty(value = "Registered username", allowableValues = "NonEmpty String", allowEmptyValue = false)
	private String username;

	@ApiModelProperty(value = "Valid user password", required = true, allowableValues = "NonEmpty String")
	private String password;

}
