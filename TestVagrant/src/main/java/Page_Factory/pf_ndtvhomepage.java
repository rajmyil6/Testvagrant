package Page_Factory;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.sql.Driver;
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

public class pf_ndtvhomepage extends pf_genericmethods{
	Basefunctions b = new Basefunctions();

	@FindBy(how = How.ID, using = "h_sub_menu") WebElement submenu;
	@FindAll({@FindBy(how = How.XPATH, using = "//*[@id=\"subnav\"]/div/div/div/div/div/a")}) public List<WebElement> submenulist;
	String submen = "//*[@id=\"subnav\"]/div/div/div/div/div/a[#DELIM#]";
	@FindBy(how = How.ID, using = "searchBox") WebElement searchbox;
	@FindBy(how = How.XPATH, using = "//*[@id=\"messages\"]/div[1]/label") WebElement citylabels;
	//	@FindBy(how = How.XPATH, using = "//*[@for=#DELIM#]/input") WebElement cname;
	@FindAll({@FindBy(how = How.XPATH, using = "//*[@id=\"map_canvas\"]/div[1]/div[4]/div")}) public List<WebElement> mapcitylist;
	String mapcity = "//*[@id=\"map_canvas\"]/div[1]/div[4]/div[#DELIM#]/div/div[2]";
	String temp = "//*[@id=\"map_canvas\"]/div[1]/div[4]/div[#DELIM#]/div/div[1]/span[1]";

	@FindBy(how = How.XPATH, using = "//*[@id=\"map_canvas\"]/div[1]/div[4]/div[12]/div/div[1]/span[1]") WebElement citytemp;
	@FindBy(how = How.CLASS_NAME, using = "noti_wrap") WebElement notification;
	@FindBy(how = How.CLASS_NAME, using = "notnow") WebElement nothanks;
	@FindAll({@FindBy(how = How.XPATH, using = "//div[@id='more']/div/div[3]/div")}) public List<WebElement> nameofcitylist;
	String city = "//div[@id='more']/div/div[3]/div[#DELIM#]";
	String cname ="//*[@for=#DELIM#]/input";


	//@FindBy(how = How .XPATH, using = "//div[@id='Bengaluru']") WebElement city;
	//	Initializing the Pagefactory

	public pf_ndtvhomepage(WebDriver driver){

		PageFactory.initElements(driver, this);
	}

	public String ndtv(WebDriver driver, String submenuname,String searchinput) throws Exception{
		String ciname = null;
		WebElement check = null;
		String ct = null;
		int mcl = 0;
		Thread.sleep(5000);
		//	Work flows list check
		if (notification.isDisplayed()) {
			cl_click(nothanks);
			Thread.sleep(3000);
		}
		cl_click(submenu);
		Thread.sleep(2000);
		int sml = submenulist.size();
//		System.out.println("Submenulist is "+sml);
		for(int i=1;i<=sml;i++) {
			String menuname = driver.findElement(By.xpath(submen.replace("#DELIM#",String.valueOf(i)))).getText();
			//			System.out.println(menuname);
			if(submenuname.equalsIgnoreCase(menuname)) {
				cl_click(driver.findElement(By.xpath(submen.replace("#DELIM#",String.valueOf(i)))));
				break;
			}
		}
		cl_click(searchbox);
		cl_entertext(searchbox, searchinput);
		Robot r = new Robot();
		r.keyPress((KeyEvent.VK_ENTER));
		Thread.sleep(3000);
		int size = nameofcitylist.size();
		System.out.println("Size : "+size);
		for(int k=1;k<=size;k++) {
			String nam = driver.findElement(By.xpath(city.replace("#DELIM#",String.valueOf(k)))).getText();
			nam = nam.replaceAll(" ","");
//			System.out.println(nam);
			if(nam.equalsIgnoreCase(searchinput)) {
//				System.out.println(searchinput);
				String si = "'"+searchinput+"'";
				try {
					check = driver.findElement(By.xpath(cname.replace("#DELIM#",String.valueOf(si))));
					ciname = check.getAttribute("checked");
					mcl = mapcitylist.size();
//					System.out.println("Map City : "+mcl);
					if (!(ciname.isEmpty())) {
//						cl_click(check);
						Thread.sleep(3000);		
						for(int j=1;j<=mcl;j++) {
							String mcname = driver.findElement(By.xpath(mapcity.replace("#DELIM#",String.valueOf(j)))).getText();
							System.out.println("City in Map : "+mcname);
							if(mcname.equalsIgnoreCase(searchinput)) {
								ct = driver.findElement(By.xpath(temp.replace("#DELIM#",String.valueOf(j)))).getText();
								System.out.println("City Temperature is "+ct+" Degree Celsius");
								break;
							}
						}

					}

				}catch(Exception e) {
//					e.printStackTrace();
					cl_click(check);
					Thread.sleep(3000);		
					int mcl1 = mapcitylist.size();
//					System.out.println("MCL :" +mcl1);
					for(int m=1;m<=mcl1;m++) {
						
						String mcname1 = driver.findElement(By.xpath(mapcity.replace("#DELIM#",String.valueOf(m)))).getText();
						System.out.println("City in Map updated: "+mcname1);
						if(mcname1.equalsIgnoreCase(searchinput)) {
							ct = driver.findElement(By.xpath(temp.replace("#DELIM#",String.valueOf(m)))).getText();
							System.out.println("City Temperature is "+ct +" Degree Celsius");
							break;
						}
					}
				}

			} 
			/*
			 * else { System.out.println("Entered City name not in list");
			 * 
			 * }
			 */




		}return ct;
	}
}