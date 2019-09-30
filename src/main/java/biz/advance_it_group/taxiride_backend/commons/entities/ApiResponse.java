package biz.advance_it_group.taxiride_backend.commons.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Réponse générique renvoyée à un utilisateur suite à un appel d'API sur la plateforme TaxiRide.
 * @author KITIO
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {

	private String data;
	private Boolean success;

}
