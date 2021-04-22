package utils;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.collections.Lists;
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
        dataTable2.setPath("MAIN");
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
                System.out.println("include");
                System.out.println(suiteIC.get("Test Case Name").get(i));
            }
        }
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "http://testng.org/testng-1.0.dtd");
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File("testNG.xml"));
        transformer.transform(domSource, streamResult);
        System.out.println("Done creating XML File");
        TestListenerAdapter tla = new TestListenerAdapter();
        TestNG testng = new TestNG();
        List<String> suites = Lists.newArrayList();
        suites.add("testNG.xml");//path to xml..
        testng.setTestSuites(suites);
        testng.run();

//        generateXML();
//
//        Attr attr = document.createAttribute("id");
//        attr.setValue("10");
//        employee.setAttributeNode(attr);
    }

    public static void generateXML() throws TransformerException, ParserConfigurationException {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();
        // root element
        Element root = document.createElement("company");
        document.appendChild(root);
        // employee element
        Element employee = document.createElement("employee");
        root.appendChild(employee);
        // set an attribute to staff element
        Attr attr = document.createAttribute("id");
        attr.setValue("10");
        employee.setAttributeNode(attr);

        //you can also use staff.setAttribute("id", "1") for this

        // firstname element
        Element firstName = document.createElement("firstname");
        firstName.appendChild(document.createTextNode("James"));
        employee.appendChild(firstName);

        // lastname element
        Element lastname = document.createElement("lastname");
        lastname.appendChild(document.createTextNode("Harley"));
        employee.appendChild(lastname);

        // email element
        Element email = document.createElement("email");
        email.appendChild(document.createTextNode("james@example.org"));
        employee.appendChild(email);

        // department elements
        Element department = document.createElement("department");
        department.appendChild(document.createTextNode("Human Resources"));
        employee.appendChild(department);

        // create the xml file
        //transform the DOM Object to an XML File
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File("testNG2.xml"));


        // If you use
        // StreamResult result = new StreamResult(System.out);
        // the output will be pushed to the standard output ...
        // You can use that for debugging

        transformer.transform(domSource, streamResult);

        System.out.println("Done creating XML File");
    }
}
