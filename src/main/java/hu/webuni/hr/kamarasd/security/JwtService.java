package hu.webuni.hr.kamarasd.security;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import hu.webuni.hr.kamarasd.config.HrConfigProperties;
import hu.webuni.hr.kamarasd.model.Employee;
import hu.webuni.hr.kamarasd.model.HrUser;
import hu.webuni.hr.kamarasd.repository.EmployeeRepository;

@Service
public class JwtService {
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	HrConfigProperties hrConfProp;

	private Algorithm alg;
	private String issuer;
	private String auth;
	private Integer logInMinutes;
	
	@PostConstruct
	public void init() {
		issuer = hrConfProp.gethrConf().getIssuer();
		alg = (Algorithm) Algorithm.HMAC256(hrConfProp.gethrConf().getSecret());
		auth = hrConfProp.gethrConf().getAuth();
		logInMinutes = hrConfProp.gethrConf().getMinutesLoggedIn();
	}
	
	private static final String SUP_EMP_NAME = "superiored_user_full_name";
	private static final String SUP_EMP_ID = "superiored_user_id";
	
	private static final String EMP_NAME = "user_full_name";
	private static final String EMP_USER = "username";
	private static final String EMP_ID = "user_id";

	public String createJwtToken(UserDetails principal) {
		Builder jwt = JWT.create().withSubject(principal.getUsername())
		.withArrayClaim(auth, principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new));
		
		Employee employee = ((HrUser) principal).getEmployee();
		if(employee.getPosition().getPosName().contentEquals("Superior")) {
			List<Employee> superioredEmployees = employeeRepository.findBySuperior(employee.getName());
			if(!superioredEmployees.isEmpty()) {
				jwt.withArrayClaim(SUP_EMP_ID, superioredEmployees.stream().map(Employee::getEmployeeId).toArray(Long[]::new))
				.withArrayClaim(SUP_EMP_NAME, superioredEmployees.stream().map(Employee::getName).toArray(String[]::new));
			}
		}
		
		jwt.withClaim(EMP_ID, employee.getEmployeeId());
		jwt.withClaim(EMP_USER, employee.getUsername());
		jwt.withClaim(EMP_NAME, employee.getName());
		
		return jwt.withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(logInMinutes))).withIssuer(issuer)
				.sign(alg);
		
	}
	
	public UserDetails parseJwt(String jwtToken) {
		DecodedJWT decodedJwt = JWT.require(alg)
		.withIssuer(issuer)
		.build()
		.verify(jwtToken);
		
		return new User(decodedJwt.getSubject(), "dumdum", 
				decodedJwt.getClaim(auth)
				.asList(String.class)
				.stream()
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList()));
	}
}
