package models;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Represents classes that provides APIs to fetch stock related data.
 */
interface APIModel {

  /**
   * Returns prices of a stock on particular dates.
   *
   * @param ticker Ticker symbol of the stock.
   * @return All prices for the stock on particular dates (back to 2014).
   */
  HashMap<String, Float[]> getStockPrices(String ticker);

  /**
   * Returns a set of all valid ticker symbols.
   *
   * @return All valid ticker symbols.
   */
  HashSet<String> getValidTickers();
}

