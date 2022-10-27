package models;

import java.util.HashMap;
import java.util.Map;

/**
 * Description of class.
 */
public class PortfolioModelImpl implements PortfolioModel {

  private HashMap<String, HashMap<String, Integer>> map;
  private StorageModel store;

  public PortfolioModelImpl() {
    try {
      this.store = new StorageModelLocalImpl();
    } catch (Exception e) {
      e.printStackTrace();
    }
    HashMap<String, HashMap<String, Integer>> mapFromStorage = store.read();
    if (mapFromStorage != null) {
      this.map = store.read();
    } else {
      this.map = new HashMap<>();
    }
  }

  @Override
  public void updatePortfolio(HashMap<String, HashMap<String, Integer>> portfolio) {
    for (String key : portfolio.keySet()) {
      this.map.put(key, portfolio.get(key));
    }
    store.write(this.map);
  }

  @Override
  public HashMap<String, Integer> getPortfolio(String name) {
    if (this.map.containsKey(name)) {
      return this.map.get(name);
    } else {
      return null;
    }
  }

  @Override
  public String[] getPortfolios() {
    return this.map.keySet().toArray(new String[0]);
  }

  @Override
  public HashMap<String, Float> getPortfolioValues(String name, String date) {
    HashMap<String, Float> values = new HashMap<>();
    PriceModel model = new PriceModelImpl();
    String[] stockNames = new String[this.map.get(name).size()];
    int i = 0;
    for (Map.Entry<String, Integer> pair : this.map.get(name).entrySet()) {
      stockNames[i] = pair.getKey();
      i += 1;
    }
    float[] prices = model.getPriceForTickers(stockNames, date);

    for(i = 0; i < prices.length; i++) {
      values.put(stockNames[i], (this.map.get(name).get(stockNames[i]) * prices[i]));
    }

    return values;
  }

  @Override
  public Float getPortfolioTotal(HashMap<String, Float> portfolioValues) {
    float total = 0.00f;
    for(Map.Entry<String, Float> pair : portfolioValues.entrySet()) {
      total += pair.getValue();
    }
    return total;
  }
}
