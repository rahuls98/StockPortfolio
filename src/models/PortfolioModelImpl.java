package models;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Represents a class that manages stock portfolios for different users using an XML file storage.
 */
public class PortfolioModelImpl implements PortfolioModel {

  private User user;
  private StorageModel store;
  private HashSet<String> tickerSet;

  /**
   * Represents an object of the PortfolioModelImpl class.
   *
   * @param userName The user whose portfolio is to be managed.
   */
  public PortfolioModelImpl(String userName) throws IOException {
    this.store = new StorageModelLocalImpl();
    this.user = this.store.readUser(userName);
    APIModel model = new APIModelImpl();
    tickerSet = model.getValidTickers();
  }

  @Override
  public void addPortfolio(User user) {
    if (this.user.getName().equals(user.getName())) {
      for (Map.Entry<String, Portfolio> entry : user.getPortfolios().entrySet()) {
        this.user.addPortfolio(entry.getValue());
      }
    } else {
      this.user = user;
    }
    this.store.writeUser(this.user);
  }

  @Override
  public Portfolio getPortfolio(String portfolioName) {
    return this.user.getPortfolios().get(portfolioName);
  }

  @Override
  public String[] getPortfolios() {
    return this.user.getPortfolios().keySet().toArray(new String[0]);
  }

  @Override
  public HashMap<String, Float> getPortfolioValues(String portfolioName, String date) {
    return this.user.getPortfolios().get(portfolioName).getValue(date);
  }

  @Override
  public Float getPortfolioTotal(String portfolioName, String date) {
    return this.user.getPortfolios().get(portfolioName).getTotalComp(date);
  }

  @Override
  public Boolean isValidTicker(String ticker) {
    return this.tickerSet.contains(ticker);
  }

  public Portfolio loadPortfolioFromXml(String pathToFile) {
    int quantity;
    FileModelXmlImpl xmlFileHandler = new FileModelXmlImpl();
    xmlFileHandler.readFile(pathToFile);
    Document document = xmlFileHandler.getDocument();
    NodeList nodeList = document.getElementsByTagName("portfolio");
    Node portfolioNode = nodeList.item(0);
    Element portfolioElement = (Element) portfolioNode;
    Portfolio portfolioObj = new Portfolio(portfolioElement.getAttribute("title"));
    NodeList stockList = portfolioElement.getElementsByTagName("stock");
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
      Stock stock = new Stock(stockElement.getAttribute("symbol"));
      portfolioObj.addStock(stock, quantity);
    }
    return portfolioObj;
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
}