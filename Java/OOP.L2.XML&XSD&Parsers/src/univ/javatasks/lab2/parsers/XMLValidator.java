package univ.javatasks.lab2.parsers;

/**
 * Created by flystyle on 28.11.15.
 */
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.function.BiPredicate;
public interface XMLValidator {

    BiPredicate<File,File> validateDocument =  (xsd,xml) -> {
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = null;
            schema = schemaFactory.newSchema(xsd);
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xml));
        } catch (SAXException | IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    };
}
