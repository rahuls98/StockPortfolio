package models;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class portfolioTest {
  private Portfolio portfolio;

  @Before
  public void initTest() {
    HashMap<String, Integer> stocks = new HashMap<>();
    stocks.put("GOOG", 1);
    Order order = new Order(Action.BUY, LocalDate.of(2022, 11, 8), 1);
    order.addStocks(stocks);
    portfolio = new Portfolio("test1", PortfolioType.FLEXIBLE, new ArrayList<>());
    this.portfolio.placeOrder(order);
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
  public void testInvalidSellOlderDate() {
    HashMap<String, Integer> stocks = new HashMap<>();
    stocks.put("AAPL", 1);
    Order o = new Order(Action.SELL, LocalDate.of(2022, 11, 4), 1);
    o.addStocks(stocks);
    assertFalse(this.portfolio.placeOrder(o));
    HashMap<String, Integer> comp = new HashMap<>();
    comp.put("GOOG", 1);
    assertEquals(comp, this.portfolio.getStockQuantities());
  }

  @Test
  public void testInvalidSellMiddleDate() {
    HashMap<String, Integer> stocks = new HashMap<>();
    stocks.put("GOOG", 1);
    Order o = new Order(Action.SELL, LocalDate.of(2022, 11, 14), 1);
    o.addStocks(stocks);
    assertTrue(this.portfolio.placeOrder(o));
    Order o1 = new Order(Action.SELL, LocalDate.of(2022, 11, 11), 1);
    o1.addStocks(stocks);
    assertFalse(this.portfolio.placeOrder(o1));
    HashMap<String, Integer> comp = new HashMap<>();
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
    Portfolio p1 = new Portfolio("test1", PortfolioType.FLEXIBLE, new ArrayList<>());
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
    Portfolio p1 = new Portfolio("test1", PortfolioType.FLEXIBLE, new ArrayList<>());
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
  public void testGetCostBasis() {
    HashMap<String, Integer> stocks1 = new HashMap<>();
    stocks1.put("AAPL", 1);
    HashMap<String, Integer> stocks2 = new HashMap<>();
    stocks2.put("GOOG", 1);
    HashMap<String, Integer> stocks3 = new HashMap<>();
    stocks3.put("GOOG", 1);
    Order o1 = new Order(Action.BUY, LocalDate.of(2022, 10, 07), 10.00f);
    o1.addStocks(stocks1);
    Order o2 = new Order(Action.BUY, LocalDate.of(2022, 10, 13), 20.00f);
    o2.addStocks(stocks2);
    Order o3 = new Order(Action.SELL, LocalDate.of(2022, 10, 17), 10.00f);
    o3.addStocks(stocks3);
    Portfolio port = new Portfolio("test1", PortfolioType.FLEXIBLE, new ArrayList<>());
    port.placeOrder(o1); //Buy 1 AAPL on 7th October - 140.09
    port.placeOrder(o2); //Buy 1 GOOG on 13th October - 99.71
    port.placeOrder(o3);//Sell 1 GOOG on 17th October

    assertEquals(0.00f, port.getCostBasis(LocalDate.parse("2022-09-01")), 0.01);
    assertEquals(150.09f, port.getCostBasis(LocalDate.parse("2022-10-07")), 0.01);
    assertEquals((150.09f + 99.71 + 20), port.getCostBasis(LocalDate.parse("2022-10-13")), 0.01);
    assertEquals((150.09f + 99.71 + 20 + 10), port.getCostBasis(LocalDate.parse("2022-10-18")), 0.01);
  }
}