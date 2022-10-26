package models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.stream.IntStream;

public class PriceModelImpl implements PriceModel{
  @Override
  public float getPriceOnDate(String ticker, String date) {
    return 0;
  }

  @Override
  public HashMap<String, HashMap<String, String[]>> callAPI(String ticker) throws MalformedURLException {
    String apiKey = "BHKT7UTDPMVQV5QF";
    String stockSymbol = ticker;
    URL url;

    try {
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + stockSymbol + "&apikey="+apiKey+"&datatype=csv");
    }
    catch (MalformedURLException e) {
      throw new MalformedURLException("the alphavantage API has either changed or "
              + "no longer works");
    }

    InputStream in;

    HashMap<String, HashMap<String, String[]>> map = new HashMap<>();
    HashMap<String, String[]> innerMap = new HashMap<>();
    StringBuilder output = new StringBuilder();

    try {
      in = url.openStream();
      int b;

      while ((b=in.read())!=-1) {
        output.append((char)b);
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

    }
    catch (IOException e) {
      throw new IllegalArgumentException("No price data found for "+stockSymbol);
    }
    map.put(ticker, innerMap);
    return map;
  }
}
