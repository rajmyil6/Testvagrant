package Scripts;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.Test;

import Generic_Library.Basefunctions;
import Page_Factory.pf_api;
import Page_Factory.pf_ndtvhomepage;

public class NDTV_Script extends Basefunctions {
	final static Logger log = Logger.getLogger(NDTV_Script.class);

	@Test(dataProvider= "Valid_data",dataProviderClass=Dataproviders.dp_login.class,enabled=true,priority=1,groups={"SMK","REG"})
	public void Login(Map hm) throws Exception{	
		PropertyConfigurator.configure(System.getProperty("user.dir") + "\\src\\main\\resources\\log4j.properties");
		//Application Initiation and loading

		tcid = hm.get("TC_ID").toString();
		order=hm.get("Order").toString();
		String submenu=hm.get("Submenuname").toString();

		et =  es.startTest("Application loading");
		log.info("this is application loading information");
		String input = hm.get("Searchinput").toString();
		log.info("Submenuname picked from Excel is "+submenu);
		log.info("Searchinput picked from Excel is "+input);
		pf_ndtvhomepage pl = new pf_ndtvhomepage(w);
		pl.ndtv(w, submenu, input);
		pl.apirest();
		pl.comparator();
		log.info("Loaded in successfully");
	}

}
