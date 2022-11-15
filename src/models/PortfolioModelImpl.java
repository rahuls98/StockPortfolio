package models;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Represents a class that manages all the functionalities of a stock portfolio application for
 * different users using an XML file storage.
 */
public class PortfolioModelImpl implements PortfolioModel {

  private final User user;
  private final StorageModel store;
  private final HashSet<String> tickerSet;

  /**
   * Returns an object of the PortfolioModelImpl class.
   *
   * @param userName The user whose portfolio is to be managed.
   */
  public PortfolioModelImpl(String userName) throws Exception {
    try {
      this.store = new StorageModelLocalImpl();
    } catch (Exception e) {
      throw new Exception(e);
    }
    this.user = this.store.readUser(userName);
    APIModel model = new APIModelImpl();
    tickerSet = model.getValidTickers();
  }

  @Override
  public void addPortfolio(String portfolioName) {
    return;
  }

  @Override
  public void addFlexiblePortfolio(String portfolioName) {
    this.user.addPortfolio(new Portfolio(portfolioName, PortfolioType.FLEXIBLE, null));
  }

  //TODO: It takes, type, Hashmap of stock & quantity.
  @Override
  public void addInflexiblePortfolio(String portfolioName, HashMap<String, Integer> stocks) {
    List<Order> initialOrders = new ArrayList<>();
    for (Map.Entry<String, Integer> stock : stocks.entrySet()) {
      initialOrders.add(new Order(Action.BUY, LocalDate.of(2011, 03, 02), 0.00f));
    }
    this.user.addPortfolio(new Portfolio(portfolioName, PortfolioType.FLEXIBLE, initialOrders));
  }

  @Override
  public PortfolioInstanceModel getPortfolio(String portfolioName) {
    return this.user.getPortfolios().get(portfolioName);
  }


  @Override
  public void addOrderToPortfolio(String portfolio, Order o) {
    this.user.getPortfolios().get(portfolio).placeOrder(o);
  }

  @Override
  public Float getCostBasis(String portfolioName, String date) {
    return this.getPortfolio(portfolioName).getCostBasis(LocalDate.parse(date));
  }

  @Override
  public Order createOrder(String date, String action, float c, HashMap<String, Integer> stocks) {
    Order o;
    if (action.equals("BUY")) {
      o = new Order(Action.BUY, LocalDate.parse(date), c);
    } else {
      o = new Order(Action.SELL, LocalDate.parse(date), c);
    }
    o.addStocks(stocks);
    return o;
  }

  @Override
  public String[] getPortfolios() {
    return this.user.getPortfolios().keySet().toArray(new String[0]);
  }

  @Override
  public String[] getFlexiblePortfolios() {
    return this.user.getFlexiblePortfolios().keySet().toArray(new String[0]);
  }

  @Override
  public String[] getInflexiblePortfolios() {
    return this.user.getInflexiblePortfolios().keySet().toArray(new String[0]);
  }

  @Override
  public HashMap<String, Float> getPortfolioValues(String portfolioName, String date) {
    return this.user.getPortfolios().get(portfolioName).getValue(date);
  }

  @Override
  public Float getPortfolioTotal(String portfolioName, String date) {
    return this.user.getPortfolios().get(portfolioName).getTotalValue(
            this.user.getPortfolios().get(portfolioName).getValue(date));
  }

