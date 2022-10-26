package models;

import org.junit.Test;

import java.net.MalformedURLException;
import java.util.HashMap;

import static org.junit.Assert.*;

public class PriceModelImplTest {

  @Test
  public void sanityTest() throws MalformedURLException {
    PriceModel pm = new PriceModelImpl();
    HashMap<String, HashMap<String, String[]>> map;
    map = pm.callAPI("GOOG");
    System.out.println(map);
  }

}