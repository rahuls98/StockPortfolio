package models;

import org.junit.Test;

import java.net.MalformedURLException;
import java.util.HashMap;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

public class PriceModelImplTest {

  @Test
  public void testPrice() {
    PriceModel model = new PriceModelImpl();
//    System.out.println(model.getPriceOnDate("GOOG", "2022-10-25"));
  }

  @Test
  public void testTimeout() throws InterruptedException {
    PriceModel model = new PriceModelImpl();
    String[] tickers = new String[]{"AAPL", "MSFT", "GOOGL", "GOOG", "AMZN", "TSLA", "UNH", "JNJ", "XOM", "V", "WMT",
            "HD", "MA", "BAC", "META", "KO"};
//    float[] prices = model.getPriceForTickers(tickers, "2022-10-25");
//    for (float price : prices) {
//      System.out.println(price);
//    }
  }
}