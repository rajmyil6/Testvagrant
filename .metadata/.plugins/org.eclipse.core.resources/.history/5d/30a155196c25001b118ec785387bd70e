package Page_Factory;

import java.text.DecimalFormat;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class pf_comparator extends pf_genericmethods {
	
	 pf_ndtvhomepage n = new pf_ndtvhomepage(w);
	 pf_api ap = new pf_api(w);
	final static Logger log = Logger.getLogger(pf_comparator.class);
	
	public pf_comparator(WebDriver driver){

		PageFactory.initElements(driver, this);
	}



	public void compare() throws Exception{
		
		
		
		
		
		
		RestAssured.baseURI="http://api.openweathermap.org";
		
//		Request Object
		RequestSpecification rs = RestAssured.given();
		
//		Response Object
		Response re = rs.request(Method.GET, "/data/2.5/weather?q=Bengaluru&appid=7fe67bf08c80ded756e598d6f8fedaea");
		
		String b = re.getBody().asString();
		System.out.println("Response :"+b );
		
		String temperature = re.jsonPath().getString("main").substring(25,30);
		System.out.println("Temperature: "+temperature);
		Double d = Double.parseDouble(temperature);
		double tem = (d - 273.15);
		DecimalFormat df = new DecimalFormat("#.##");
		String apitemperature = df.format(tem).toString();
		System.out.println("Temperature from API: "+apitemperature);
		
		
	}

}