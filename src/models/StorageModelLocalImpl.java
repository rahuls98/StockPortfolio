package models;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Description of class.
 */
class StorageModelLocalImpl implements StorageModel {

  private final String pathToLocalStorage;

  public StorageModelLocalImpl() throws IOException {
    this.pathToLocalStorage = "./localStorage.xml";
    try {
      File localStorage = new File(pathToLocalStorage);
      localStorage.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public HashMap<String, HashMap<String, Integer>> read() {
    File newFile = new File(pathToLocalStorage);
    if (newFile.length() == 0) {
      return null;
    }
    Document document = readXmlFromFile();
    return xmlToHashmap(document);
  }

  @Override
  public void write(HashMap<String, HashMap<String, Integer>> data) {
    Document document = hashmapToXml(data);
    writeXmlToFile(document);
  }

  private Document readXmlFromFile() {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document document = builder.parse(new File(pathToLocalStorage));
      document.getDocumentElement().normalize();
      return document;
    } catch (ParserConfigurationException | SAXException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  private HashMap<String, HashMap<String, Integer>> xmlToHashmap(Document document) {
    HashMap<String, HashMap<String, Integer>> portfolios = new HashMap<>();
    NodeList portfolioList = document.getElementsByTagName("portfolio");
    Node portfolioNode;
    Element portfolioElement;
    NodeList stockList;
    HashMap<String, Integer> stocks;
    Node stock;
    Element stockElement;
    for (int i = 0; i < portfolioList.getLength(); i++) {
      portfolioNode = portfolioList.item(i);
      if (portfolioNode.getNodeType() == Node.ELEMENT_NODE) {
        portfolioElement = (Element) portfolioNode;
        stockList = portfolioElement.getElementsByTagName("stock");
        stocks = new HashMap<>();
        for (int j = 0; j < stockList.getLength(); j++) {
          stock = stockList.item(j);
          if (stock.getNodeType() == Node.ELEMENT_NODE) {
            stockElement = (Element) stock;
            stocks.put(stockElement.getAttribute("symbol"),
                    Integer.parseInt(stockElement.getAttribute("quantity")));
          }
        }
        portfolios.put(portfolioElement.getAttribute("title"), stocks);
      }
    }
    return portfolios;
  }

  private Document hashmapToXml(HashMap<String, HashMap<String, Integer>> data) {
    try {
      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
      Document document = docBuilder.newDocument();
      Element rootElement = document.createElement("portfolios");
      document.appendChild(rootElement);
      for (Map.Entry<String, HashMap<String, Integer>> entry : data.entrySet()) {
        Element portfolio = document.createElement("portfolio");
        rootElement.appendChild(portfolio);
        portfolio.setAttribute("title", entry.getKey());
        Element stocks = document.createElement("stocks");
        portfolio.appendChild(stocks);
        for (Map.Entry<String, Integer> nestedEntry : entry.getValue().entrySet()) {
          Element stock = document.createElement("stock");
          stock.setAttribute("symbol", nestedEntry.getKey());
          stock.setAttribute("quantity", Integer.toString(nestedEntry.getValue()));
          stocks.appendChild(stock);
        }
      }
      return document;
    } catch (ParserConfigurationException e) {
      throw new RuntimeException(e);
    }
  }

  private void writeXmlToFile(Document document) {
    try {
      Transformer tr = TransformerFactory.newInstance().newTransformer();
      tr.setOutputProperty(OutputKeys.INDENT, "yes");
      tr.setOutputProperty(OutputKeys.METHOD, "xml");
      tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
      tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
      tr.transform(new DOMSource(document),
              new StreamResult(new FileOutputStream(pathToLocalStorage)));

    } catch (TransformerException | IOException te) {
      System.out.println(te.getMessage());
    }
  }
}
