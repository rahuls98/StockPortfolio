package models;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
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
    assertEquals(150.6500, prices.get("2022-11-01")[3], 0.01f);
    assertEquals(153.3400, prices.get("2022-10-31")[3], 0.01f);
    assertEquals(151.0000, prices.get("2022-07-19")[3], 0.01f);
  }

  @Test
  public void testGetValidTickers() {
    HashSet<String> set = model.getValidTickers();
    assertTrue(set.size() > 0);
    assertFalse(set.contains("RXF"));
    assertTrue(set.contains("AAPL"));
  }
}