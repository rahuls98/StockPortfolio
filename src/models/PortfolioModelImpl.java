package models;

import java.util.HashMap;

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
}
