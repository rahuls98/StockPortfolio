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

  /**
   * Description of constructor.
   *
   * @param userName desc.
   */
  public PortfolioModelImpl(String userName) {
    try {
      this.store = new StorageModelLocalImpl();
    } catch (Exception e) {
      // TODO : handle exceptions
      e.printStackTrace();
    }
    User userFromStore = this.store.readUser(userName);
    this.user = (userFromStore != null) ? userFromStore : new User(userName);
  }

  @Override
  public void addPortfolio(User user) {
    // TODO: user not null
    if (this.user.getName().equals(user.getName())) {
      for (Map.Entry<String, Portfolio> entry : user.getPortfolios().entrySet()) {
        this.user.addPortfolio(entry.getValue());
      }
    } else {
      this.user = user;
    }
    this.store.writeUser(this.user);
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