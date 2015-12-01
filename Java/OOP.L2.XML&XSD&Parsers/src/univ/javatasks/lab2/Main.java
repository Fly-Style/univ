package univ.javatasks.lab2;

import univ.javatasks.lab2.parsers.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.*;

/**
 * Created by flystyle on 30.11.15.
 */
public class Main {

        public static void transformHTML(String pathXML, String pathXSL, String pathHTML) throws TransformerException {

                TransformerFactory factory = TransformerFactory.newInstance();
                StreamSource xslStream = new StreamSource(pathXSL);
                Transformer transformer = factory.newTransformer(xslStream);
                StreamSource in = new StreamSource(pathXML);
                StreamResult out = new StreamResult(pathHTML);
                transformer.transform(in, out);
        }

        public static void main(String[] args) throws IOException, TransformerException {

                String xmlPath = "/Users/flystyle/Documents/Java Projects/OOP.L2.XML&XSD&Parsers/src/univ/javatasks/lab2/xml_xsd/computer.xml/";
                String xsdPath = "/Users/flystyle/Documents/Java Projects/OOP.L2.XML&XSD&Parsers/src/univ/javatasks/lab2/xml_xsd/device.xsd";
                String xslPath = "/Users/flystyle/Documents/Java Projects/OOP.L2.XML&XSD&Parsers/src/univ/javatasks/lab2/xml_xsd/template.xsl";
                String htmlPath = "/Users/flystyle/Documents/Java Projects/OOP.L2.XML&XSD&Parsers/src/univ/javatasks/lab2/xml_xsd/example.html";

                final File xml = new File(xmlPath);
                final File schema = new File(xsdPath);
                final File xsl = new File (xslPath);
                final File renderedHTML = new File (htmlPath);



                Optional<Map<String, Device>> domResult = new DOMParser().parseDocument.apply(schema,xml);
                Optional<Map<String, Device>> saxResult = new SAXParser().parseDocument.apply(schema, xml);
                Optional<Map<String, Device>> staxResult = new StAXParser().parseDocument.apply(schema, xml);


                System.out.println("---DOM Parses---");
                domResult.get().forEach((key, value) -> System.out.println(key + " " + value));

                System.out.println("---SAX Parses---");
                saxResult.get().forEach((key, value) -> System.out.println(key + " " + value));

                System.out.println("---StAX Parses---");
                staxResult.get().forEach((key, value) -> System.out.println(key + " " + value));

                transformHTML(xmlPath, xslPath, htmlPath);
    }
}
