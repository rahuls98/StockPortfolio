package models;

import java.net.MalformedURLException;
import java.util.HashMap;

/**
 * Represents the functionality of a class that
 * is used to fetch data from Stock API.
 */
public interface PriceModel {
  /**
   * Returns the price on the particular date.
   * @param date String in YYYY-MM-DD format
   * @return float of price on that day.
   */
  float getPriceOnDate(String ticker, String date);

  /**
   * Makes a call to Stock API and gets price.
   * @param ticker Ticker of company.
   * @return String of all Values from today to 2014.
   */
  HashMap<String, String[]> callAPI(String ticker) throws MalformedURLException;
}

