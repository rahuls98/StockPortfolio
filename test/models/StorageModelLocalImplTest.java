package models;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test suite for the StorageModelLocalImpl.
 */
public class StorageModelLocalImplTest {

  private StorageModel localStorage;

  @Before
  public void setup() {
    try {
      localStorage = new StorageModelLocalImpl();
    } catch (Exception e) {
      System.out.println("Storage invalid/corrupted!");
    }
  }

  @Test
  public void testLocalStorageInstanceOfStorageModel() {
    assertTrue(localStorage instanceof StorageModel);
  }

  @Test
  public void testLocalStorageCreation() {
    File localStorageFile = new File("./localStorage.xml");
    if (localStorageFile.delete()) {
      setup();
      assertTrue(localStorageFile.exists());
      assertNotEquals(0, localStorageFile.length());
    } else {
      fail();
    }
  }

  @Test
  public void testRead() {
    User user = localStorage.readUser("main");
    HashMap<String, PortfolioInstanceModel> portfolios = user.getPortfolios();
    /*
    for (Map.Entry<String, PortfolioInstanceModel> portfolioEntry: portfolios.entrySet()) {
      System.out.println(portfolioEntry.getKey());
      HashMap<String, Float> values = portfolioEntry.getValue().getValue("2022-10-05");
      for (Map.Entry<String, Float> valueEntry: values.entrySet()) {
        System.out.println(valueEntry.getKey() + " " + valueEntry.getValue());
      }
    }
    */
    // assertEquals(2, portfolios.size());
  }

  @Test
  public void testWrite() {
    User user = new User("Test 2");
    Order o1 = new Order(Action.BUY, LocalDate.of(2022, 10, 1), 1);
    HashMap<String, Integer> stocks1 = new HashMap<>();
    stocks1.put("GOOG", 10);
    o1.addStocks(stocks1);
    Order o2 = new Order(Action.BUY, LocalDate.of(2022, 10, 2), 1);
    HashMap<String, Integer> stock2 = new HashMap<>();
    stock2.put("AAPL", 60);
    o2.addStocks(stock2);
    Order o3 = new Order(Action.BUY, LocalDate.of(2022, 10, 15), 1);
    HashMap<String, Integer> stocks3 = new HashMap<>();
    stocks3.put("AAPL", 23);
    o3.addStocks(stocks3);
    Order o4 = new Order(Action.BUY, LocalDate.of(2022, 10, 18), 1);
    HashMap<String, Integer> stocks4 = new HashMap<>();
    stocks4.put("AAPL", 123);
    o4.addStocks(stocks4);
    newPortfolio p1 = new newPortfolio("test1");
    p1.placeOrder(o1);
    p1.placeOrder(o2);
    p1.placeOrder(o3);
    p1.placeOrder(o4);
    user.addPortfolio(p1);
    localStorage.writeUser(user);
    localStorage.readUser("Test 2");
    // HashMap<String, PortfolioInstanceModel> portfolios = user.getPortfolios();
    // assertEquals(2, portfolios.size());
  }
}