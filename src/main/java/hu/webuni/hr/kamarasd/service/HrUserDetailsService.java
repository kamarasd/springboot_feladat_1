package hu.webuni.hr.kamarasd.service;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hu.webuni.hr.kamarasd.model.Employee;
import hu.webuni.hr.kamarasd.model.HrUser;
import hu.webuni.hr.kamarasd.repository.EmployeeRepository;

@Service
public class HrUserDetailsService implements UserDetailsService {
	
	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Employee employee = employeeRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
		
		return new HrUser(username, employee.getPassword(), Arrays.asList(new SimpleGrantedAuthority("USER")), employee);
		
		
	}

}
