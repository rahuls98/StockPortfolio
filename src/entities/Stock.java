package entities;

import java.util.HashMap;

public class Stock {
  private final String ticker;
  private HashMap<String, String[]> prices;

  public Stock(String ticker) {
    this.ticker = ticker;
    this.prices = new HashMap<>();
  }

  public String getTicker() {
    return ticker;
  }

  public HashMap<String, String[]> getPrices() {
    return prices;
  }
}
