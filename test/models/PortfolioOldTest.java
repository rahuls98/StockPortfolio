//package models;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.HashMap;
//import java.util.HashSet;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;
//
//
///**
// * Test suite for the Portfolio.
// */
//public class PortfolioOldTest {
//
//  private Portfolio_old portfolioOld;
//
//  @Before
//  public void setup() {
//    this.portfolioOld = new Portfolio_old("test");
//  }
//
//  @Test
//  public void testPortfolioInstantiationWithInvalidInput() {
//    try {
//      portfolioOld = new Portfolio_old(null);
//      fail();
//    } catch (IllegalArgumentException e) {
//      assertEquals("Portfolio name cannot be empty!", e.getMessage());
//    }
//    try {
//      portfolioOld = new Portfolio_old("");
//      fail();
//    } catch (IllegalArgumentException e) {
//      assertEquals("Portfolio name cannot be empty!", e.getMessage());
//    }
//  }
//
//  @Test
//  public void testGetName() {
//    assertEquals("test", portfolioOld.getName());
//  }
//
//  @Test
//  public void testGetStocksEmpty() {
//    HashMap<Stock, Integer> stocks = portfolioOld.getStocks();
//    assertEquals(0, stocks.size());
//  }
//
//  @Test
//  public void testAddStock() {
//    Stock stock = new Stock("AAPL");
//    portfolioOld.addStock(stock, 4);
//    HashMap<Stock, Integer> stocks = portfolioOld.getStocks();
//    assertEquals(1, stocks.size());
//    assertTrue(stocks.containsKey(stock));
//  }
//
//  @Test
//  public void testAddStockWithInvalidInput() {
//    try {
//      portfolioOld.addStock(null, 4);
//    } catch (IllegalArgumentException e) {
//      assertEquals("Stock cannot be null!", e.getMessage());
//    }
//
//    try {
//      Stock stock = new Stock("AAPL");
//      portfolioOld.addStock(stock, -4);
//    } catch (IllegalArgumentException e) {
//      assertEquals("Quantity cannot be negative!", e.getMessage());
//    }
//  }
//
//  @Test
//  public void testGetTotalCompOnPortfolio() {
//    this.portfolioOld.addStock(new Stock("AAPL"), 2);
//    this.portfolioOld.addStock(new Stock("GOOG"), 2);
//    assertEquals(this.portfolioOld.getTotalComp("2022-10-31"), 496.0, 0.01f);
//  }
//
//  @Test
//  public void testGetValueOnPortfolio() {
//    this.portfolioOld.addStock(new Stock("AAPL"), 2);
//    this.portfolioOld.addStock(new Stock("GOOG"), 2);
//    HashMap<String, Float> value = this.portfolioOld.getValue("2022-10-31");
//    assertEquals(value.get("AAPL"), 306.67, 0.01f);
//    assertEquals(value.get("GOOG"), 189.32, 0.01f);
//    assertEquals((value.get("AAPL") + value.get("GOOG")),
//            this.portfolioOld.getTotalComp("2022-10-31"),
//            0.01f);
//  }
//
//  @Test
//  public void testGetStocks() {
//    this.portfolioOld.addStock(new Stock("AAPL"), 2);
//    this.portfolioOld.addStock(new Stock("GOOG"), 2);
//    HashSet<String> stockNames = new HashSet<>();
//    stockNames.add("AAPL");
//    stockNames.add("GOOG");
//    assertEquals(stockNames, this.portfolioOld.getStockNames());
//  }
//
//  @Test
//  public void testGetStockQuantities() {
//    this.portfolioOld.addStock(new Stock("AAPL"), 2);
//    this.portfolioOld.addStock(new Stock("GOOG"), 2);
//    HashMap<String, Integer> stockQuantities = new HashMap<>();
//    stockQuantities.put("AAPL", 2);
//    stockQuantities.put("GOOG", 2);
//    assertEquals(stockQuantities, this.portfolioOld.getStockQuantities());
//  }
//}
