package models;

import org.junit.Test;

import java.net.MalformedURLException;
import java.util.HashMap;

import static org.junit.Assert.*;

public class PriceModelImplTest {

  @Test
  public void testPrice() {
    PriceModel model = new PriceModelImpl();
    System.out.println(model.getPriceOnDate("GOOG", "2022-10-25"));
  }

}