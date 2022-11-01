package models;

import org.junit.Test;

import java.util.HashSet;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class APIModelImplTest {

  @Test
  public void testTickers() {
    APIModel model = new APIModelImpl();
    HashSet<String> set = model.callTickerApi();
    assertFalse(set.contains("RXF"));
  }
}