package univ.javatasks.lab2.parsers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import univ.javatasks.lab2.Device;
import univ.javatasks.lab2.app.Type;
import univ.javatasks.lab2.app.energy.Other;
import univ.javatasks.lab2.app.energy.Perifery;
import univ.javatasks.lab2.app.otherdevices.Headphones;
import univ.javatasks.lab2.app.otherdevices.Keyboard;
import univ.javatasks.lab2.app.otherdevices.Mouse;
import univ.javatasks.lab2.app.ports.COMPort;
import univ.javatasks.lab2.app.ports.Port;
import univ.javatasks.lab2.app.ports.USBPort;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.BiFunction;

/**
 * Created by flystyle on 29.11.15.
 */
public class SAXParser {

    private class UserHandler extends DefaultHandler {

        private boolean isName;
        private boolean isOrigin;
        private boolean isPrice;
        private boolean isType;

        private boolean isEnergy, isDevices, isPort, isCooler, isCrit;
        private boolean isKey, isMouse, isHead;

        private boolean isParsed;

        private Device device;
        private Type type;

        private TreeMap <String,Device> deviceTreeMap;

        public Optional<TreeMap<String,Device>> getResult() {
            return isParsed ? Optional.ofNullable(deviceTreeMap) : Optional.empty();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (qName.contains("computer")) {
                device = new Device();
                type = new Type();
                device.setSerial(attributes.getValue("serial"));
            }

            if (qName.contains("Name_"))
                isName = true;

            if (qName.contains("Origin")) {
                isOrigin = true;
            }

            if (qName.contains("Price")) {
                isPrice = true;
            }

            if (qName.contains("Port")) {
                isPort = true;
            }

            if (qName.contains("Devices")) {
                isDevices = true;
            }

            if (qName.contains("Keyboard"))
                isKey = true;
            if (qName.contains("Mouse"))
                isMouse = true;
            if (qName.contains("Headphones"))
                isHead = true;

            if (qName.contains("Energy")) {
                isEnergy = true;
            }

            if (qName.contains("IsCooler")) {
                isCooler = true;
            }

            if (qName.contains("Crit")) {
                isCrit = true;
            }

        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            if (isName) {
                String name = new String(ch,start,length);
                device.setName(name);
                isName = false;
            }

            if (isOrigin) {
                device.setOrigin(new String(ch,start,length));
                isOrigin = false;
            }

            if (isPrice) {
                device.setPrice(Short.parseShort(new String(ch, start, length)));
                isPrice = false;
            }

            if (isPort) {
                String str = new String(ch, start, length);
                if (str.equals("COM")) {
                    type.setPort(new COMPort());
                }

                if (str.equals("USB"))
                    type.setPort(new USBPort());
                isPort = false;
            }

                if (isKey) {
                    String key = new String(ch, start, length);
                    Keyboard keys = new Keyboard(key);
                    type.setKeyboard(keys);
                    isKey = false;
                }

                if (isMouse) {
                    String m = new String(ch, start, length);
                    Mouse mouse = new Mouse(m);
                    type.setMouse(mouse);
                    isMouse = false;
                }

                if (isHead) {
                    String head = new String(ch, start, length);
                    Headphones heads = new Headphones(head);
                    type.setHeadphones(heads);
                    isHead = false;
                }
                isDevices = false;

            if (isEnergy) {
                String ins = new String(ch, start, length);
                Integer in = new Integer(ins);
                if (in < 230)
                    type.setEnergyUsing(new Perifery());
                else type.setEnergyUsing(new Other());
                isEnergy = false;
            }

            if (isCooler) {
                String val = new String(ch, start, length);
                if (val.equals("true"))
                    type.setCooler(true);
                else type.setCooler(false);

                isCooler = false;
            }

            if (isCrit) {
                String val = new String(ch, start, length);
                if (val.equals("true"))
                    type.setCrit(true);
                else type.setCrit(false);

                isCrit = false;
            }

        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (qName.equals("computer")) {
                device.setType(type);
                deviceTreeMap.put(device.getSerial(),device);
            }
        }

        @Override
        public void startDocument() throws SAXException {
            deviceTreeMap = new TreeMap<>(String::compareToIgnoreCase);
        }

        @Override
        public void endDocument() throws SAXException {
            isParsed = true;
        }
    }

    public BiFunction<File,File,Optional<Map<String, Device>>> parseDocument = (xsd, xml) -> {
        if (XMLValidator.validateDocument.test(xsd,xml)){
            try {
                SAXParserFactory factory = SAXParserFactory.newInstance();
                javax.xml.parsers.SAXParser saxParser = factory.newSAXParser();
                UserHandler userHandler = new UserHandler();
                saxParser.parse(xml,userHandler);
                TreeMap<String, Device> knifeMap = userHandler.getResult().get();
                return Optional.ofNullable(knifeMap);
            } catch (ParserConfigurationException | SAXException | IOException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();

    };
}