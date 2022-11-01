package entities;

import java.util.HashMap;

import models.APIModel;
import models.APIModelImpl;

/**
 * Description of class.
 */
public class Stock {
  private final String ticker;
  private HashMap<String, Float[]> prices;

  /**
   * Description of constructor.
   */
  public Stock(String ticker) {
    if (ticker == null || ticker.equals("")) {
      throw new IllegalArgumentException("Ticker cannot be empty!");
    }
    this.ticker = ticker;
    this.prices = new HashMap<>();
  }

  /**
   * Description of method.
   *
   * @return desc.
   */
  public String getTicker() {
    return ticker;
  }

  /**
   * Description of method.
   *
   * @return desc.
   */
  public HashMap<String, Float[]> getPrices() {
    return prices;
  }

  /**
   * Description of method.
   *
   * @param date desc.
   * @return desc.
   */
  public float getPriceOnDate(String date) {
    int closingValue = 3;
    if (!(prices.containsKey(date))) {
      APIModel model = new APIModelImpl();
      this.prices = model.callStockApi(this.ticker);
    }
    return this.prices.get(date)[closingValue];
  }
}
