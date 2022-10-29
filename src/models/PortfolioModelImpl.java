package models;

import java.util.HashMap;
import java.util.Map;

import entities.Portfolio;
import entities.Stock;
import entities.User;

/**
 * Description of class.
 */
public class PortfolioModelImpl implements PortfolioModel {

  private User user;
  private StorageModel store;


  public PortfolioModelImpl(String name) {
    try {
      this.store = new StorageModelLocalImpl();
    } catch (Exception e) {
      e.printStackTrace();
    }

    if (this.store.read(name) != null) {
      this.user = store.read(name);
    } else {
      this.user = new User("Default");
    }
  }

  @Override
  public void updatePortfolio(User updatedUser) {
    if (this.user.getName() == updatedUser.getName()) {
      for (Map.Entry<String, Portfolio> entry : updatedUser.getPortfolios().entrySet()) {
        this.user.addPortfolio(entry.getValue());
      }
    }
    store.write(this.user);
  }


  @Override
  public Portfolio getPortfolio(String name) {
    return this.user.getPortfolios().get(name);
  }

  @Override
  public String[] getPortfolios() {
    return this.user.getPortfolios().keySet().toArray(new String[0]);
  }

  @Override
  public HashMap<String, Float> getPortfolioValues(String name, String date) {
    return this.user.getPortfolios().get(name).getValue(date);
  }

  @Override
  public Float getPortfolioTotal(String name, String date) {
    return this.user.getPortfolios().get(name).getTotalComp(date);
  }
}