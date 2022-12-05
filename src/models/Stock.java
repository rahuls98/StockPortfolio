package models;

import java.time.LocalDate;
import java.util.HashMap;

/**
 * Represents a Stock that has a unique ticker symbol and varying prices on different dates.
 */
class Stock {
  private final String ticker;
  private HashMap<String, Float[]> prices;

  /**
   * Returns an object of Stock.
   *
   * @param ticker Unique ticker for the stock.
   */
  public Stock(String ticker) throws IllegalArgumentException {
    if (ticker == null || ticker.equals("")) {
      throw new IllegalArgumentException("Ticker cannot be empty!");
    }
    this.ticker = ticker;
    this.prices = new HashMap<>();
  }

  /**
   * Retrieves the name of the stock (i.e., ticker symbol).
   *
   * @return Unique ticker of stock.
   */
  public String getTicker() {
    return ticker;
  }

  /**
   * Retrieves the prices of the stock on different dates.
   *
   * @return Stock prices by date.
   */
  public HashMap<String, Float[]> getPrices() {
    return prices;
  }

  /**
   * Retrieves the price of the stock on a particular date.
   *
   * @param date Date to retrieve the price for.
   * @return Stock price on given date.
   */
  public float getPriceOnDate(String date) {
    int closingValue = 3;
    float price;
    int count = 0;
    if(this.prices.size() == 0) {
      APIModel model = new APIModelImpl();
      this.prices = model.getStockPrices(this.ticker);
    }

    while (true) {
      if (count == 10) {
        throw new RuntimeException("No data for stock!");
      }
      count += 1;
      try {
        price = this.prices.get(date)[closingValue];
        break;
      } catch (NullPointerException e) {
        date = LocalDate.parse(date).minusDays(1).toString();
      }
    }
    return price;
  }
}
