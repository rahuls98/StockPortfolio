package entities;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Description of class
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

  // todo : testGetTicker
  @Test
  public void testGetTicker() {
    assertEquals("AAPL", stock.getTicker());
  }

  @Test
  public void testGetPrices() {
    HashMap<String, Float[]> prices = stock.getPrices();
    assertEquals(0, prices.size());
  }

  // todo : testSetPrices
  // todo : testSetPricesWithInvalidInput
  // todo : testGetPriceOnDate
  // todo : testGetPriceOnDateWithInvalidInput
}