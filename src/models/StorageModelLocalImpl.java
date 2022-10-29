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

import entities.Portfolio;
import entities.Stock;
import entities.User;

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
      // TODO : handle createNewFile result
    } catch (IOException e) {
      // TODO handle IOException
      e.printStackTrace();
    }
  }

  @Override
  public User readUser(String userName) {
    File newFile = new File(pathToLocalStorage);
    //TODO: Return null if User not in local Storage
    if (newFile.length() == 0) {
      return null;
    }
    Document document = readXmlFromFile();
    return xmlToUser(document, userName);
  }

  @Override
  public void writeUser(User user) {
    Document document = userToXml(user);
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

  private User xmlToUser(Document document, String userName) {
    User user = new User(userName);
    NodeList userList = document.getElementsByTagName("user");
    Element targetUser = null;
    for (int i = 0; i < userList.getLength(); i++) {
      Node userNode = userList.item(i);
      Element userElement = (Element) userNode;
      if (userElement.getAttribute("name").equals(userName)) {
        targetUser = userElement;
      }
    }
    // TODO : handle null targetUser
    NodeList portfolioList = targetUser.getElementsByTagName("portfolio");
    for (int i = 0; i < portfolioList.getLength(); i++) {
      Node portfolioNode = portfolioList.item(i);
      Element portfolioElement = (Element) portfolioNode;
      Portfolio portfolio = new Portfolio(portfolioElement.getAttribute("title"));
      NodeList stockList = portfolioElement.getElementsByTagName("stock");
      for (int j = 0; j < stockList.getLength(); j++) {
        Node stockNode = stockList.item(j);
        Element stockElement = (Element) stockNode;
        Stock stock = new Stock(stockElement.getAttribute("symbol"));
        portfolio.addStock(stock, Integer.parseInt(stockElement.getAttribute("quantity")));
      }
      user.addPortfolio(portfolio);
    }
    return user;
  }

  private Document userToXml(User userObj) {
    try {
      DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
      Document document = docBuilder.newDocument();
      Element rootElement = document.createElement("users");
      document.appendChild(rootElement);
      Element user = document.createElement("user");
      rootElement.appendChild(user);
      user.setAttribute("name", userObj.getName());
      Element portfolios = document.createElement("portfolios");
      user.appendChild(portfolios);
      for (Map.Entry<String, Portfolio> portfoliosObj : userObj.getPortfolios().entrySet()) {
        Element portfolio = document.createElement("portfolio");
        portfolios.appendChild(portfolio);
        portfolio.setAttribute("title", portfoliosObj.getKey());
        Element stocks = document.createElement("stocks");
        portfolio.appendChild(stocks);
        for (Map.Entry<Stock, Integer> stocksObj :
                portfoliosObj.getValue().getStocks().entrySet()) {
          Element stock = document.createElement("stock");
          stock.setAttribute("symbol", stocksObj.getKey().getTicker());
          stock.setAttribute("quantity", Integer.toString(stocksObj.getValue()));
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
