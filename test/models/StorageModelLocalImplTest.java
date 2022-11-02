package models;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;

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
    User user = localStorage.readUser("Test 2");
    HashMap<String, Portfolio> portfolios = user.getPortfolios();
    assertEquals(2, portfolios.size());
  }

  @Test
  public void testWrite() {
    User user = new User("Test 2");
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
    testPortfolio2.addStock(stockA, 56 - 10);
    testPortfolio2.addStock(stockB, 36 - 10);
    testPortfolio2.addStock(stockC, 46 - 10);
    user.addPortfolio(testPortfolio2);
    localStorage.writeUser(user);
    localStorage.readUser("Test 2");
    HashMap<String, Portfolio> portfolios = user.getPortfolios();
    assertEquals(2, portfolios.size());
  }
}