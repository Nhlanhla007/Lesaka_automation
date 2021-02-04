package pageddef;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import Logger.Log;
import utils.JsonReader;



public class PageDef {
	Identifier identifier = null;
	String key;
	String value;
	static Map<String, Identifier> map = new HashMap<String, Identifier>();
	static Logger logger = Log.getLogData(PageDef.class.getSimpleName());

	public  static void initializeLocators(String path) throws Exception {

		JsonReader reader = new JsonReader(path);
		JsonObject root = reader.getRoot();
		Iterator<Entry<String, JsonElement>> iter = root.entrySet().iterator();
		Identifier identifier = null;
		while (iter.hasNext()) {
			Entry<String, JsonElement> idJson = iter.next();
			String elemName = idJson.getKey().trim().toLowerCase();
			String[] parts = idJson.getValue().getAsString().split("=", 2);
			identifier = new Identifier(parts[0].trim(), parts[1].trim());
			map.put(elemName,identifier);
		}
	}
	public static By getIdentifier(String alias,String valueToReplace)  {

		IdentifyBy idType= map.get(alias.toLowerCase()).getIdType();
		String idValue= map.get(alias.toLowerCase()).getValue().toString();



		if(valueToReplace!=null | valueToReplace!=" ")
		{

		String[] values=valueToReplace.split(",");
	
			idValue=String.format(idValue, values);
	
		}
		By loc = null;
					{

						switch(idType)
						{
						case ID : 
							loc = By.id(idValue);
							break;
						case XPATH : 
							loc = By.xpath(idValue);
							break;
						case NAME : 
							loc=By.name(idValue);
							break;
						case CLASSNAME:
							loc=By.className(idValue);
							break;
						case LINKTEXT:
							By.linkText(idValue);
							break;
						case PARTIALLINKTEXT:
							By.partialLinkText(idValue);
							break;
						case CSS:
							By.cssSelector(idValue);
							break;
						case TAGNAME:
							By.tagName(idValue);
							break;
						default:
							String msg = "Wrong identifier passed. Identifier Type : " + idType;
							logger.info(msg);
							break;
						}

					}
					return loc;
	}
}




