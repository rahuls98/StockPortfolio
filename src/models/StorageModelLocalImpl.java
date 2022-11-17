package models;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Represents a storage model that uses XML as its storage mechanism.
 */
class StorageModelLocalImpl implements StorageModel {

  private final String pathToLocalStorage;
  private final ArrayList<User> users;

  /**
   * Returns an object of the XML file storage mechanism.
   */
  public StorageModelLocalImpl() throws Exception {
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
    } catch (Exception e) {
      throw e;
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

  private void xmlToUsers(Document document) throws Exception {
    try {
      NodeList userList = document.getElementsByTagName("user");
      for (int i = 0; i < userList.getLength(); i++) {
        Node userNode = userList.item(i);
        Element userElement = (Element) userNode;
        User user = new User(userElement.getAttribute("name"));
        NodeList portfolioList = userElement.getElementsByTagName("portfolio");
        for (int j = 0; j < portfolioList.getLength(); j++) {
          Node portfolioNode = portfolioList.item(j);
          Element portfolioElement = (Element) portfolioNode;
          String portfolioName = portfolioElement.getAttribute("title");
          String type = portfolioElement.getAttribute("type");
          PortfolioType portfolioType = (type.equals("inflexible")) ? PortfolioType.INFLEXIBLE :
                  PortfolioType.FLEXIBLE;
          List<Order> portfolioOrders = new ArrayList<>();
          Portfolio portfolio = null;
          NodeList orderList = portfolioElement.getElementsByTagName("order");
          if (type.equals("inflexible")) {
            for (int k = 0; k < orderList.getLength(); k++) {
              Node orderNode = orderList.item(k);
              Element orderElement = (Element) orderNode;
              String action = orderElement.getAttribute("action");
              if (action.equals("SELL")) {
                throw new Exception("Invalid portfolio action!");
              }
              Action orderAction = (action.equals("BUY")) ? Action.BUY : Action.SELL;
              String date = orderElement.getAttribute("date");
              float commission = Float.parseFloat(orderElement.getAttribute("commission"));
              if (commission < 0) {
                throw new Exception();
              }
              Order order = new Order(orderAction, LocalDate.parse(date), commission);
              NodeList stockList = orderElement.getElementsByTagName("stock");
              HashMap<String, Integer> stocks = new HashMap<>();
              for (int l = 0; l < stockList.getLength(); l++) {
                Node stockNode = stockList.item(l);
                Element stockElement = (Element) stockNode;
                stocks.put(stockElement.getAttribute("symbol"),
                        Integer.parseInt(stockElement.getAttribute("quantity")));
              }
              order.addStocks(stocks);
              portfolioOrders.add(order);
            }
            portfolio = new Portfolio(portfolioName, portfolioType, portfolioOrders);
          } else if (type.equals("flexible")) {
            portfolio = new Portfolio(portfolioName, portfolioType, portfolioOrders);
            for (int k = 0; k < orderList.getLength(); k++) {
              Node orderNode = orderList.item(k);
              Element orderElement = (Element) orderNode;
              String action = orderElement.getAttribute("action");
              Action orderAction = (action.equals("BUY")) ? Action.BUY : Action.SELL;
              String date = orderElement.getAttribute("date");
              float commission = Float.parseFloat(orderElement.getAttribute("commission"));
              Order order = new Order(orderAction, LocalDate.parse(date), commission);
              NodeList stockList = orderElement.getElementsByTagName("stock");
              HashMap<String, Integer> stocks = new HashMap<>();
              for (int l = 0; l < stockList.getLength(); l++) {
                Node stockNode = stockList.item(l);
                Element stockElement = (Element) stockNode;
                stocks.put(stockElement.getAttribute("symbol"),
                        Integer.parseInt(stockElement.getAttribute("quantity")));
              }
              order.addStocks(stocks);
              portfolioOrders.add(order);
              if (!portfolio.placeOrder(order)) {
                throw new RuntimeException("Invalid order book");
              }
            }
          }
          user.addNewPortfolio(portfolio);
        }
        this.users.add(user);
      }
    } catch (Exception e) {
      throw e;
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
      for (Map.Entry<String, PortfolioInstanceModel> portfoliosObj : userObj.getPortfolios().entrySet()) {
        Element portfolio = document.createElement("portfolio");
        portfolios.appendChild(portfolio);
        portfolio.setAttribute("title", portfoliosObj.getKey());
        portfolio.setAttribute("type", portfoliosObj.getValue().getType().toString().toLowerCase());
        Element orders = document.createElement("orders");
        portfolio.appendChild(orders);
        ArrayList<Order> orderBook = portfoliosObj.getValue().getOrderBook();
        for (Order orderObj: orderBook) {
          Element order = document.createElement("order");
          order.setAttribute("action", orderObj.getAction().name());
          order.setAttribute("date", orderObj.getDate().toString());
          order.setAttribute("commission", Float.toString(orderObj.getCommission()));
          Element stocks = document.createElement("stocks");
          order.appendChild(stocks);
          HashMap<String, Integer> orderStocks = orderObj.getStocks();
          for (Map.Entry<String, Integer> stocksObj : orderStocks.entrySet()) {
            Element stock = document.createElement("stock");
            stock.setAttribute("symbol", stocksObj.getKey());
            stock.setAttribute("quantity", Integer.toString(stocksObj.getValue()));
            stocks.appendChild(stock);
          }
          orders.appendChild(order);
        }
      }
    }
    document.normalize();
    return document;
  }
}
