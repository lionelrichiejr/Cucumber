package hellocucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.Assert.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class StepDefinitions {
 
  private String theSunGod = "Escanor";
  private Document doc;
  private File file;
  private String[] sins = new String[3];

  private void fill_sin_array() {

	NodeList nList = doc.getElementsByTagName("user");
	for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) nNode;
                Node node = elem.getElementsByTagName("firstname").item(0);
                String fname = node.getTextContent();
                node = elem.getElementsByTagName("lastname").item(0);
                String lname = node.getTextContent();
		sins[i] = fname + " " + lname;
            }
        }
  }
  @Given("the file {string}")
  public void the_file(String string) throws SAXException,
            IOException, ParserConfigurationException {

        File xmlFile = new File(string);
        file = xmlFile;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factory.newDocumentBuilder();
        doc = dBuilder.parse(xmlFile);
  }

  @Given("the file exists")
  public void the_file_exists() {
	assertEquals(true, file.exists()); 
  }

  @Then("the second users first name should be Escanor")
  public void the_second_users_first_name_should_be_escanor() {
        NodeList nList = doc.getElementsByTagName("user");
        Node nNode = nList.item(1);
	Element elem = (Element) nNode;
	Node node1 = elem.getElementsByTagName("firstname").item(0);
	String fname = node1.getTextContent();
	fill_sin_array();
	assertEquals(theSunGod, fname);
  }

  @Then("the file {string} should exist")
  public void the_file_should_exist(String string) throws ParserConfigurationException,
	TransformerException {
	DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance(); 
	DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
	Document deadly_sins = documentBuilder.newDocument();
	Element root = deadly_sins.createElement("Deadly_Sins");
	deadly_sins.appendChild(root);
	//Element member =  deadly_sins.createElement("member");
	//root.appendChild(member);
	for (int i = 0; i < 3; i++) {
	 Element member = deadly_sins.createElement("member");
	 root.appendChild(member);
	 member.appendChild(deadly_sins.createTextNode(sins[i]));
	}
	TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(deadly_sins);
	File outFile = new File("/var/lib/jenkins/workspace/CucumberTest/deadly_sins.xml");
        StreamResult streamResult = new StreamResult(outFile);
	transformer.transform(domSource, streamResult);
	assertEquals(true, outFile.exists());
  }

}
