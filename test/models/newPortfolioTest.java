package models;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class newPortfolioTest {
  private newPortfolio portfolio;

  @Before
  public void initTest() {
    HashMap<String, Integer> stocks = new HashMap<>();
    stocks.put("GOOG", 1);
    Order order = new Order(Action.BUY, LocalDate.of(2022, 11, 8), 1);
    order.addStocks(stocks);
    portfolio = new newPortfolio("test1");
    this.portfolio.placeOrder(order);
  }

  @Test
  public void testisValidDate() {
    assertTrue(this.portfolio.isValidDate(LocalDate.of(2022, 11, 8)));
    assertTrue(this.portfolio.isValidDate(LocalDate.of(2022, 11, 25)));
    assertFalse(this.portfolio.isValidDate(LocalDate.of(2022, 10, 25)));
  }

  @Test
  public void testAdd() {
    HashMap<String, Integer> stocks = new HashMap<>();
    stocks.put("AAPL", 1);
    Order o = new Order(Action.BUY, LocalDate.of(2022, 11, 9), 1);
    o.addStocks(stocks);
    this.portfolio.placeOrder(o);
    HashMap<String, Integer> comp = new HashMap<>();
    comp.put("GOOG", 1);
    comp.put("AAPL", 1);
    assertEquals(comp, this.portfolio.getStockQuantities());
  }

  @Test
  public void testSellInvalidQuantity() {
    HashMap<String, Integer> stocks = new HashMap<>();
    stocks.put("AAPL", 1);
    Order o = new Order(Action.SELL, LocalDate.of(2022, 11, 9), 1);
    o.addStocks(stocks);
    this.portfolio.placeOrder(o);
    HashMap<String, Integer> comp = new HashMap<>();
    comp.put("GOOG", 1);
    assertEquals(comp, this.portfolio.getStockQuantities());
  }

  @Test
  public void testSellValidQuantity() {
    HashMap<String, Integer> stocks = new HashMap<>();
    stocks.put("AAPL", 1);
    Order buyOrder = new Order(Action.BUY, LocalDate.of(2022, 11, 9), 1);
    Order sellOrder = new Order(Action.SELL, LocalDate.of(2022, 11, 9), 1);
    buyOrder.addStocks(stocks);
    sellOrder.addStocks(stocks);
    this.portfolio.placeOrder(buyOrder);
    this.portfolio.placeOrder(sellOrder);
    HashMap<String, Integer> comp = new HashMap<>();
    comp.put("GOOG", 1);
    assertEquals(comp, this.portfolio.getStockQuantities());
  }

  @Test
  public void testCompositionOnDate() {
    Order o1 = new Order(Action.BUY, LocalDate.of(2022, 6, 1), 1);
    HashMap<String, Integer> stocks1 = new HashMap<>();
    stocks1.put("GOOG", 1);
    o1.addStocks(stocks1);
    Order o2 = new Order(Action.BUY, LocalDate.of(2022, 6, 15), 1);
    HashMap<String, Integer> stock2 = new HashMap<>();
    stock2.put("AAPL", 1);
    o2.addStocks(stock2);
    Order o3 = new Order(Action.BUY, LocalDate.of(2022, 7, 1), 1);
    HashMap<String, Integer> stocks3 = new HashMap<>();
    stocks3.put("AAPL", 1);
    o3.addStocks(stocks3);
    newPortfolio p1 = new newPortfolio("test1");
    p1.placeOrder(o1);
    p1.placeOrder(o2);
    p1.placeOrder(o3);

    HashMap<String, Integer> comp = new HashMap<>();
    comp.put("GOOG", 1);
    comp.put("AAPL", 1);
    assertEquals(comp, p1.getStockCompositionOnDate(LocalDate.of(2022, 6, 17)));
    comp.put("AAPL", 2);
    assertEquals(comp, p1.getStockCompositionOnDate(LocalDate.of(2022, 7, 17)));
  }

  @Test
  public void testGetValue() {
    Order o1 = new Order(Action.BUY, LocalDate.of(2022, 6, 1), 1);
    HashMap<String, Integer> stocks1 = new HashMap<>();
    stocks1.put("GOOG", 1);
    o1.addStocks(stocks1);
    Order o2 = new Order(Action.BUY, LocalDate.of(2022, 6, 15), 1);
    HashMap<String, Integer> stock2 = new HashMap<>();
    stock2.put("AAPL", 1);
    o2.addStocks(stock2);
    Order o3 = new Order(Action.BUY, LocalDate.of(2022, 7, 1), 1);
    HashMap<String, Integer> stocks3 = new HashMap<>();
    stocks3.put("AAPL", 1);
    o3.addStocks(stocks3);
    newPortfolio p1 = new newPortfolio("test1");
    p1.placeOrder(o1);
    p1.placeOrder(o2);
    p1.placeOrder(o3);

    HashMap<String, Float> vals = new HashMap<>();
    vals.put("GOOG", 2132.72f);
    vals.put("AAPL", 130.06f);
    assertEquals(vals, p1.getValue("2022-06-16"));
    vals.put("GOOG", 109.91f);
    vals.put("AAPL", 294.14f);
    assertEquals(vals, p1.getValue("2022-07-18"));

  }

  @Test
  public void testDateRangeSplitter() {
    Order o1 = new Order(Action.BUY, LocalDate.of(2022, 10, 1), 1);
    HashMap<String, Integer> stocks1 = new HashMap<>();
    stocks1.put("GOOG", 10);
    o1.addStocks(stocks1);
    Order o2 = new Order(Action.BUY, LocalDate.of(2022, 10, 2), 1);
    HashMap<String, Integer> stock2 = new HashMap<>();
    stock2.put("AAPL", 60);
    o2.addStocks(stock2);
    Order o3 = new Order(Action.BUY, LocalDate.of(2022, 10, 15), 1);
    HashMap<String, Integer> stocks3 = new HashMap<>();
    stocks3.put("AAPL", 23);
    o3.addStocks(stocks3);
    Order o4 = new Order(Action.BUY, LocalDate.of(2022, 10, 18), 1);
    HashMap<String, Integer> stocks4 = new HashMap<>();
    stocks4.put("AAPL", 123);
    o4.addStocks(stocks4);
    newPortfolio p1 = new newPortfolio("test1");
    p1.placeOrder(o1);
    p1.placeOrder(o2);
    p1.placeOrder(o3);
    p1.placeOrder(o4);

    p1.dateRangeSplitter();
  }
}