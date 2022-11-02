package models;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Description of class.
 */
class StorageModelLocalImpl implements StorageModel {

  private final String pathToLocalStorage;

  public StorageModelLocalImpl() throws IOException {
    this.pathToLocalStorage = "./localStorage.xml";
    try {
      File localStorage = new File(pathToLocalStorage);
      if (localStorage.createNewFile()) {
        this.initializeLocalStorage();
      }
    } catch (IOException e) {
      throw new IOException(e);
    }
  }

  @Override
  public User readUser(String userName) {
    File newFile = new File(pathToLocalStorage);
    if (newFile.length() == 0) {
      return null;
    }
    FileModelXmlImpl xmlHandler = new FileModelXmlImpl();
    xmlHandler.readFile(pathToLocalStorage);
    Document document = xmlHandler.getDocument();
    return getUserFromXml(document, userName);
  }

  @Override
  public void writeUser(User user) {
    if (user == null) {
      throw new IllegalArgumentException("User cannot be null!");
    }
    FileModelXmlImpl xmlHandler = new FileModelXmlImpl();
    xmlHandler.readFile(pathToLocalStorage);
    Document document = this.addUserToXml(user, xmlHandler.getDocument());
    xmlHandler.setDocument(document);
    xmlHandler.writeFile(pathToLocalStorage);
  }

  private void initializeLocalStorage() {
    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = null;
    try {
      docBuilder = docFactory.newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      throw new RuntimeException(e);
    }
    Document document = docBuilder.newDocument();
    Element rootElement = document.createElement("users");
    document.appendChild(rootElement);
    FileModelXmlImpl xmlHandler = new FileModelXmlImpl();
    xmlHandler.setDocument(document);
    xmlHandler.writeFile(this.pathToLocalStorage);
  }

  private User getUserFromXml(Document document, String userName) {
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
    if (targetUser == null) {
      return new User(userName);
    }
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
    // TODO : handle missing nodes
    return user;
  }

  private Document addUserToXml(User userObj, Document document) {
    NodeList usersList = document.getElementsByTagName("users");
    Node usersNode = usersList.item(0);
    // Element usersElement = (Element) usersNode;
    Element user = document.createElement("user");
    usersNode.appendChild(user);
    user.setAttribute("name", userObj.getName());
    Element portfolios = document.createElement("portfolios");
    user.appendChild(portfolios);
    for (Map.Entry<String, Portfolio> portfoliosObj : userObj.getPortfolios().entrySet()) {
      Element portfolio = document.createElement("portfolio");
      portfolios.appendChild(portfolio);
      portfolio.setAttribute("title", portfoliosObj.getKey());
      Element stocks = document.createElement("stocks");
      portfolio.appendChild(stocks);
      HashMap<Stock, Integer> portfolioStocks = portfoliosObj.getValue().getStocks();
      for (Map.Entry<Stock, Integer> stocksObj : portfolioStocks.entrySet()) {
        Element stock = document.createElement("stock");
        stock.setAttribute("symbol", stocksObj.getKey().getTicker());
        stock.setAttribute("quantity", Integer.toString(stocksObj.getValue()));
        stocks.appendChild(stock);
      }
    }
    return document;
  }
}
