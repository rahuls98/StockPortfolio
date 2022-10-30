package models;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.IntStream;

public class APIModelImpl implements APIModel {
  private HashMap<String, HashMap<String, String[]>> priceMap;
  private final String key = "0KD9ZFT8VD5QTNLQ";

  public APIModelImpl() {
    this.priceMap = new HashMap<>();
  }


  @Override
  public HashMap<String, Float[]> callStockApi(String ticker) {
    String apiKey = this.key;
    String stockSymbol = ticker;
    URL url;

    try {
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + stockSymbol + "&apikey=" + apiKey + "&datatype=csv");
    } catch (Exception e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
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
        if (line.charAt(0) == 't') { //Skip first line
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
      throw new IllegalArgumentException("No price data found for " + stockSymbol);
    }
    return innerMap;
  }

  @Override
  public HashSet<String> callTickerApi() {
    URL url;
    try {
      url = new URL("https://raw.githubusercontent.com/rreichel3/US-Stock-Symbols/main/all/all_tickers.txt");
    } catch (Exception e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
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
      for(String ticker: strArr) {
        tickerSet.add(ticker);
      }
    } catch (IOException e) {
      throw new RuntimeException("No data found");
    }
    return tickerSet;
  }
}
