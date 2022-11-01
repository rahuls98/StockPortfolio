package models;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import entities.Portfolio;
import entities.Stock;
import entities.User;

/**
 * Description of class.
 */
public class PortfolioModelImpl implements PortfolioModel {

  private User user;
  private StorageModel store;
  private HashSet<String> tickerSet;

  /**
   * Description of constructor.
   *
   * @param userName desc.
   */
  public PortfolioModelImpl(String userName) throws IOException {
    this.store = new StorageModelLocalImpl();
    this.user = this.store.readUser(userName);
    APIModel model = new APIModelImpl();
    tickerSet = model.callTickerApi();
  }

  @Override
  public void addPortfolio(User user) {
    // TODO: user not null
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
  public Portfolio getPortfolio(String name) {
    return this.user.getPortfolios().get(name);
  }

  @Override
  public String[] getPortfolios() {
    return this.user.getPortfolios().keySet().toArray(new String[0]);
  }

  @Override
  public HashMap<String, Float> getPortfolioValues(String name, String date) {
    return this.user.getPortfolios().get(name).getValue(date);
  }

  @Override
  public Float getPortfolioTotal(String name, String date) {
    return this.user.getPortfolios().get(name).getTotalComp(date);
  }

  @Override
  public Boolean isValidTicker(String ticker) {
    return this.tickerSet.contains(ticker);
  }

  public Portfolio readPortfolioFromXml(String pathToFile) {
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
      Stock stock = new Stock(stockElement.getAttribute("symbol"));
      portfolioObj.addStock(stock, Integer.parseInt(stockElement.getAttribute("quantity")));
    }
    return portfolioObj;
  }

  @Override
  public boolean isValidDate(String date) {
//    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
    try {
      LocalDate.parse(date, formatter);
    } catch (DateTimeParseException e) {
      return false;
    }
    return true;
  }
}