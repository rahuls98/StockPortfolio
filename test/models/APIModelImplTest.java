package models;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;

import static org.junit.Assert.assertFalse;

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
  }

  @Test
  public void testGetValidTickers() {
    HashSet<String> set = model.getValidTickers();
    assertFalse(set.contains("RXF"));
  }
}