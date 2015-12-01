package univ.javatasks.lab2.parsers;

/**
 * Created by flystyle on 28.11.15.
 */
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
import univ.javatasks.lab2.Device;
import univ.javatasks.lab2.app.*;
import univ.javatasks.lab2.app.energy.EnergyUsing;
import univ.javatasks.lab2.app.energy.Other;
import univ.javatasks.lab2.app.energy.Perifery;
import univ.javatasks.lab2.app.otherdevices.Devices;
import univ.javatasks.lab2.app.otherdevices.Headphones;
import univ.javatasks.lab2.app.otherdevices.Keyboard;
import univ.javatasks.lab2.app.otherdevices.Mouse;
import univ.javatasks.lab2.app.ports.COMPort;
import univ.javatasks.lab2.app.ports.Port;
import univ.javatasks.lab2.app.ports.USBPort;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.Function;

public class DOMParser {

    public Function<File,Optional<Document>> convertXMLFileToDocument = xmlFile -> {
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);
            document.getDocumentElement().normalize();
            return Optional.ofNullable(document);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    };



    public BiFunction<File,File,Optional<Map<String,Device>>> parseDocument = (xsd, xml) -> {
        Map<String,Device> deviceMap = new TreeMap<>(String::compareToIgnoreCase);
        if (XMLValidator.validateDocument.test(xsd,xml)) {
            Optional<Document> document = convertXMLFileToDocument.apply(xml);
            String expression = "/device/computer";
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();
            NodeList deviceList = null;
            try {
                deviceList = (NodeList) xPath.compile(expression).evaluate(document.get(), XPathConstants.NODESET);
            } catch (XPathExpressionException e) {
                e.printStackTrace();
                return Optional.empty();
            }
            if (deviceList == null) {
                return Optional.empty();
            }
            for (int nodeCounter = 0; nodeCounter < deviceList.getLength(); nodeCounter++) {
                final Device device = new Device();
                Node xmldevice = deviceList.item(nodeCounter);
                device.setSerial(xmldevice.getAttributes().getNamedItem("serial").getTextContent());
                NodeList xmldeviceElements = xmldevice.getChildNodes();
                for (int xmldeviceElementsCounter = 0; xmldeviceElementsCounter <xmldeviceElements.getLength(); xmldeviceElementsCounter++) {
                    Node element = xmldeviceElements.item(xmldeviceElementsCounter);
                    if (!(element instanceof Text)) {

                        if (element.getNodeName().contains("Name_")) {
                            device.setName(element.getTextContent());
                        }
                        if (element.getNodeName().contains("Origin")) {
                            device.setOrigin(element.getTextContent());
                        }

                        if (element.getNodeName().contains("Price")) {
                            device.setPrice(Short.parseShort(element.getTextContent()));
                        }

                        if (element.getNodeName().contains("tipe")) {
                            Type currentType = new Type();
                            NodeList currentTypeList = element.getChildNodes();
                            for (int currentTypeCounter = 0; currentTypeCounter < currentTypeList.getLength(); currentTypeCounter++) {
                                Node type = currentTypeList.item(currentTypeCounter);
                                if (!(type instanceof Text)) {
                                    if (type.getNodeName().contains("IsCooler")) {
                                        if (type.getTextContent().equals("true"))
                                            currentType.setCooler(true);
                                        else currentType.setCooler(false);
                                    }

                                    if (type.getNodeName().contains("Crit")) {
                                        if (type.getTextContent().equals("true"))
                                        currentType.setCrit(true);
                                        else currentType.setCrit(false);
                                    }


                                    if (type.getNodeName().contains("Port")) {
                                        Port port;
                                        NodeList portNodeList = type.getChildNodes();
                                        for (int portCounter = 0; portCounter < portNodeList.getLength(); portCounter++) {
                                            Node portNode = portNodeList.item(portCounter);
                                            if ((portNode instanceof Text)) {
                                                if (portNode.getTextContent().contains("COM")) {
                                                    port = new COMPort();
                                                    currentType.setPort(port);
                                                }

                                                if (portNode.getTextContent().contains("USB")) {
                                                    port = new USBPort();
                                                    currentType.setPort(port);
                                                }
                                            }
                                        }
                                    }

                                    if (type.getNodeName().contains("Devices")) {
                                        NodeList deviceNodeList = type.getChildNodes();
                                        Keyboard devK; Mouse devM; Headphones devH;
                                        for (int deviceCounter = 0; deviceCounter < deviceNodeList.getLength(); deviceCounter++) {
                                            Node deviceNode = deviceNodeList.item(deviceCounter);
                                            if (!(deviceNode instanceof Text)) {
                                                if (deviceNode.getNodeName().contains("Keyboard")) {
                                                    devK = new Keyboard(deviceNode.getTextContent());
                                                    currentType.setKeyboard(devK);

                                                }

                                                if (deviceNode.getNodeName().contains("Mouse")) {
                                                    devM = new Mouse(deviceNode.getTextContent());
                                                    currentType.setMouse(devM);
                                                }

                                                if (deviceNode.getNodeName().contains("Headphones")) {
                                                    devH = new Headphones(deviceNode.getTextContent());
                                                    currentType.setHeadphones(devH);
                                                }
                                            }
                                        }
                                    }

                                    if (type.getNodeName().contains("Energy")) {
                                        int watt = Integer.parseInt(type.getTextContent());
                                        EnergyUsing energyUsing;
                                        if (watt > 0 && watt <= 230) {
                                            energyUsing = new Perifery();
                                        }
                                        else if (watt > 230) energyUsing = new Other();
                                        else energyUsing = null;

                                        currentType.setEnergyUsing(energyUsing);
                                    }


                                }
                                device.setType(currentType);
                            }

                        }
                    }
                }
                deviceMap.put(device.getSerial(),device);
            }
            return Optional.ofNullable(deviceMap);
        }
        return Optional.empty();
    };
}
