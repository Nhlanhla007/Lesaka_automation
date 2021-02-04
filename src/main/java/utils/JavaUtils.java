package utils;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;

import Logger.Log;


/**
 * <copyright file="JavaUtils.java" company="Modi's Consulting Group"> Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic,
 * mechanical or otherwise, is prohibited without the prior written permission
 * of the copyright owner. </copyright>
 * 
 * @author 
 */

public class JavaUtils {

	static Logger logger = Log.getLogData(JavaUtils.class.getSimpleName());

	public static String filePath;

	/**
	 * deleteFiles() method deletes the files inside the directory. 
	 * @param directoryPath
	 */


	public static void deleteFiles(String directoryPath)
	{
		File dir = new File(directoryPath);

		if(dir.isDirectory() == false) {
			logger.info("Not a directory. Do nothing");
			return;
		}


		File[] listFiles = dir.listFiles();
		for(File file : listFiles){
			file.delete();
		}
	}

	public static String getAbsoluteFilePath(String relativeFilePath)
	{
		File f= new File(relativeFilePath);
		logger.info("absolute path"+f.getAbsolutePath());
		return f.getAbsolutePath();
	}

	/**
	 * deletDirectory() method deletes the directory
	 * @param directoryPath
	 */

	public static void deletDirectory(String directoryPath) throws IOException
	{
		File dir = new File(directoryPath);
		FileUtils.deleteDirectory(dir);
	}


	/**
	 * getSystemDate() method returns the system date in dd/MM/yyyy format
	 * @return system date
	 */

	public static String getSystemDate()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		logger.info(dateFormat.format(date));
		return dateFormat.format(date);
	}

	public static String getSystemDateAndTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date date = new Date();
		logger.info("System date time"+dateFormat.format(date));
		return dateFormat.format(date);
	}

	public static String getSystemDateAndTime12HourFormat()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy hh:mm:ss aa");
		Date date = new Date();
		logger.info("System date time"+dateFormat.format(date));
		return dateFormat.format(date);
	}


		/**
		 * getSystemDatePlusNumberOfDays() method adds specified number of days to particular date.
		 * @return date in dd/MM/yyyy format
		 */

		public static String getSystemDatePlusNumberOfDays(String date,int count) throws ParseException
		{
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Calendar c = Calendar.getInstance();
			c.setTime(dateFormat.parse(date));
			c.add(Calendar.DATE, count); 
			return dateFormat.format(c.getTime());
		}

		/**
		 * getSystemDatePlusNumberOfDays() method substracts specified number of days to particular date.
		 * @return date in dd/MM/yyyy format
		 */

		public static String getSystemDateMinusNumberOfDays(String date,int count) throws ParseException
		{
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Calendar c = Calendar.getInstance();
			c.setTime(dateFormat.parse(date));
			c.add(Calendar.DATE, -count); 
			return dateFormat.format(c.getTime());
		}

		/**
		 * saveFileFromIEBrowser() method uses Robot class to save the files from IE browser. 
		 */

		public static void saveFileFromIEBrowser()
		{
			try
			{
				Robot robot = new Robot();
				robot.setAutoDelay(250);
				robot.keyPress(KeyEvent.VK_ALT);
				Thread.sleep(2000);
				robot.keyPress(KeyEvent.VK_S);
				robot.keyRelease(KeyEvent.VK_ALT);
				robot.keyRelease(KeyEvent.VK_S);
			}
			catch (AWTException |InterruptedException e)
			{
				e.printStackTrace();
			}
		}


		/**
		 * handleAuthentication_Chrome() method uses Robot class to enter user name and password to Chrome authentication alert
		 */

		public static void handleAuthentication_Chrome(String userName,String password)
		{
			try
			{
				Robot robot = new Robot();
				StringSelection username = new StringSelection(userName);
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(username, null);            
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_V);
				robot.keyRelease(KeyEvent.VK_V);
				robot.keyRelease(KeyEvent.VK_CONTROL);

				//tab to password entry field
				robot.keyPress(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_TAB);
				Thread.sleep(2000);

				//Enter password by ctrl-v
				StringSelection pwd = new StringSelection(password);
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(pwd, null);
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_V);
				robot.keyRelease(KeyEvent.VK_V);
				robot.keyRelease(KeyEvent.VK_CONTROL);

				//press enter
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);

				//wait
				Thread.sleep(5000);
			}
			catch (AWTException |InterruptedException e)
			{
				e.printStackTrace();
			}
		}


		/**
		 * getLatestFilefromDir() method  returns latest modified file from directory. 
		 * @param dirPAth
		 */

		public static File getLatestFilefromDir(String dirPath){
			File dir = new File(dirPath);
			File[] files = dir.listFiles();
			if (files == null || files.length == 0) {
				return null;
			}

			File lastModifiedFile = files[0];
			for (int i = 1; i < files.length; i++) {
				if (lastModifiedFile.lastModified() < files[i].lastModified()) {
					lastModifiedFile = files[i];
				}
			}
			return lastModifiedFile;
		}

		/**
		 * readCSVFile() method reads particular column of CSV file using comma as delimiter 
		 * @param filePath
		 * @param column Number
		 */

		public static List<String> readCSVFile(String filePath,int columnNumber)

		{

			List<String> records=new ArrayList<String>();
			String line = "";
			String cvsSplitBy = ",";
			try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

				while ((line = br.readLine()) != null) {

					// use comma as separator
					String[] details = line.split(cvsSplitBy);
					records.add(details[columnNumber]);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return records;

		}

		/**
		 * fileExistsInDirectory() method checks for the existence of the file in a directory
		 * @param directoryPath
		 * @param fileName
		 */

		public static Boolean fileExistsInDirectory(String directoryPath,String fileName)
		{
			Boolean b=false;
			File dir = new File(directoryPath);

			File[] listFiles = dir.listFiles();
			for(File file : listFiles){
				if(file.getName().contains(fileName))
				{
					filePath=file.getAbsolutePath();
					b=true;
					break;
				}
			}
			return b;
		}


		public static int generateRandomDigits(int n) {
			int m = (int) Math.pow(10, n - 1);
			return m + new Random().nextInt(9 * m);
		}


		public static String generateRandomAlphaNumeric(int count) {

			final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

			StringBuilder builder = new StringBuilder();
			while (count-- != 0) {
				int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
				builder.append(ALPHA_NUMERIC_STRING.charAt(character));
			}
			return builder.toString();

		}

		public static String readPDF() throws InvalidPasswordException, IOException
		{
			PDDocument document = PDDocument.load(new File(filePath));
			String text = null;
			if (!document.isEncrypted()) {
				PDFTextStripper stripper = new PDFTextStripper();
				text = stripper.getText(document);
				logger.info("Text:" + text);
			}
			document.close();

			return text;
		}

	}
