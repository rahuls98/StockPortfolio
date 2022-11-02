package models;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Test suite for the Stock.
 */
public class StockTest {

  private Stock stock;

  @Before
  public void setup() {
    stock = new Stock("AAPL");
  }

  @Test
  public void testStockInstantiationWithInvalidInput() {
    try {
      stock = new Stock(null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Ticker cannot be empty!", e.getMessage());
    }

    try {
      stock = new Stock("");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Ticker cannot be empty!", e.getMessage());
    }
  }

  @Test
  public void testGetTicker() {
    assertEquals("AAPL", stock.getTicker());
  }

  @Test
  public void testGetPricesInitial() {
    HashMap<String, Float[]> prices = stock.getPrices();
    assertEquals(0, prices.size());
  }

  @Test
  public void testGetPricesAfterCallingAPI() {
    assertEquals(153.3400, stock.getPriceOnDate("2022-10-31"), 0.01);
    assertEquals(144.8000, stock.getPriceOnDate("2022-10-27"), 0.01);
    assertEquals(155.7400, stock.getPriceOnDate("2022-10-28"), 0.01);
  }
}