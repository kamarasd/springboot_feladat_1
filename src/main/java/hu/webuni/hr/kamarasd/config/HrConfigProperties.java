package hu.webuni.hr.kamarasd.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.auth0.jwt.algorithms.Algorithm;

@ConfigurationProperties(prefix = "hr")
@Component
public class HrConfigProperties {

	private hrConfigData hrConf = new hrConfigData();
	
	public hrConfigData gethrConf() {
		return hrConf;
	}

	public void sethrConf(hrConfigData hrhrConf) {
		this.hrConf = hrConf;
	}

	public static class hrConfigData {
		
		private String auth;
		private String issuer;
		private String alg;
		private String secret;
		private Integer minutesLoggedIn;
		
		public String getSecret() {
			return secret;
		}

		public void setSecret(String secret) {
			this.secret = secret;
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
		
		public String getAlg() {
			return alg;
		}
		
		public void setAlg(String alg) {
			this.alg = alg;
		}
		
		public Integer getMinutesLoggedIn() {
			return minutesLoggedIn;
		}
		
		public void setMinutesLoggedIn(Integer minutesLoggedIn) {
			this.minutesLoggedIn = minutesLoggedIn;
		}
	}
}
