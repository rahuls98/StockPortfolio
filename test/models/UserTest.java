package models;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


/**
 * Test suite for the User.
 */
public class UserTest {

  private User user;

  @Before
  public void setup() {
    this.user = new User("tester");
  }

  @Test
  public void testUserInstantiationWithInvalidInput() {
    try {
      user = new User("");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Username cannot be empty!", e.getMessage());
    }

    try {
      user = new User(null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Username cannot be empty!", e.getMessage());
    }
  }

  @Test
  public void testGetName() {
    assertEquals("tester", user.getName());
  }

  @Test
  public void testGetPortfolios() {
    HashMap<String, PortfolioInstanceModel> portfolios = user.getPortfolios();
    assertEquals(0, portfolios.size());
  }
//
//  @Test
//  public void testAddPortfolio() {
//    Portfolio_old portfolioOld = new Portfolio_old("portfolio");
//    user.addPortfolio(portfolioOld);
//    HashMap<String, PortfolioInstanceModel> portfolios = user.getPortfolios();
//    assertEquals(1, portfolios.entrySet().size());
//    assertTrue(portfolios.containsKey("portfolio"));
//  }

  @Test
  public void testAddPortfolioWithInvalidInput() {
    try {
      user.addPortfolio(null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Null portfolio not allowed!", e.getMessage());
    }
  }
}