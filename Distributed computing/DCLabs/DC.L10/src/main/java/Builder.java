import com.jamesmurty.utils.XMLBuilder;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author is flystyle
 * Created on 26.03.16.
 */
public class Builder {

    private XMLBuilder builder;
    private DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
    private File file;

    public Builder(File file) {
        builder = null;
        this.file = file;
        builder = null; //XMLBuilder.create(file.getPath());
    }

    public Builder(XMLBuilder builder) {
        this.builder = builder;
    }

    public void addInfo (Product productToAdd) {
        DocumentBuilder documentBuilder = null;


        try {
            documentBuilder = builderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);

            Element element = document.createElement("product");

            Element name = document.createElement("name");
            name.appendChild(document.createTextNode(productToAdd.getName()));
            element.appendChild(name);

            Element id = document.createElement("id");
            id.appendChild(document.createTextNode(String.valueOf(productToAdd.getId())));
            element.appendChild(name);

            Element price = document.createElement("price");
            name.appendChild(document.createTextNode(String.valueOf(productToAdd.getPrice())));
            element.appendChild(name);



            Element root = document.getDocumentElement();
            if (root.getTagName().equals("Shop")) {
                NodeList listCategories = root.getElementsByTagName("Category");
                for (int i = 0; i < listCategories.getLength(); i++) {
                    Element node = (Element)listCategories.item(i);
                    if (node.getAttribute("id").equals(String.valueOf(productToAdd.getCategoryId()))) {
                        node.appendChild(element);
                    }
                }
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addInfo (Category category) {
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = builderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);

            Element root = document.getDocumentElement();

            Element newInfo = document.createElement("Category");

            Attr id = document.createAttribute("id");
            id.appendChild(id);
            id.setValue(String.valueOf(category.getCategoryName()));
            newInfo.appendChild(id);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseDocument () throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
        System.out.println(file.getName());
        InputStream stream = new FileInputStream("/Users/flystyle/Documents/6 semester/Distributed computing/DCLabs/DC.L10/src/main/xml/shop.xml");
        Document document = documentBuilder.parse(stream);
        {
            Element root = document.getDocumentElement();
            if (root.getTagName().equals("Shop")) {
                NodeList listCategories = root.getElementsByTagName("Category");
                for (int i = 0; i < listCategories.getLength(); ++i) {
                    Element caregory = (Element)listCategories.item(i);
                    NodeList productList = root.getElementsByTagName("product");
                    for (int j = 0; j < productList.getLength(); ++j) {
                        Element product = (Element)productList.item(j);
                        String prodName = product.getAttribute("name");
                        int id = Integer.valueOf(product.getAttribute("id"));
                        int price = Integer.valueOf(product.getAttribute("price"));
                        System.out.println(caregory.toString());
                        System.out.println("Id: " + id + ", name :" + prodName + ", price :" + price);
                    }

                }
            }
        }
    }

    public static void main(String[] args) {
        File document = new File("shop.xml");
        System.out.println(document.getPath());
        System.out.println(document.exists() + " " + document.canRead() + " " + document.canWrite() + " " + document.isFile());
        Builder builder = new Builder(document);
        try {
            builder.parseDocument();
            builder.addInfo(new Product(30, "lol", 100, new Category(1)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

