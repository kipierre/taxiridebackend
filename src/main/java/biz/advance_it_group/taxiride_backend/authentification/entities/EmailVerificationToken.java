package biz.advance_it_group.taxiride_backend.authentification.entities;

import biz.advance_it_group.taxiride_backend.authentification.enums.TokenStatus;
import biz.advance_it_group.taxiride_backend.commons.entities.DateAudit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

/**
 * Cette classe sauvegarde le code de vérification de l'adresse mail sur le système TaxiRide.
 * @author KITIO
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "EMAIL_VERIFICATION_TOKEN")
public class EmailVerificationToken extends DateAudit {

	@Id
	@Column(name = "TOKEN_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "email_token_seq")
	@SequenceGenerator(name = "email_token_seq", allocationSize = 1)
	private Long id;

	@Column(name = "TOKEN", nullable = false, unique = true)
	private String token;

	@OneToOne(targetEntity = Users.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "USER_ID")
	private Users user;

	@Column(name = "TOKEN_STATUS")
	@Enumerated(EnumType.STRING)
	private TokenStatus tokenStatus;

	@Column(name = "EXPIRY_DT", nullable = false)
	private Instant expiryDate;

	public void confirmStatus() {
		setTokenStatus(TokenStatus.STATUS_CONFIRMED);
	}


}
