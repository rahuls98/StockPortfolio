package models;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import entities.Portfolio;
import entities.Stock;
import entities.User;

/**
 * Represents a storage model that uses XML as its storage mechanism.
 */
public class StorageModelLocalImpl implements StorageModel {

  private final String pathToLocalStorage;
  private ArrayList<User> users;

  /**
   * Returns an object of the XML file storage mechanism.
   */
  public StorageModelLocalImpl() throws IOException {
    this.pathToLocalStorage = "./localStorage.xml";
    this.users = new ArrayList<>();
    try {
      File localStorage = new File(pathToLocalStorage);
      if (localStorage.createNewFile()) {
        this.initializeLocalStorage();
      } else {
        FileModelXmlImpl xmlHandler = new FileModelXmlImpl();
        xmlHandler.readFile(pathToLocalStorage);
        Document document = xmlHandler.getDocument();
        this.xmlToUsers(document);
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
    boolean userExists = false;
    int i;
    for (i = 0; i < this.users.size(); i++) {
      if (this.users.get(i).getName().equals(userName)) {
        userExists = true;
        break;
      }
    }
    if (userExists) {
      return this.users.get(i);
    } else {
      return new User(userName);
    }
  }

  @Override
  public void writeUser(User user) {
    if (user == null) {
      throw new IllegalArgumentException("User cannot be null!");
    }
    boolean userExists = false;
    int i;
    for (i = 0; i < this.users.size(); i++) {
      if (this.users.get(i).getName().equals(user.getName())) {
        userExists = true;
        break;
      }
    }
    if (userExists) {
      users.remove(i);
      users.add(user);
    } else {
      users.add(user);
    }
    FileModelXmlImpl xmlHandler = new FileModelXmlImpl();
    xmlHandler.setDocument(this.usersToXml());
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

  private void xmlToUsers(Document document) {
    NodeList userList = document.getElementsByTagName("user");
    for (int i = 0; i < userList.getLength(); i++) {
      Node userNode = userList.item(i);
      Element userElement = (Element) userNode;
      User user = new User(userElement.getAttribute("name"));
      NodeList portfolioList = userElement.getElementsByTagName("portfolio");
      for (int j = 0; j < portfolioList.getLength(); j++) {
        Node portfolioNode = portfolioList.item(j);
        Element portfolioElement = (Element) portfolioNode;
        Portfolio portfolio = new Portfolio(portfolioElement.getAttribute("title"));
        NodeList stockList = portfolioElement.getElementsByTagName("stock");
        for (int k = 0; k < stockList.getLength(); k++) {
          Node stockNode = stockList.item(k);
          Element stockElement = (Element) stockNode;
          Stock stock = new Stock(stockElement.getAttribute("symbol"));
          portfolio.addStock(stock, Integer.parseInt(stockElement.getAttribute("quantity")));
        }
        user.addPortfolio(portfolio);
      }
      this.users.add(user);
    }
  }

  private Document usersToXml() {
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
    for (User userObj : this.users) {
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
        HashMap<Stock, Integer> portfolioStocks = portfoliosObj.getValue().getStocks();
        for (Map.Entry<Stock, Integer> stocksObj : portfolioStocks.entrySet()) {
          Element stock = document.createElement("stock");
          stock.setAttribute("symbol", stocksObj.getKey().getTicker());
          stock.setAttribute("quantity", Integer.toString(stocksObj.getValue()));
          stocks.appendChild(stock);
        }
      }
    }
    document.normalize();
    return document;
  }
}
