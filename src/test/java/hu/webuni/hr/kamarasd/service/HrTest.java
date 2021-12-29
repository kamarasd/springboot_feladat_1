package hu.webuni.hr.kamarasd.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import hu.webuni.hr.kamarasd.web.EmployeeController;

@ExtendWith(MockitoExtension.class)
public class HrTest {
	
	@InjectMocks
	EmployeeService employeeService;
	
	@Mock
	EmployeeController employeeController;
	
	
	@Test
	public void testPutEmployee() throws Exception {
		
	}

}
