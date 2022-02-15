package hu.webuni.hr.kamarasd.config;

import org.springframework.stereotype.Component;

import com.auth0.jwt.algorithms.Algorithm;

@Component
public class LoginConfig {
	
	private String auth;
	private String issuer;
	private Algorithm alg;
	private Integer minutesLoggedIn;
	
	public LoginConfig() {
		this.auth = "auth";
		this.issuer = "HrApp";
		this.alg = Algorithm.HMAC256("mySecret");
		this.minutesLoggedIn = 2;
	}
	
	public Integer getMinutesLoggedIn() {
		return minutesLoggedIn;
	}
	
	public void setMinutesLoggedIn(Integer minutesLoggedIn) {
		this.minutesLoggedIn = minutesLoggedIn;
	}
	
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	
	public String getIssuer() {
		return issuer;
	}
	
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	
	public Algorithm getAlg() {
		return alg;
	}
	
	public void setAlg(Algorithm alg) {
		this.alg = alg;
	}
	
	
	

}
