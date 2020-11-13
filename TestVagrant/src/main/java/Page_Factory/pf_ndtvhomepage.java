package Page_Factory;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.sql.Driver;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.relevantcodes.extentreports.LogStatus;

import Generic_Library.Basefunctions;
import Generic_Library.Utility;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class pf_ndtvhomepage extends pf_genericmethods{
	Basefunctions b = new Basefunctions();
	pf_api pa = new pf_api(w);
	
//	Submenu Elements
	
	@FindBy(how = How.ID, using = "h_sub_menu") WebElement submenu;
	@FindAll({@FindBy(how = How.XPATH, using = "//*[@id=\"subnav\"]/div/div/div/div/div/a")}) public List<WebElement> submenulist;
	String submen = "//*[@id=\"subnav\"]/div/div/div/div/div/a[#DELIM#]";
	@FindBy(how = How.CLASS_NAME, using = "noti_wrap") WebElement notification;
	@FindBy(how = How.CLASS_NAME, using = "notnow") WebElement nothanks;
	
//	Weather page elements
	
	@FindBy(how = How.ID, using = "searchBox") WebElement searchbox;
	@FindBy(how = How.XPATH, using = "//*[@id=\"messages\"]/div[1]/label") WebElement citylabels;
	@FindAll({@FindBy(how = How.XPATH, using = "//*[@id=\"map_canvas\"]/div[1]/div[4]/div")}) public List<WebElement> mapcitylist;
	String mapcity = "//*[@id=\"map_canvas\"]/div[1]/div[4]/div[#DELIM#]/div/div[2]";
	String temp = "//*[@id=\"map_canvas\"]/div[1]/div[4]/div[#DELIM#]/div/div[1]/span[1]";
	@FindBy(how = How.XPATH, using = "//*[@id=\"map_canvas\"]/div[1]/div[4]/div[12]/div/div[1]/span[1]") WebElement citytemp;
	@FindAll({@FindBy(how = How.XPATH, using = "//div[@id='more']/div/div[3]/div")}) public List<WebElement> nameofcitylist;
	String city = "//div[@id='more']/div/div[3]/div[#DELIM#]";
	String cname ="//*[@for=#DELIM#]/input";

	String ct = null;
	String UI_city_temp;
	String apitemperature;
	
	//	Initializing the Pagefactory

	public pf_ndtvhomepage(WebDriver driver){

		PageFactory.initElements(driver, this);
	}

	public void ndtv(WebDriver driver, String submenuname,String searchinput) throws Exception{
		String ciname = null;
		WebElement check = null;

		int mcl = 0;
		Thread.sleep(5000);
		//	Alert notification check

		if (notification.isDisplayed()) {
			cl_click(nothanks);
			Thread.sleep(3000);
		}

		//		Submenu Clicking event 

		cl_click(submenu);
		Thread.sleep(2000);
		int sml = submenulist.size();
		for(int i=1;i<=sml;i++) {
			String menuname = driver.findElement(By.xpath(submen.replace("#DELIM#",String.valueOf(i)))).getText();
			if(submenuname.equalsIgnoreCase(menuname)) {
				cl_click(driver.findElement(By.xpath(submen.replace("#DELIM#",String.valueOf(i)))));
				break;
			}
		}

		//		Searchbox input by user

		cl_click(searchbox);
		cl_entertext(searchbox, searchinput);
		Robot r = new Robot();
		r.keyPress((KeyEvent.VK_ENTER));
		Thread.sleep(3000);

		//		Getting the total list of Cities

		int size = nameofcitylist.size();
		System.out.println("Total Cities listed: "+size);

		//		Checking the city name & comparing with input

		for(int k=1;k<=size;k++) {
			String nam = driver.findElement(By.xpath(city.replace("#DELIM#",String.valueOf(k)))).getText();
			nam = nam.replaceAll(" ","");
			if(nam.equalsIgnoreCase(searchinput)) {

				String si = "'"+searchinput+"'";
				try {
					check = driver.findElement(By.xpath(cname.replace("#DELIM#",String.valueOf(si))));
					ciname = check.getAttribute("checked");
					mcl = mapcitylist.size();
					if (!(ciname.isEmpty())) {
						Thread.sleep(3000);		

						//		Calling the method to get the temperature

						UI_city_temp=mapcity(driver, searchinput);	
					}

					//					If city is not selected by default, then selecting the city

				}catch(Exception e) {
					cl_click(check);
					Thread.sleep(3000);	
					UI_city_temp=mapcity(driver, searchinput);
				}
			}
		} 
	}

	//	Method for getting the temperature from NDTV application

	public String mapcity(WebDriver driver,String searchinput) {
		int mcl1 = mapcitylist.size();
		for(int m=1;m<=mcl1;m++) {
			String mcname1 = driver.findElement(By.xpath(mapcity.replace("#DELIM#",String.valueOf(m)))).getText();
			System.out.println("City in Map updated: "+mcname1);
			if(mcname1.equalsIgnoreCase(searchinput)) {
				ct = driver.findElement(By.xpath(temp.replace("#DELIM#",String.valueOf(m)))).getText().substring(0,2);
				System.out.println(searchinput + " City Temperature is "+ct +" Degree Celsius");
				break;
			}

		}return ct;
	}

	//	Method for getting the temperature from API

	public String apirest() throws Exception{

		RestAssured.baseURI="http://api.openweathermap.org";

		//		Request Object
		RequestSpecification rs = RestAssured.given();

		//		Response Object
		Response re = rs.request(Method.GET, "/data/2.5/weather?q=Bengaluru&appid=7fe67bf08c80ded756e598d6f8fedaea");

		String b = re.getBody().asString();
		System.out.println("Response :"+b );

		String temperature = re.jsonPath().getString("main").substring(59,65);
		System.out.println("Temperature: "+temperature);
		Double d = Double.parseDouble(temperature);
		double tem = (d - 273.15);
		DecimalFormat df = new DecimalFormat("#.##");
		apitemperature = df.format(tem).toString();
		System.out.println("Temperature from API: "+apitemperature);
		return apitemperature;

	}

	//Method for comparing the temperatures from web application and API service

	public void comparator() throws Exception {

		System.out.println("API temp: "+apitemperature);
		if(UI_city_temp.equals(apitemperature)) {
			System.out.println("Tempertaure of Web application and API are same");
		}else {
			System.out.println("Tempertaure of Web application and API are different");
		}
	}
}