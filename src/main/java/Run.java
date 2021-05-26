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
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Run {
    public static void main(String[]args) throws Exception {

        DataTable2 dataTable2;
        dataTable2= new DataTable2();
        String moduleName="MAIN";
        dataTable2.setPath(moduleName);
        LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> dataMap2 = new LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>>();
        dataMap2=dataTable2.getExcelData();

        LinkedHashMap<String, ArrayList<String>> suites = dataMap2.get("Suites");
        int numberOfRowsInSuites=suites.get("Execute").size();

        int numberOfRows=0;

        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();
        Element root = document.createElement("suite");
        Attr suiteNameAttr = document.createAttribute("name");
        suiteNameAttr.setValue("JDGTest");
        root.setAttributeNode(suiteNameAttr);
        document.appendChild(root);


        String sampleTestMethod=FileToString("src/test/resources/sampleTestMethod");
        String sampleTestClass=FileToString("src/test/resources/sampleTestClass");
        String allTestMethods="";
        for(int z = 0; z < numberOfRowsInSuites; z++) {
            if(suites.get("Execute").get(z).toLowerCase().equals("yes")) {
                String testSuitname=suites.get("testSuitName").get(z);
                LinkedHashMap<String, ArrayList<String>> suiteCur=dataMap2.get(testSuitname);
                numberOfRows=suiteCur.get("Execute").size();

                ////////////////////////////////////////////////////////////////

                Element test = document.createElement("test");
                Attr testNameAttr = document.createAttribute("name");
                testNameAttr.setValue(testSuitname);
                test.setAttributeNode(testNameAttr);
                root.appendChild(test);
                Element parameter1 = document.createElement("parameter");
                Attr parameter1Attr1 = document.createAttribute("name");
                Attr parameter1Attr2 = document.createAttribute("value");
                parameter1Attr1.setValue("moduleName");
                parameter1.setAttributeNode(parameter1Attr1);
                parameter1Attr2.setValue(moduleName);
                parameter1.setAttributeNode(parameter1Attr2);

                Element parameter2 = document.createElement("parameter");
                Attr parameter2Attr1 = document.createAttribute("name");
                Attr parameter2Attr2 = document.createAttribute("value");
                parameter2Attr1.setValue("CurSuite");
                parameter2.setAttributeNode(parameter2Attr1);
                parameter2Attr2.setValue(testSuitname);
                parameter2.setAttributeNode(parameter2Attr2);

                test.appendChild(parameter1);
                test.appendChild(parameter2);
                Element classes = document.createElement("classes");
                test.appendChild(classes);
                Element className = document.createElement("class");
                classes.appendChild(className);
                Element methods = document.createElement("methods");
                Attr classNameAttr = document.createAttribute("name");
//        classNameAttr.setValue("tests.JDGTest_TestNG");
                classNameAttr.setValue("tests.testTestClass");
                className.setAttributeNode(classNameAttr);
                className.appendChild(methods);
                /////////////////////////////////////////////////////////////////


                for (int i = 0; i < numberOfRows; i++) {
                    if (suiteCur.get("Execute").get(i).toLowerCase().equals("yes")) {
                        Element include = document.createElement("include");
                        Attr includeName = document.createAttribute("name");
                        String testCasename=suiteCur.get("Test_Case_Name").get(i)+"_"+testSuitname;
                        includeName.setValue(testCasename);
                        allTestMethods = allTestMethods + System.getProperty("line.separator") + sampleTestMethod.replaceAll("\\$\\{testCaseName}", testCasename).replace("${TCID}", suiteCur.get("TestCaseID").get(i));
                        include.setAttributeNode(includeName);
                        methods.appendChild(include);
                    }
                }
            }
        }
        sampleTestClass=sampleTestClass.replace("${AllTestMethods}",allTestMethods);
        stringToFile("src/test/java/tests/testTestClass.java",sampleTestClass);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "http://testng.org/testng-1.0.dtd");
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File("testNG.xml"));
        transformer.transform(domSource, streamResult);
//        System.out.println("Done creating XML File");
    }
    public static String FileToString(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        String ls = System.getProperty("line.separator");
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(ls);
        }
// delete the last new line separator
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        reader.close();
        String content = stringBuilder.toString();
        return content;
    }
    public static void stringToFile(String fileName,String content){
        try {
            File myObj = new File(fileName);
            if (myObj.createNewFile()) {
//                System.out.println("File created: " + myObj.getName());
            } else {
//                System.out.println("File already exists.");
            }
            FileWriter myWriter = new FileWriter(myObj);
            myWriter.write(content);
            myWriter.close();
//            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
//            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


}