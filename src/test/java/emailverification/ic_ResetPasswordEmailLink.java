package emailverification;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import utils.Action;
import utils.DataTable2;

public class ic_ResetPasswordEmailLink {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
	
	public ic_ResetPasswordEmailLink(WebDriver driver, DataTable2 dataTable2){
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2=dataTable2;
	}
	

}
