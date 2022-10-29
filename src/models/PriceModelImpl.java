package models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.IntStream;

import entities.Stock;

import static java.lang.Thread.sleep;

public class PriceModelImpl implements PriceModel {
  private HashMap<String, HashMap<String, String[]>> priceMap;
  private final String key = "0KD9ZFT8VD5QTNLQ";

  public PriceModelImpl() {
    this.priceMap = new HashMap<>();
  }

  @Override
  public Stock getPriceOnDate(Stock stock, String date) {
    // TODO : Handle illegal date
    if(!(stock.getPrices().containsKey(date))) {
      try {
        stock.setPrices(this.callAPI(stock.getTicker()));
      }catch (Exception e) {
        //TODO: Handle exception.
      }
    }
    return stock;
  }


  @Override
  public HashMap<String, Float[]> callAPI(String ticker) {
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
}
