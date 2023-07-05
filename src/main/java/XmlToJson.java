import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XmlToJson {
    static final String fileName = "data.xml";

    private static List<Employee> parseXML(String fileName) throws IOException, SAXException, ParserConfigurationException {

        ArrayList<Employee> employeesList = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(fileName));

//        Node root = document.getDocumentElement();
//        NodeList rootList = root.getChildNodes();

        NodeList rootList = document.getDocumentElement().getChildNodes();

        for (int i = 0; i < rootList.getLength(); i++) {
            Node employee = rootList.item(i);



        }
    }

//    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
//        parseXML(fileName);
//        CsvToJson.writeString(parseXML(fileName).toString(), "data2.json");
//    }
}
