package models;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import entities.Portfolio;
import entities.Stock;
import entities.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class StorageModelLocalImplTest {

  private StorageModel localStorage;

  @Before
  public void setup() {
    try {
      localStorage = new StorageModelLocalImpl();
    } catch (IOException e) {
      System.out.println(e.getMessage());
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
      assertEquals(0, localStorageFile.length());
    } else {
      fail();
    }
  }

  @Test
  public void testReadWithEmptyLocalStorage() {
    File localStorageFile = new File("./localStorage.xml");
    if (localStorageFile.delete()) {
      setup();
      assertNull(localStorage.read("Default"));
    } else {
      fail();
    }
  }

  @Test
  public void testRead() {
    User user = localStorage.read("Test");
    HashMap<String, Portfolio> portfolios = user.getPortfolios();
    for (Map.Entry<String, Portfolio> portfolio : portfolios.entrySet()) {
      for (Map.Entry<Stock, Integer> stock : portfolio.getValue().getStocks().entrySet()) {
        System.out.println(stock.getKey().getTicker());
        System.out.println(stock.getValue());
      }
    }
  }

  @Test
  public void testWrite() {
    User user = new User("Test");
    Portfolio testPortfolio = new Portfolio("Test portfolio");
    Stock stockX = new Stock("X");
    Stock stockY = new Stock("Y");
    Stock stockZ = new Stock("Z");
    testPortfolio.addStock(stockX, 56);
    testPortfolio.addStock(stockY, 36);
    testPortfolio.addStock(stockZ, 46);
    user.addPortfolio(testPortfolio);

    Portfolio testPortfolio2 = new Portfolio("Test portfolio 2");
    Stock stockA = new Stock("A");
    Stock stockB = new Stock("B");
    Stock stockC = new Stock("C");
    testPortfolio2.addStock(stockA, 56-10);
    testPortfolio2.addStock(stockB, 36-10);
    testPortfolio2.addStock(stockC, 46-10);
    user.addPortfolio(testPortfolio2);
    localStorage.write(user);
  }
}