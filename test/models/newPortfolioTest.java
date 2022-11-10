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
    ArrayList<Order> init = new ArrayList<>();
    HashMap<String, Integer> stocks = new HashMap<>();
    stocks.put("GOOG", 1);
    init.add(new Order(Action.BUY, LocalDate.of(2022, 11, 8), stocks, 1));
    portfolio = new newPortfolio("test1", init);
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
    this.portfolio.buy(LocalDate.of(2022, 11, 9), stocks, 1);
    HashMap<String, Integer> comp = new HashMap<>();
    comp.put("GOOG", 1);
    comp.put("AAPL", 1);
    assertEquals(comp, this.portfolio.getStockQuantities());
  }

  @Test
  public void testSellInvalidQuantity() {
    HashMap<String, Integer> stocks = new HashMap<>();
    stocks.put("AAPL", 1);
    this.portfolio.sell(LocalDate.of(2022, 11, 9), stocks, 1);
    HashMap<String, Integer> comp = new HashMap<>();
    comp.put("GOOG", 1);
    assertEquals(comp, this.portfolio.getStockQuantities());
  }

  @Test
  public void testSellValidQuantity() {
    HashMap<String, Integer> stocks = new HashMap<>();
    stocks.put("AAPL", 1);
    this.portfolio.buy(LocalDate.of(2022, 11, 9), stocks, 1);
    this.portfolio.sell(LocalDate.of(2022, 11, 9), stocks, 1);
    HashMap<String, Integer> comp = new HashMap<>();
    comp.put("GOOG", 1);
    assertEquals(comp, this.portfolio.getStockQuantities());
  }

  @Test
  public void testCompositionOnDate() {
    ArrayList<Order> init = new ArrayList<>();
    HashMap<String, Integer> stocks1 = new HashMap<>();
    stocks1.put("GOOG", 1);
    init.add(new Order(Action.BUY, LocalDate.of(2022, 6, 1), stocks1, 1));
    HashMap<String, Integer> stock2 = new HashMap<>();
    stock2.put("AAPL", 1);
    init.add(new Order(Action.BUY, LocalDate.of(2022, 6, 15), stock2, 1));
    HashMap<String, Integer> stocks3 = new HashMap<>();
    stocks3.put("AAPL", 1);
    init.add(new Order(Action.BUY, LocalDate.of(2022, 7, 1), stocks3, 1));
    newPortfolio p1 = new newPortfolio("test1", init);

    HashMap<String, Integer> comp = new HashMap<>();
    comp.put("GOOG", 1);
    comp.put("AAPL", 1);
    assertEquals(comp, p1.getStockCompositionOnDate(LocalDate.of(2022,6,17)));
    comp.put("AAPL", 2);
    assertEquals(comp, p1.getStockCompositionOnDate(LocalDate.of(2022,7 ,17)));
  }
}