import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        // 1 task
        List<Employee> list1 = parseCSV(columnMapping, fileName1);
        String json = listToJson(list1);
        writeString(listToJson(list1), "data1.json");

        // 2 task
        String xmlFileName = "data.xml";
        List<Employee> list2 = parseXML(xmlFileName);
        String json2 = listToJson(list2);
        String Filename = "data2.json";
        writeString(json2, Filename);
    }

    static final String fileName1 = "data.csv";
    static final String fileName2 = "data.xml";
    static final String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};

    private static List<Employee> parseCSV(String[] columnMapping, String fileName1) {


        try (CSVReader reader = new CSVReader(new FileReader(fileName1))) {

            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(columnMapping);

            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(reader)
                    .withMappingStrategy(strategy)
                    .build();
            return csv.parse();

        } catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }

    public static String listToJson(List<Employee> list) {
        GsonBuilder builder = new GsonBuilder();
        Gson json = builder.create();
        Type listType = new TypeToken<List<Employee>>() {
        }.getType();
        return json.toJson(list, listType);
    }

    public static void writeString(String json, String fileName) {
        try (FileWriter file = new
                FileWriter(fileName)) {
            file.write(json);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Employee> parseXML(String fileName) throws IOException, SAXException, ParserConfigurationException {

        List<Employee> employeesList = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            org.w3c.dom.Document document = builder.parse(fileName);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("employee");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    int id = Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent());
                    String firstName = element.getElementsByTagName("firstName").item(0).getTextContent();
                    String lastName = element.getElementsByTagName("lastName").item(0).getTextContent();
                    String country = element.getElementsByTagName("country").item(0).getTextContent();
                    int age = Integer.parseInt(element.getElementsByTagName("age").item(0).getTextContent());
                    employeesList.add(new Employee(id, firstName, lastName, country, age));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employeesList;
    }
}
