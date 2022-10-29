package models;

import java.net.MalformedURLException;
import java.util.HashMap;

import entities.Stock;

/**
 * Represents the functionality of a class that
 * is used to fetch data from entities.Stock API.
 */
public interface PriceModel {
  /**
   * Returns the price on the particular date.
   * @param date String in YYYY-MM-DD format
   * @return float of price on that day.
   */
  Stock getPriceOnDate(Stock stock, String date);

  /**
   * Makes a call to entities.Stock API and gets price.
   * @param ticker Ticker of company.
   * @return String of all Values from today to 2014.
   */
  HashMap<String, Float[]> callAPI(String ticker);
}

