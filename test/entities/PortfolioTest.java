package entities;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import models.Portfolio;
import models.Stock;

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
  public void testGetStocks() {
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

  // todo : testGetValue
  // todo : testGetTotalComp
}