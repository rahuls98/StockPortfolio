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
    // todo : handle null, empty, invalid strings
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
   * @param prices desc.
   */
  public void setPrices(HashMap<String, Float[]> prices) {
    this.prices = prices;
  }

  /**
   * Description of method.
   *
   * @param date desc.
   * @return desc.
   */
  public float getPriceOnDate(String date) {
    // todo : validate date
    if (!(prices.containsKey(date))) {
      APIModel model = new APIModelImpl();
      prices = model.callStockApi(this.ticker);
    }
    // todo : create variable to indicate what [3] is, example: closingValue =
    return prices.get(date)[3];
  }
}
