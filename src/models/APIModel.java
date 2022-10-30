package models;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Represents the functionality of a class that
 * is used to fetch data from entities.Stock API.
 */
public interface APIModel {

  /**
   * Makes a call to entities.Stock API and gets price.
   * @param ticker Ticker of company.
   * @return String of all Values from today to 2014.
   */
  HashMap<String, Float[]> callStockApi(String ticker);

  HashSet<String> callTickerApi();
}

