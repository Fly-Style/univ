package univ.javatasks.lab2.parsers;

import univ.javatasks.lab2.Device;
import univ.javatasks.lab2.app.Type;
import univ.javatasks.lab2.app.energy.*;
import univ.javatasks.lab2.app.otherdevices.Headphones;
import univ.javatasks.lab2.app.otherdevices.Keyboard;
import univ.javatasks.lab2.app.otherdevices.Mouse;
import univ.javatasks.lab2.app.ports.*;

import javax.xml.namespace.QName;
import javax.xml.stream.*;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.BiFunction;

/**
 * Created by flystyle on 30.11.15.
 */
public class StAXParser {

    private boolean isName;
    private boolean isOrigin;
    private boolean isPrice;

    private boolean isEnergy, isDevices, isPort, isCooler, isCrit;
    private boolean isKey, isMouse, isHead;

    private boolean isParsed;

    private Device device;
    private Type type;

    private TreeMap<String,Device> knifeMap = new TreeMap<>();

    public BiFunction<File,File,Optional<Map<String,Device>>> parseDocument = (xsd, xml) -> {
        if (XMLValidator.validateDocument.test(xsd, xml)) {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            try {
                XMLEventReader eventReader =  factory.createXMLEventReader(new FileInputStream(xml));
                while (eventReader.hasNext()) {
                    XMLEvent event = eventReader.nextEvent();
                    switch (event.getEventType()) {
                        case XMLStreamConstants.START_ELEMENT:
                            StartElement startElement = event.asStartElement();
                            String qname = startElement.getName().getLocalPart();

                            if (qname.equals("computer")) {
                                device = new Device();
                                type = new Type();
                                 device.setSerial(startElement.getAttributeByName(QName.valueOf("serial")).getValue());
                            }
                            else if (qname.equals("Name_")) {
                                isName = true;
                            }
                            else if (qname.equals("Origin")) {
                                isOrigin = true;
                            }
                            else if (qname.equals("Price")) {
                                isPrice = true;
                            }
                            else if (qname.equals("Port")) {
                                isPort = true;
                            }
                            else if (qname.equals("Devices")) {
                                isDevices = true;
                            }
                            else if (qname.equals("Keyboard")) {
                                isKey = true;
                            }
                            else if (qname.equals("Mouse")) {
                                isMouse = true;
                            }
                            else if (qname.equals("Headphones")) {
                                isHead = true;
                            }
                            else if (qname.equals("Energy")) {
                                isEnergy = true;
                            }

                            else if (qname.equals("IsCooler")) {
                                isCooler = true;
                            }
                            else if (qname.equals("Crit")) {
                                isCrit = true;
                            }

                            break;

                        case XMLStreamConstants.CHARACTERS:
                            Characters characters = event.asCharacters();
                            if (isName) {
                                String name = characters.getData();
                                device.setName(name);
                                isName = false;
                            }
                            if (isOrigin) {
                                device.setOrigin(characters.getData());
                                isOrigin = false;
                            }
                            if (isPrice) {
                                String ch = characters.getData();
                                System.out.println(ch);
                                device.setPrice(Short.parseShort(characters.getData()));
                                isPrice = false;
                            }

                            if (isPort) {
                                if (characters.getData().equals("COM"))
                                    type.setPort(new COMPort());
                                else if (characters.getData().equals("USB")) {
                                    type.setPort(new USBPort());
                                }
                                isDevices = false;
                            }
                            if (isKey) {
                                Keyboard keyboard = new Keyboard(characters.getData());
                                type.setKeyboard(keyboard);
                                isKey = false;
                            }

                            if (isMouse) {
                                Mouse mouse = new Mouse(characters.getData());
                                type.setMouse(mouse);
                                isMouse = false;
                            }

                            if (isHead) {
                                Headphones headd = new Headphones(characters.getData());
                                type.setHeadphones(headd);
                                isHead = false;
                            }
                            if (isEnergy) {
                                Integer integer = Integer.parseInt(characters.getData());
                                if (integer < 230)
                                    type.setEnergyUsing(new Perifery());
                                else type.setEnergyUsing(new Other());
                                isEnergy = false;
                            }

                            if (isCrit) {
                                if (characters.getData().equals("true"))
                                    type.setCrit(true);
                                else type.setCrit(false);
                                isCrit = false;
                            }

                            if (isCooler) {
                                if (characters.getData().equals("true"))
                                    type.setCooler(true);
                                else type.setCooler(false);
                                isCooler = false;
                            }

                            break;
                        case XMLStreamConstants.END_ELEMENT:
                            if (event.asEndElement().getName().getLocalPart().contains("computer")) {
                                device.setType(type);
                                knifeMap.put(device.getSerial(),device);
                            }
                    }

                }
                isParsed = true;
                return Optional.of(knifeMap);
            } catch (XMLStreamException | FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    };

}
