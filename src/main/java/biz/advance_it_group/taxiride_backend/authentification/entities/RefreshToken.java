package biz.advance_it_group.taxiride_backend.authentification.entities;

import biz.advance_it_group.taxiride_backend.commons.entities.DateAudit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.time.Instant;

/**
 * Cette classe le code de rafraichissement de la session de connexion sur la plateforme TaxiRide.
 * @author KITIO
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "REFRESH_TOKEN")
public class RefreshToken extends DateAudit {

	@Id
	@Column(name = "TOKEN_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "TOKEN", nullable = false, unique = true)
	@NaturalId(mutable = true)
	private String token;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "USER_ID", nullable = false)

	private Users user;

	@Column(name = "REFRESH_COUNT")
	private Long refreshCount;

	@Column(name = "EXPIRY_DT", nullable = false)
	private Instant expiryDate;

	public void incrementRefreshCount() {
		refreshCount = refreshCount + 1;
	}


}
