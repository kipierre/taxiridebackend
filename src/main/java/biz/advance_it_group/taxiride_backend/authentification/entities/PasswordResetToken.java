package biz.advance_it_group.taxiride_backend.authentification.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.time.Instant;

/**
 * Cette classe sauvegarde le code de réinitialisation du mot de passe sur le système TaxiRide.
 * @author Simon Ngang
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "PASSWORD_RESET_TOKEN")
public class PasswordResetToken {

    @Id
    @Column(name = "TOKEN_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @NaturalId
    @Column(name = "TOKEN_NAME", nullable = false, unique = true)
    private String token;

    @Column(name = "EXPIRY_DT", nullable = false)
    private Instant expiryDate;

    @OneToOne(targetEntity = Users.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "USER_ID")
    private Users user;

}