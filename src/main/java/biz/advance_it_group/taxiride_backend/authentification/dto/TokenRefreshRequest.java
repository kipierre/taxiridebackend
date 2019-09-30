package biz.advance_it_group.taxiride_backend.authentification.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Token refresh Request", description = "The jwt token refresh request dto")
public class TokenRefreshRequest {

	@NotBlank(message = "Refresh token cannot be blank")
	@ApiModelProperty(value = "Valid refresh token passed during earlier successful authentications", required = true,
			allowableValues = "NonEmpty String")
	private String refreshToken;

}
