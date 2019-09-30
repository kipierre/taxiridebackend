package biz.advance_it_group.taxiride_backend.authentification.dto;

import biz.advanceitgroup.taxirideserver.authentification.annotations.ValidEmail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Password reset link request", description = "The password reset link dto")
public class PasswordResetLinkRequest {
    @ValidEmail
	@NotBlank(message = "Email cannot be blank")
	@ApiModelProperty(value = "Users registered email", required = true, allowableValues = "NonEmpty String")
	private String email;

}
