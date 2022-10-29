package entities;

import java.util.HashMap;

import models.PriceModel;
import models.PriceModelImpl;

public class Stock {
  private final String ticker;
  private HashMap<String, Float[]> prices;

  public Stock(String ticker) {
    this.ticker = ticker;
    this.prices = new HashMap<>();
  }

  public void setPrices(HashMap<String, Float[]> prices) {
    this.prices = prices;
  }

  public float getPriceOnDate(String date) {
    // todo : validate date
    if (!(prices.containsKey(date))) {
      PriceModel model = new PriceModelImpl();
      prices = model.callAPI(this.ticker);
    }
    // todo : create variable to indicate what [3] is, example: closingValue =
    return prices.get(date)[3];
  }

  public String getTicker() {
    return ticker;
  }

  public HashMap<String, Float[]> getPrices() {
    return prices;
  }
}
