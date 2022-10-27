package models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.stream.IntStream;

public class PriceModelImpl implements PriceModel {
  private HashMap<String, HashMap<String, String[]>> priceMap;

  public PriceModelImpl() {
    priceMap = new HashMap<>();
  }

  @Override
  public float getPriceOnDate(String ticker, String date) {
    if ((!this.priceMap.containsKey(ticker)) || (!(this.priceMap.get(ticker).containsKey(date)))) {
      try {
        this.priceMap.put(ticker, callAPI(ticker));
      } catch (Exception e) {
        //TODO: Handle exception
      }
    }
    return Float.parseFloat(this.priceMap.get(ticker).get(date)[3]);
  }

  @Override
  public HashMap<String, String[]> callAPI(String ticker) {
    String apiKey = "BHKT7UTDPMVQV5QF";
    String stockSymbol = ticker;
    URL url;

    try {
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + stockSymbol + "&apikey=" + apiKey + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }

    InputStream in;

    HashMap<String, String[]> innerMap = new HashMap<>();
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
        innerMap.put(arrOfStr[0], subarray);
      }

    } catch (IOException e) {
      throw new IllegalArgumentException("No price data found for " + stockSymbol);
    }
    return innerMap;
  }
}
