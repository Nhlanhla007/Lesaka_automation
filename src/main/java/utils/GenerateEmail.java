package utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import Logger.Log;
import customreports.TestSuites;

public class GenerateEmail {
	public static boolean errorFlag;
	static Properties mailServerProperties;
	static Session getMailSession;
	static MimeMessage generateMailMessage;
	static Logger logger = Log.getLogData(GenerateEmail.class.getSimpleName());
	private static final String TEMPLATE_PATH="template\\EmailTemplate.html";



	private static final String TITLE = "WAPOL Test Automation Report";
	private static final String TITLE_PLACEHOLDER = "#TITLE#";
	private static final String DATE_TIME_PLACEHOLDER = "#DATETIME#";
	private static final String EXECUTION_START_TIME_PLACEHOLDER = "#EXECUTIONSTARTTIME#";
	private static final String EXECUTION_END_TIME_PLACEHOLDER = "#EXECUTIONENDTIME#";
	private static final String TOTAL_EXECUTION_TIME_PLACEHOLDER = "#TOTALEXECUTIONTIME#";
	private static final String OS_PLACEHOLDER = "#OS#";
	private static final String RESULTDETAILS1 = "#RESULTDETAILS1#";
	private static final String PASSPERCENTAGE = "#PASSPERCENTAGE#";
	private static final String FAILPERCENTAGE = "#FAILPERCENTAGE#";
	private static final String PASSEDTESTCASESCOUNT = "#PASSEDTESTCASESCOUNT#";
	private static final String FAILEDTESTCASESCOUNT = "#FAILEDTESTCASESCOUNT#";
	private static DecimalFormat round = new DecimalFormat("##.00");


	public static void main(String args[]) throws AddressException, MessagingException {
		//generateAndSendEmail();
		System.out.println("\n\n ===> Your Java Program has just sent an Email successfully. Check your email..");
	}

