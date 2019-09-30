package biz.advance_it_group.taxiride_backend.notifications.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;


@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Token {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	private String token;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date = new Date();


	public Token() {
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
