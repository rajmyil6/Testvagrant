package Page_Factory;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import Generic_Library.Basefunctions;

public class pf_genericmethods extends Basefunctions {

	//	Entering text
	
	

	public void cl_entertext(WebElement el, String input){

		el.sendKeys(input);
	}

	//	Click on element

	public void cl_click(WebElement el){

		el.click();
	}

	// click OK on alert
	public void cl_popup() throws Exception {
		// Switch to Alert        
		Alert alert = w.switchTo().alert();		

		// Capture alert message.    
		String alertMessage= w.switchTo().alert().getText();		

		// Display alert message		
		System.out.println(alertMessage);	
		Thread.sleep(5000);

		// Accepting alert		
		alert.accept();	
	}
	
	public void cl_dropdownCheck(String[] exp,List<WebElement> options ) {
		int count=0;
		for (WebElement we : options) {
	        for (int i = 0; i < exp.length; i++) {
	            if (we.getText().equals(exp[i])) {
	                count++;
	            }
	        }
	    }
	    if (count == exp.length) {
	        System.out.println("Actual and Expected values in dropdown matches");
	    } else {
	        System.out.println("Actual and Expected values in dropdown not matches");
	    }
	}
	
	public void cl_cmpreResult(String actual, String Exp) throws Exception {
		if(actual.equals(Exp)) {
			System.out.println("Actual and Expected Result matches");
	    } else {
	        System.out.println("Actual and Expected Result not matches");
	    }
	}
	
	
	

}
