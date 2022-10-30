package models;

import org.junit.Test;

import static java.lang.Thread.sleep;

public class APIModelImplTest {

  @Test
  public void testPrice() {
    APIModel model = new APIModelImpl();
//    System.out.println(model.getPriceOnDate("GOOG", "2022-10-25"));
  }

  @Test
  public void testTimeout() throws InterruptedException {
    APIModel model = new APIModelImpl();
    String[] tickers = new String[]{"AAPL", "MSFT", "GOOGL", "GOOG", "AMZN", "TSLA", "UNH", "JNJ", "XOM", "V", "WMT",
            "HD", "MA", "BAC", "META", "KO"};
//    float[] prices = model.getPriceForTickers(tickers, "2022-10-25");
//    for (float price : prices) {
//      System.out.println(price);
//    }
  }
}