	public static void generateAndSendEmail(String filePath,String currentDate,long totalTime, HashMap<String, TestSuites> suites,List<String> suiteNames) throws AddressException, MessagingException {
		try
		{
			// Step1
			logger.info("\n 1st ===> setup Mail Server Properties..");
			mailServerProperties = System.getProperties();
			//mailServerProperties.put("mail.smtp.port", "25");
			mailServerProperties.put("mail.smtp.host", "smtp-relay-int.ad.police.wa.gov.au");
			mailServerProperties.put("mail.smtp.auth", "false");
			mailServerProperties.put("mail.smtp.starttls.enable", "true");
			mailServerProperties.put("mail.smtp.ssl.trust", "smtp-relay-int.ad.police.wa.gov.au");
			logger.info("Mail Server Properties have been setup successfully..");

			// Step2
			logger.info("\n\n 2nd ===> get Mail Session..");
			getMailSession = Session.getDefaultInstance(mailServerProperties, null);
			generateMailMessage = new MimeMessage(getMailSession);
			generateMailMessage.setFrom(new InternetAddress("No_Reply@police.wa.gov.au"));
			generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("Deepa.MATHIAS@police.wa.gov.au"));

			generateMailMessage.setSubject("Test Automation Report Dated:"+currentDate);
			//HTML mail content

			String reportIn = new String(Files.readAllBytes(Paths
					.get(TEMPLATE_PATH)));

			reportIn = reportIn.replace(TITLE_PLACEHOLDER, TITLE);
			reportIn = reportIn.replace(OS_PLACEHOLDER, "Windows");
			reportIn = reportIn.replace(EXECUTION_START_TIME_PLACEHOLDER,
					Values.startTime);
			reportIn = reportIn.replace(EXECUTION_END_TIME_PLACEHOLDER,
					Values.endTime);
			reportIn = reportIn.replace(TOTAL_EXECUTION_TIME_PLACEHOLDER,
					String.valueOf(totalTime / 1000) + "s");
			reportIn = reportIn.replace(DATE_TIME_PLACEHOLDER, Values.endTime);

			//Table iteration
			int totalCount = 0;
			int executedCount = 0;
			int passedCount = 0;
			int failedCount = 0;
			int skippedCount = 0;
			DecimalFormat decimalFormat = new DecimalFormat("#.##");
			StringBuffer details1 = new StringBuffer();
			ArrayList<String> expList1 = new ArrayList<String>();

			for (int i = 0; i < suites.size(); i++) {
				expList1.add("ts" + (i + 1));
				String suiteName = suiteNames.get(i);
				TestSuites ts = suites.get(suiteName);
				details1.append(" <tr>");
				String col=(ts.isPassed() ? "green" : "#a50000");
				details1.append("<th class=\"blueNormal\"; style=\"text-decoration: none; font-size: 1em;color:"
						+col
						+ "\">"
						+"<strong>"
						+suiteName
						+"</strong></th>");
				details1.append("<th class=\"blueNormal\"> "+ts.getNoOfTestCases()+" </th>");
				details1.append("<th class=\"blueNormal\"> "+(ts.getNoOfPassedTestCases()+ts.getNoOfFailedTestCases())+" </th>");
				details1.append("<th class=\"blueNormal\"> "+ts.getNoOfPassedTestCases()+" </th>");
				details1.append("<th class=\"blueNormal\"> "+ts.getNoOfFailedTestCases()+" </th>");
				details1.append("<th class=\"blueNormal\"> "+ts.getNoOfSkippedTestCases()+" </th>");
				details1.append("<th class=\"blueNormal\"> "+decimalFormat.format((float)(((ts.getNoOfPassedTestCases()+ts.getNoOfFailedTestCases())*100)/ts.getNoOfTestCases()))+"% </th>");
				details1.append("<th class=\"blueNormal\">"+decimalFormat.format(100*ts.getNoOfPassedTestCases()/(float)ts.getNoOfTestCases())+"% </th>");
				details1.append(" </tr>");
				totalCount=totalCount+ts.getNoOfTestCases();
				executedCount=executedCount+(ts.getNoOfPassedTestCases()+ts.getNoOfFailedTestCases());
				passedCount=passedCount+ts.getNoOfPassedTestCases();
				failedCount=failedCount+ts.getNoOfFailedTestCases();
				skippedCount=skippedCount+ts.getNoOfSkippedTestCases();
			}
			details1.append(" <tr>");
			details1.append("<th class=\"blue\"><i>Total</i></th>");
			details1.append("<th class=\"blue\">"+totalCount+"</th>");
			details1.append("<th class=\"blue\">"+executedCount+"</th>");
			details1.append("<th class=\"blue\">"+passedCount+"</th>");
			details1.append("<th class=\"blue\">"+failedCount+"</th>");
			details1.append("<th class=\"blue\">"+skippedCount+"</th>");
			details1.append("<th class=\"blue\">"+decimalFormat.format((float)(((passedCount+failedCount)*100)/totalCount))+"% </th>");
			details1.append("<th class=\"blue\">"+decimalFormat.format(100*passedCount/(float)totalCount)+"% </th>");
			details1.append(" </tr>");
			reportIn = reportIn
					.replace(RESULTDETAILS1, new String(details1));
			reportIn = reportIn.replace(PASSPERCENTAGE, new String(round.format(100*passedCount/(float)totalCount)));
			reportIn = reportIn.replace(FAILPERCENTAGE, new String(round.format(100*failedCount/(float)totalCount)));
			reportIn = reportIn.replace(PASSEDTESTCASESCOUNT, new String(Integer.toString(passedCount)));
			reportIn = reportIn.replace(FAILEDTESTCASESCOUNT, new String(Integer.toString(failedCount)));

			generateMailMessage.setContent(reportIn, "text/html");
			logger.info("Mail Session has been created successfully..");

			BodyPart messageBodyPart = new MimeBodyPart();
			// Now set the actual message
			Multipart multipart = new MimeMultipart();
			messageBodyPart.setContent(reportIn, "text/html");
			multipart.addBodyPart(messageBodyPart);


			// Part two is attachment
			messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(filePath);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName("Test Automation Report_"+currentDate+".html");
			multipart.addBodyPart(messageBodyPart);

			// Send the complete message parts
			generateMailMessage.setContent(multipart);
			Transport.send(generateMailMessage);

		}
		catch(Exception e)
		{
			logger.error("Error in sending email report",e);
		}

	}


}
