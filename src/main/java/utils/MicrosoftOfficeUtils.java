package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xslf.usermodel.SlideLayout;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFSlideLayout;
import org.apache.poi.xslf.usermodel.XSLFSlideMaster;
import org.apache.poi.xslf.usermodel.XSLFTextShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.testng.Assert;
import Logger.Log;


public class MicrosoftOfficeUtils {


	private static final String wordDocumentName= System.getProperty("user.dir") +"\\Test1.docx";
	private static final String excelFileName= System.getProperty("user.dir") +"\\Test1.xlsx";
	private static final String powerPointFileName= System.getProperty("user.dir") +"\\Test1.pptx";
	private static final String text ="Hello, Windows 10 Upgrade project.";
	static Logger logger = Log.getLogData(MicrosoftOfficeUtils.class.getSimpleName());


	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		createPowerPointPresentation();
	}

	//Create word document and write a paragraph
	public static void createAndReadWordDocument() throws IOException {

		try
		{

			XWPFDocument document = new XWPFDocument();
			FileOutputStream out= new FileOutputStream(new File(wordDocumentName));
			XWPFParagraph paragraph = document.createParagraph();  
			XWPFRun run = paragraph.createRun();  
			run.setText(text);  
			document.write(out);
			out.close();
			document.close();

			File file = new File(wordDocumentName);
			FileInputStream fis = new FileInputStream(file.getAbsolutePath());

			XWPFDocument inDocument = new XWPFDocument(fis);

			List<XWPFParagraph> paragraphs = inDocument.getParagraphs();


			for (XWPFParagraph para : paragraphs) {
				Assert.assertEquals(para.getText(), text,"Data is not matching");
				System.out.println(para.getText());
			}
			fis.close();
			inDocument.close();

		}
		catch(Exception e)
		{
			Assert.fail("error in creating word document");
			logger.error("Error", e);
		}

	}


	public static void createAndReadExcelWorkBook()
	{
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Test");

		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue(text);

		try {
			FileOutputStream outputStream = new FileOutputStream(excelFileName);
			workbook.write(outputStream);
			workbook.close();
		} catch (Exception e) {
			Assert.fail("error in creating excel workbook");
			logger.error("Error", e);
		}

		//Read

		sheet = workbook.getSheetAt(0);
		String cellData=sheet.getRow(0).getCell(0).getStringCellValue();
		Assert.assertEquals(cellData, text,"Data is not matching");
		System.out.println("cell data"+cellData);


	}

	public static void createPowerPointPresentation() throws FileNotFoundException, IOException
	{
		XMLSlideShow ppt = new XMLSlideShow();	     
		try
		{
			//creating an FileOutputStream object
			File file = new File(powerPointFileName);
			FileOutputStream out = new FileOutputStream(file);

			XSLFSlideMaster defaultMaster = ppt.getSlideMasters().get(0);
			// title slide
			XSLFSlideLayout titleLayout = defaultMaster.getLayout(SlideLayout.TITLE_AND_CONTENT);
			// fill the placeholders
			XSLFSlide slide = ppt.createSlide(titleLayout);
			XSLFTextShape title1 = slide.getPlaceholder(0);
			title1.setText(text);
			XSLFTextShape body = slide.getPlaceholder(1);  
			body.clearText();  
			//saving the changes to a file
			ppt.write(out);
			out.close();
			ppt.close();
		}
		catch(Exception e)
		{
			Assert.fail("error in creating PPT");
			logger.error("Error", e);
		}

		 StringBuilder str = new StringBuilder(); 

		for (XSLFSlide slide: ppt.getSlides()) {
			List<XSLFShape> shapes = slide.getShapes();
			for (XSLFShape shape: shapes) {
				if (shape instanceof XSLFTextShape) {
					XSLFTextShape textShape = (XSLFTextShape)shape;
					String text = textShape.getText();
					str.append(text);
				}
			}
		}
		Assert.assertEquals(text.trim(), text);
	

	}
}