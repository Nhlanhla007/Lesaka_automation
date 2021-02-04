package utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
	
}
