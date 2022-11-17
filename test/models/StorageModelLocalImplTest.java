package models;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
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
  public void testWrite() {
    File f = new File("./localStorage.xml");
    if (f.delete()) {
      try {
        User user = new User("Test");
        // Create inflexible portfolio
        HashMap<String, Integer> inflexiblePortfolioStocks = new HashMap<>();
        inflexiblePortfolioStocks.put("APPL", 10);
        inflexiblePortfolioStocks.put("GOOG", 15);
        inflexiblePortfolioStocks.put("V", 20);
        Order order = new Order(Action.BUY,
                LocalDate.of(2011, 03, 01), 0.0f);
        order.addStocks(inflexiblePortfolioStocks);
        ArrayList<Order> orders = new ArrayList<>();
        orders.add(order);
        Portfolio inflexiblePortfolio = new Portfolio("inflexible_test",
                PortfolioType.INFLEXIBLE, new ArrayList<Order>(orders));
        user.addNewPortfolio(inflexiblePortfolio);
        // Create flexible portfolio
        Portfolio flexiblePortfolio = new Portfolio("flexible_test", PortfolioType.FLEXIBLE,
                new ArrayList<>());
        HashMap<String, Integer> stocks1 = new HashMap<>();
        stocks1.put("AAPL", 1);
        HashMap<String, Integer> stocks2 = new HashMap<>();
        stocks2.put("GOOG", 1);
        HashMap<String, Integer> stocks3 = new HashMap<>();
        stocks3.put("GOOG", 1);
        Order o1 = new Order(Action.BUY,
                LocalDate.of(2022, 10, 07), 10.00f);
        o1.addStocks(stocks1);
        Order o2 = new Order(Action.BUY,
                LocalDate.of(2022, 10, 13), 20.00f);
        o2.addStocks(stocks2);
        Order o3 = new Order(Action.SELL,
                LocalDate.of(2022, 10, 17), 10.00f);
        o3.addStocks(stocks3);
        flexiblePortfolio.placeOrder(o1); //Buy 1 AAPL on 7th October - 140.09
        flexiblePortfolio.placeOrder(o2); //Buy 1 GOOG on 13th October - 99.71
        flexiblePortfolio.placeOrder(o3); //Sell 1 GOOG on 17th October
        user.addPortfolio(flexiblePortfolio);
        localStorage.writeUser(user);
        localStorage.readUser("Test");
      } catch (Exception e) {
        fail();
      }
    } else {
      fail();
    }
  }

  @Test
  public void testRead() {
    int numPortfolios = 2;
    String inflexiblePortfolioName = "inflexible_test";
    int inflexiblePortfolioOrders = 1;
    String flexiblePortfolioName = "flexible_test";
    int flexiblePortfolioOrders = 3;

    User user = localStorage.readUser("Test");
    HashMap<String, PortfolioInstanceModel> portfolios = user.getPortfolios();
    assertEquals(numPortfolios, portfolios.size());
    for (Map.Entry<String, PortfolioInstanceModel> portfolio: portfolios.entrySet()) {
      if (portfolio.getValue().getType() == PortfolioType.INFLEXIBLE) {
        assertEquals(inflexiblePortfolioName, portfolio.getKey());
        assertEquals(inflexiblePortfolioOrders, portfolio.getValue().getOrderBook().size());
        for (Order order : portfolio.getValue().getOrderBook()) {
          if (order.getAction() == Action.SELL) {
            fail();
          }
        }
      } else if (portfolio.getValue().getType() == PortfolioType.FLEXIBLE) {
        assertEquals(flexiblePortfolioName, portfolio.getKey());
        assertEquals(flexiblePortfolioOrders, portfolio.getValue().getOrderBook().size());
      }
    }
  }
}