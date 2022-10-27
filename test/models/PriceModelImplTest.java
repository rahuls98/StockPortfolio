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
    System.out.println(model.getPriceOnDate("GOOG", "2022-10-25"));
  }

  @Test
  public void testTimeout() throws InterruptedException {
    PriceModel model = new PriceModelImpl();
    String[] tickers = new String[]{"AAPL", "MSFT", "GOOGL", "GOOG", "AMZN", "TSLA",
            "BRK/A", "BRK/B", "UNH", "JNJ", "XOM", "V", "WMT"};
    for (String ticker : tickers) {
      System.out.println(model.getPriceOnDate(ticker, "2022-10-25"));
//      sleep(10000);
    }
  }
}