  @Override
  public Boolean isValidTicker(String ticker) {
    return this.tickerSet.contains(ticker);
  }


//
//  @Override
//  public Portfolio loadPortfolioFromXml(String pathToFile) {
//    try {
//      int quantity;
//      FileModelXmlImpl xmlFileHandler = new FileModelXmlImpl();
//      xmlFileHandler.readFile(pathToFile);
//      Document document = xmlFileHandler.getDocument();
//      NodeList nodeList = document.getElementsByTagName("portfolio");
//      Node portfolioNode = nodeList.item(0);
//      Element portfolioElement = (Element) portfolioNode;
//      String portfolioName = portfolioElement.getAttribute("title");
//      // String portfolioType = portfolioElement.getAttribute("type");
//      Portfolio portfolioObj = new Portfolio(portfolioName);
//      NodeList stockList = portfolioElement.getElementsByTagName("stock");
//      for (int j = 0; j < stockList.getLength(); j++) {
//        Node stockNode = stockList.item(j);
//        Element stockElement = (Element) stockNode;
//        if (!(this.isValidTicker(stockElement.getAttribute("symbol")))) {
//          throw new IllegalArgumentException("Invalid Ticker");
//        }
//        try {
//          quantity = Integer.parseInt(stockElement.getAttribute("quantity"));
//          if (quantity <= 0) {
//            throw new IllegalArgumentException("Invalid Quantity");
//          }
//        } catch (NumberFormatException e) {
//          throw new IllegalArgumentException("Invalid Quantity");
//        }
//        Stock stock = new Stock(stockElement.getAttribute("symbol"));
//        portfolioObj.addStock(stock, quantity);
//      }
//      return portfolioObj;
//    } catch (Exception e) {
//      throw new IllegalArgumentException("Invalid XML!");
//    }
//  }

//  @Override
//  public Portfolio_old loadPortfolioFromXml(String pathToFile) {
//    try {
//      int quantity;
//      FileModelXmlImpl xmlFileHandler = new FileModelXmlImpl();
//      xmlFileHandler.readFile(pathToFile);
//      Document document = xmlFileHandler.getDocument();
//      NodeList nodeList = document.getElementsByTagName("portfolio");
//      Node portfolioNode = nodeList.item(0);
//      Element portfolioElement = (Element) portfolioNode;
//      String portfolioName = portfolioElement.getAttribute("title");
//      String portfolioType = portfolioElement.getAttribute("type");
//      Portfolio portfolioObj = new Portfolio(portfolioName);
//      NodeList orderList = portfolioElement.getElementsByTagName("order");
//      for (int k = 0; k < orderList.getLength(); k++) {
//        Node orderNode = orderList.item(k);
//        Element orderElement = (Element) orderNode;
//        String action = orderElement.getAttribute("action");
//        if (portfolioType.equals("inflexible") && action.equals("SELL")) {
//          throw new Exception("Invalid portfolio action!");
//        }
//        Action orderAction = (action.equals("BUY")) ? Action.BUY : Action.SELL;
//        String date = orderElement.getAttribute("date");
//        float commission = Float.parseFloat(orderElement.getAttribute("commission"));
//        Order order = new Order(orderAction, LocalDate.parse(date), commission);
//        NodeList stockList = orderElement.getElementsByTagName("stock");
//        HashMap<String, Integer> stocks = new HashMap<>();
//        for (int l = 0; l < stockList.getLength(); l++) {
//          Node stockNode = stockList.item(l);
//          Element stockElement = (Element) stockNode;
//          stocks.put(stockElement.getAttribute("symbol"),
//                  Integer.parseInt(stockElement.getAttribute("quantity")));
//        }
//        order.addStocks(stocks);
//        if (!portfolioObj.placeOrder(order)) {
//          throw new RuntimeException("Invalid order book");
//        }
//      }
//      System.out.println(portfolioObj);
//      return null;
//    } catch (Exception e) {
//      System.out.println(e);
//      throw new IllegalArgumentException("Invalid XML!");
//    }
//  }

  @Override
  public String loadPortfolioNameFromXML(String pathToFile) {
    try {
      FileModelXmlImpl xmlFileHandler = new FileModelXmlImpl();
      xmlFileHandler.readFile(pathToFile);
      Document document = xmlFileHandler.getDocument();
      NodeList nodeList = document.getElementsByTagName("portfolio");
      Node portfolioNode = nodeList.item(0);
      Element portfolioElement = (Element) portfolioNode;
      return portfolioElement.getAttribute("title");
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid XML!");
    }
  }

  @Override
  public boolean isValidDate(String date) {
    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
    try {
      LocalDate.parse(date, formatter);
    } catch (DateTimeParseException e) {
      return false;
    }
    return true;
  }

  //TODO: remove old functions
  @Override
  public void addStock(String portfolioName, String stockTicker, int stockQuantity) {
    Stock stock = new Stock(stockTicker);
    this.getPortfolio(portfolioName).addStock(stock, stockQuantity);
  }

  public void addOrder(String portfolioName, Order o) {
    this.user.getPortfolios().get(portfolioName).placeOrder(o);
  }

//  @Override
//  public void addPortfolioToUser(Portfolio_old portfolioOld, String portfolioName) {
//    portfolioOld.setName(portfolioName);
//    this.user.addPortfolio(portfolioOld);
//  }

  @Override
  public void persist() {
    this.store.writeUser(this.user);
  }

  @Override
  public HashSet<String> getStockTickersInPortfolio(String portfolioName) {
    return this.getPortfolio(portfolioName).getStockNames();
  }

  @Override
  public HashMap<String, Integer> getStockQuantitiesInPortfolio(String portfolioName, String date) {
    return this.getPortfolio(portfolioName).getStockCompositionOnDate(LocalDate.parse(date));
  }

  @Override
  public void getPerformance(String portfolioName, String date1, String date2) {
    this.getPortfolio(portfolioName).dateRangeSplitter(date1, date2);
  }
}