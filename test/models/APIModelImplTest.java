package models;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class APIModelImplTest {

  private APIModel model;

  @Before
  public void setup() {
    model = new APIModelImpl();
  }

  @Test
  public void testGetStockPrices() {
    HashMap<String, Float[]> prices = model.getStockPrices("AAPL");
    assertFalse(prices.isEmpty());
    // todo : get prices from postman and compare for a few dates
  }

  @Test
  public void testGetValidTickers() {
    HashSet<String> set = model.getValidTickers();
    assertTrue(set.size() > 0);
    assertFalse(set.contains("RXF"));
  }
}