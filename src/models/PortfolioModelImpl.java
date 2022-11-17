package models;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
  public void addPortfolioToUser(Portfolio portfolio, String portfolioName) {
    portfolio.setName(portfolioName);
    this.user.addPortfolio(portfolio);
  }

  @Override
  public void addFlexiblePortfolio(String portfolioName) {
    this.user.addPortfolio(new Portfolio(portfolioName, PortfolioType.FLEXIBLE, new ArrayList<>()));
  }

  //TODO: It takes, type, Hashmap of stock & quantity.
  @Override
  public void addInflexiblePortfolio(String portfolioName, HashMap<String, Integer> stocks) {
    List<Order> initialOrders = new ArrayList<>();
    Order o = new Order(Action.BUY, LocalDate.of(2011, 03, 02), 0.00f);
    o.addStocks(stocks);
    initialOrders.add(o);
    this.user.addPortfolio(new Portfolio(portfolioName, PortfolioType.INFLEXIBLE, initialOrders));
  }

  @Override
  public PortfolioInstanceModel getPortfolio(String portfolioName) {
    return this.user.getPortfolios().get(portfolioName);
  }


  private Boolean addOrderToPortfolio(String portfolio, Order o) {
    return this.user.getPortfolios().get(portfolio).placeOrder(o);
  }

  @Override
  public Float getCostBasis(String portfolioName, String date) {
    return this.getPortfolio(portfolioName).getCostBasis(LocalDate.parse(date));
  }


  private Order createOrder(String date, String action, float c, HashMap<String, Integer> stocks) {
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

  @Override
  public void loadPortfolioFromXml(String pathToFile) {
    try {
      int quantity;
      FileModelXmlImpl xmlFileHandler = new FileModelXmlImpl();
      xmlFileHandler.readFile(pathToFile);
      Document document = xmlFileHandler.getDocument();
      NodeList nodeList = document.getElementsByTagName("portfolio");
      Node portfolioNode = nodeList.item(0);
      Element portfolioElement = (Element) portfolioNode;
      String portfolioName = portfolioElement.getAttribute("title");
      String type = portfolioElement.getAttribute("type");
      if (type.equals("")) {
        throw new Exception();
      }
      NodeList orderList = portfolioElement.getElementsByTagName("order");
      if (type.equals("inflexible")) {
        NodeList stockList = portfolioElement.getElementsByTagName("stock");
        HashMap<String, Integer> stocks = new HashMap<>();
        for (int j = 0; j < stockList.getLength(); j++) {
          Node stockNode = stockList.item(j);
          Element stockElement = (Element) stockNode;
          if (!(this.isValidTicker(stockElement.getAttribute("symbol")))) {
            throw new IllegalArgumentException("Invalid Ticker");
          }
          try {
            quantity = Integer.parseInt(stockElement.getAttribute("quantity"));
            if (quantity <= 0) {
              throw new IllegalArgumentException("Invalid Quantity");
            }
          } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid Quantity");
          }
          stocks.put(stockElement.getAttribute("symbol"), quantity);
        }
        this.addInflexiblePortfolio(portfolioName, stocks);
      } else if (type.equals("flexible")) {
        this.addFlexiblePortfolio(portfolioName);
        for (int k = 0; k < orderList.getLength(); k++) {
          Node orderNode = orderList.item(k);
          Element orderElement = (Element) orderNode;
          String action = orderElement.getAttribute("action");
          String date = orderElement.getAttribute("date");
          float commission = Float.parseFloat(orderElement.getAttribute("commission"));
          if (commission < 0) {
            throw new Exception();
          }
          HashMap<String, Integer> stocks = new HashMap<>();
          NodeList stockList = orderElement.getElementsByTagName("stock");
          for (int l = 0; l < stockList.getLength(); l++) {
            Node stockNode = stockList.item(l);
            Element stockElement = (Element) stockNode;
            if (!(this.isValidTicker(stockElement.getAttribute("symbol")))) {
              throw new IllegalArgumentException("Invalid Ticker");
            }
            try {
              quantity = Integer.parseInt(stockElement.getAttribute("quantity"));
              if (quantity <= 0) {
                throw new IllegalArgumentException("Invalid Quantity");
              }
            } catch (NumberFormatException e) {
              throw new IllegalArgumentException("Invalid Quantity");
            }
            stocks.put(stockElement.getAttribute("symbol"), quantity);
          }
          if (!this.addOrderToPortfolio(portfolioName,
                  this.createOrder(date, action, commission, stocks))) {
            this.user.getPortfolios().remove(portfolioName);
            throw new RuntimeException("Invalid order book");
          }
        }
      }
    } catch (Exception e) {
      System.out.println(e);
      throw new IllegalArgumentException("Invalid XML!");
    }
  }

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
  public Boolean addOrderToPortfolioFromController(String portfolio, String date, String action, float c, HashMap<String, Integer> stocks) {
    return this.addOrderToPortfolio(portfolio, this.createOrder(date, action, c, stocks));
  }

  @Override
  public TreeMap<String, Float> getPerformanceValues(String portfolioName, String date1,
                                                     String date2) {
    TreeMap<String, Float> dateMapper = new TreeMap<>();
    try {
      LocalDate ld1 = LocalDate.parse(date1);
      LocalDate ld2 = LocalDate.parse(date2);
      long diff = (ld2.toEpochDay() - ld1.toEpochDay());
      if (diff < 4) {
        throw new IllegalArgumentException();
      }
      int interval = 4;
      for (int k = 30; k >= 4; k--) {
        if (diff % k == 0) {
          interval = k;
          break;
        }
      }
      diff = diff / interval;
      int i;
      int j = 0;
      LocalDate date = null;
      for (i = 0; i < interval; i++) {
        date = LocalDate.ofEpochDay(ld1.toEpochDay() + (diff * i));
        if (date.isAfter(ld2)) {
          break;
        }
        j = 0;
        boolean flag = true;
        while (true) {
          try {
            this.getPortfolioValues(portfolioName, date.toString());
            break;
          } catch (NullPointerException e) {
            j++;
            date = LocalDate.ofEpochDay(ld1.toEpochDay() + j + (diff * i));
            if (date.isAfter(ld2)) {
              flag = false;
              break;
            }
          }
        }
        if (flag) {
          HashMap<String, Float> hm = this.getPortfolioValues(portfolioName, date.toString());
          ;
          float total = 0;
          for (Map.Entry<String, Float> mapEntry : hm.entrySet()) {
            total += mapEntry.getValue();
          }
          dateMapper.put(date.toString(), total);
        } else {
          break;
        }
      }
      date = LocalDate.ofEpochDay(ld1.toEpochDay() + j + (diff * i));
      if (!date.isAfter(ld2)) {
        HashMap<String, Float> hm = this.getPortfolioValues(portfolioName, date.toString());
        float total = 0;
        for (Map.Entry<String, Float> mapEntry : hm.entrySet()) {
          total += mapEntry.getValue();
        }
        dateMapper.put(date.toString(), total);
      }
    } catch (DateTimeException e) {
      System.out.println(e.getMessage());
    }
    return dateMapper;
  }

  @Override
  public Float getScale(TreeMap<String, Float> values) {
    ArrayList<Float> vars = new ArrayList<>(values.values());
    float scale = Collections.max(vars) - Collections.min(vars);
    scale = scale / 50;
    if (scale == 0) {
      scale = 1;
    }
    return scale;
  }
}