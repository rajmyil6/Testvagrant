package Scripts;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.Test;

import Generic_Library.Basefunctions;
import Page_Factory.pf_api;
import Page_Factory.pf_ndtvhomepage;

public class API_Script extends Basefunctions {
	final static Logger log = Logger.getLogger(API_Script.class);

	@Test(enabled=true,groups={"SMK","REG"})
	public void apiscript() throws Exception{	
		PropertyConfigurator.configure(System.getProperty("user.dir") + "\\src\\main\\resources\\log4j.properties");
		//login

		
		et =  es.startTest("Login");
		log.info("this is login information");
		
		
		 pf_api pa = new pf_api(w);
		
		
		pa.apirest();
		log.info("Logged in successfully");
		

	}

}
