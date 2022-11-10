package models;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.IntStream;

/**
 * Represents a class that provides APIs to fetch stock related data.
 */
class APIModelImpl implements APIModel {

  private final String alphaVantageKey;

  /**
   * Returns an object of APIModelImpl.
   */
  public APIModelImpl() {
    // this.alphaVantageKey = "0KD9ZFT8VD5QTNLQ";
    this.alphaVantageKey = "J0PBHB2BXB1ILYHK";
  }

  @Override
  public HashMap<String, Float[]> getStockPrices(String ticker) {
    URL url;
    try {
      url = new URL("https://www.alphavantage"
              .concat(".co/query?function=TIME_SERIES_DAILY")
              .concat("&outputsize=full")
              .concat("&symbol")
              .concat("=").concat(ticker).concat("&apikey=").concat(this.alphaVantageKey)
              .concat("&datatype=csv"));
    } catch (Exception e) {
      throw new RuntimeException("The API has either changed or no longer works");
    }
    InputStream in;
    HashMap<String, Float[]> innerMap = new HashMap<>();
    StringBuilder output = new StringBuilder();
    try {
      in = url.openStream();
      int b;
      while ((b = in.read()) != -1) {
        output.append((char) b);
      }
      String[] strArr = output.toString().split("\r\n");
      for (String line : strArr) {
        if (line.charAt(0) == 't') {
          //Skip first line
          continue;
        }
        String[] arrOfStr = line.split(",", 0);
        String[] subarray = IntStream.range(1, 6)
                .mapToObj(i -> arrOfStr[i])
                .toArray(String[]::new);
        Float[] floats = Arrays.stream(subarray)
                .map(Float::valueOf)
                .toArray(Float[]::new);
        innerMap.put(arrOfStr[0], floats);
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("No price data found for " + ticker);
    }
    return innerMap;
  }

  @Override
  public HashSet<String> getValidTickers() {
    URL url;
    try {
      url = new URL("https://raw.githubusercontent.com/rreichel3/US-Stock-Symbols/"
              .concat("main/all/all_tickers.txt"));
    } catch (Exception e) {
      throw new RuntimeException("The API has either changed or no longer works");
    }
    InputStream in;
    HashSet<String> tickerSet = new HashSet<>();
    StringBuilder output = new StringBuilder();
    try {
      in = url.openStream();
      int b;
      while ((b = in.read()) != -1) {
        output.append((char) b);
      }
      String[] strArr = output.toString().split("\n");
      Collections.addAll(tickerSet, strArr);
    } catch (IOException e) {
      throw new RuntimeException("No data found");
    }
    return tickerSet;
  }
}
