package utils;

import java.io.*;
import java.util.Properties;

public class ConfigFileReader {

	
	private static Properties properties;
	private final static String propertyFilePath= System.getProperty("user.dir") + "\\src\\test\\resources\\configs\\configuration.properties";

	
	public static void loadData(){
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(propertyFilePath));
			properties = new Properties();
			try {
				properties.load(reader);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
		}		
	}
	
	public static String getProperty(String key)
	{
		return properties.getProperty(key);
	}
	public static String getPropertySavedVal(String key){
		String basePath = System.getProperty("user.dir");
		FileInputStream fip = null;
		try {
			fip = new FileInputStream(basePath+"\\src\\test\\resources\\data\\savedVar.properties");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Properties prop = new Properties();
		try {
			prop.load(fip);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String value = prop.getProperty(key);
		return value;
	}
	
	public static String getPropertyVal(String key){
		String basePath = System.getProperty("user.dir");
		FileInputStream fip = null;
		try {
			fip = new FileInputStream(basePath+"\\src\\test\\resources\\configs\\configuration.properties");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Properties prop = new Properties();
		try {
			prop.load(fip);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String value = prop.getProperty(key);
		return value;
	}
	public void setPropertyVal(String key,String value) throws IOException {
		//Instantiating the properties file
		String basePath = System.getProperty("user.dir");
		Properties props = new Properties();
		//Populating the properties file
		props.put(key, value);
		//Instantiating the FileInputStream for output file
		String path = basePath+"\\src\\test\\resources\\data\\savedVar.properties";
		FileOutputStream outputStrem = new FileOutputStream(path,true);
		//Storing the properties file
		props.store(outputStrem, null);

	}
	
}
