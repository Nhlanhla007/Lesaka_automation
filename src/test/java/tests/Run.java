package tests;

import org.testng.TestNG;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import utils.DataTable2;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Run {
    public static void main(String[]args) throws Exception {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();
        Element root = document.createElement("suite");
        Attr suiteNameAttr = document.createAttribute("name");
        suiteNameAttr.setValue("JDGTestIC");
        root.setAttributeNode(suiteNameAttr);
        document.appendChild(root);
        DataTable2 dataTable2;
        dataTable2= new DataTable2();
        dataTable2.setPath("UPDATEFINAL");
        LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> dataMap2 = new LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>>();
        dataMap2=dataTable2.getExcelData();
        LinkedHashMap<String, ArrayList<String>> suiteIC = dataMap2.get("IC");
        int numberOfRows=suiteIC.get("Execute").size();
        Element test = document.createElement("test");
        Attr testNameAttr = document.createAttribute("name");
        testNameAttr.setValue("JDGTestIC");
        test.setAttributeNode(testNameAttr);
        root.appendChild(test);
        Element classes = document.createElement("classes");
        test.appendChild(classes);
        Element className = document.createElement("class");
        classes.appendChild(className);
        Element methods = document.createElement("methods");
        Attr classNameAttr = document.createAttribute("name");
        classNameAttr.setValue("tests.JDGTest_TestNG");
        className.setAttributeNode(classNameAttr);
        className.appendChild(methods);
        for(int i=0;i<numberOfRows;i++){
            if(suiteIC.get("Execute").get(i).toLowerCase().equals("yes")){
                Element include = document.createElement("include");
                Attr includeName = document.createAttribute("name");
                includeName.setValue(suiteIC.get("Test Case Name").get(i));
                include.setAttributeNode(includeName);
                methods.appendChild(include);
//                System.out.println("include");
//                System.out.println(suiteIC.get("Test Case Name").get(i));
            }
        }
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "http://testng.org/testng-1.0.dtd");
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File("testNG.xml"));
        transformer.transform(domSource, streamResult);
//        System.out.println("Done creating XML File");
//        Thread.sleep(10000);
//        TestNG runner=new TestNG();
//        List<String> suitefiles=new ArrayList<String>();
//        suitefiles.add("testNG.xml");
//        runner.setTestSuites(suitefiles);
//        runner.run();
    }


}