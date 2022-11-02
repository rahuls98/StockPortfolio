package models;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class PortfolioTest {

  private Portfolio portfolio;

  @Before
  public void setup() {
    this.portfolio = new Portfolio("test");
  }

  @Test
  public void testPortfolioInstantiationWithInvalidInput() {
    try {
      portfolio = new Portfolio(null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be empty!", e.getMessage());
    }
    try {
      portfolio = new Portfolio("");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Portfolio name cannot be empty!", e.getMessage());
    }
  }

  @Test
  public void testGetName() {
    assertEquals("test", portfolio.getName());
  }

  @Test
  public void testGetStocksEmpty() {
    HashMap<Stock, Integer> stocks = portfolio.getStocks();
    assertEquals(0, stocks.size());
  }

  @Test
  public void testAddStock() {
    Stock stock = new Stock("AAPL");
    portfolio.addStock(stock, 4);
    HashMap<Stock, Integer> stocks = portfolio.getStocks();
    assertEquals(1, stocks.size());
    assertTrue(stocks.containsKey(stock));
  }

  @Test
  public void testAddStockWithInvalidInput() {
    try {
      portfolio.addStock(null, 4);
    } catch (IllegalArgumentException e) {
      assertEquals("Stock cannot be null!", e.getMessage());
    }

    try {
      Stock stock = new Stock("AAPL");
      portfolio.addStock(stock, -4);
    } catch (IllegalArgumentException e) {
      assertEquals("Quantity cannot be negative!", e.getMessage());
    }
  }

  //testGetTotalComp
  @Test
  public void testGetTotalCompOnPortfolio() {
    this.portfolio.addStock(new Stock("AAPL"), 2);
    this.portfolio.addStock(new Stock("GOOG"), 2);
    assertEquals(this.portfolio.getTotalComp("2022-10-31"), 496.0, 0.01f);
  }

  //testGetValue
  @Test
  public void testGetValueOnPortfolio() {
    this.portfolio.addStock(new Stock("AAPL"), 2);
    this.portfolio.addStock(new Stock("GOOG"), 2);
    HashMap<String, Float> value = this.portfolio.getValue("2022-10-31");
    assertEquals(value.get("AAPL"), 306.67, 0.01f);
    assertEquals(value.get("GOOG"), 189.32, 0.01f);
    assertEquals((value.get("AAPL") + value.get("GOOG")),
            this.portfolio.getTotalComp("2022-10-31"),
            0.01f);
  }

  //testGetStocks
  @Test
  public void testGetStocks() {
    this.portfolio.addStock(new Stock("AAPL"), 2);
    this.portfolio.addStock(new Stock("GOOG"), 2);
    HashSet<String> stockNames = new HashSet<>();
    stockNames.add("AAPL");
    stockNames.add("GOOG");
    assertEquals(stockNames, this.portfolio.getStockNames());
  }

  //testGetStockQuantities
  @Test
  public void testGetStockQuantities() {
    this.portfolio.addStock(new Stock("AAPL"), 2);
    this.portfolio.addStock(new Stock("GOOG"), 2);
    HashMap<String, Integer> stockQuantities = new HashMap<>();
    stockQuantities.put("AAPL", 2);
    stockQuantities.put("GOOG", 2);
    assertEquals(stockQuantities, this.portfolio.getStockQuantities());
  }

}
