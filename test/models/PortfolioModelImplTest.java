package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import controllers.PortfolioController;
import controllers.PortfolioControllerImpl;
import entities.Portfolio;
import entities.User;
import views.PortfolioView;
import views.PortfolioViewImpl;

import static org.junit.Assert.*;

public class PortfolioModelImplTest {
  private OutputStream out;
  private String userName;

  private InputStream input;
  private User defaultUser;
  private PortfolioModel model;
  private PortfolioView view;

  @Before
  public void setUp() {
    File file = new File("localStorage.xml");
    if (file.exists()) {
      file.delete();
    }
    this.out = new ByteArrayOutputStream();
    this.userName = "Test";
    this.defaultUser = new User(userName);
    try {
      this.model = new PortfolioModelImpl(userName);
    } catch (Exception e) {
      e.printStackTrace();
    }
    this.view = new PortfolioViewImpl(new PrintStream(out));
    String testPortfolio = "testPortfolio";
    String s = "1\n1\n" + testPortfolio + "\n2\nAAPL\n1\nGOOG\n1\n4\n";
    InputStream input = new ByteArrayInputStream(s.getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, defaultUser, input, new PrintStream(out));
    controller.go();
  }

  @After
  public void tearDown() {
    File file = new File("localStorage.xml");
    if (file.exists()) {
      file.delete();
    }
  }

  @Test
  public void testGetPortfolio() {
    Portfolio portfolio = model.getPortfolio("testPortfolio");
    assertEquals("testPortfolio", portfolio.getName());
    assertEquals(2, portfolio.getStocks().size());
  }

  @Test
  public void testGetPortfolios() {
    String[] portfolios = model.getPortfolios();
    assertEquals(2, portfolios.length);
  }

  @Test
  public void testGetPortfolioValues() {
    // todo : create hashmap from postman values and compare
  }

  @Test
  public void testGetPortfolioTotal() {
    // todo : create total from postman values and compare
  }

  @Test
  public void testIsValidTickerTrueCase() {
    assertTrue(model.isValidTicker("AAPL"));
  }

  @Test
  public void testIsValidTickerFalseCase() {
    assertFalse(model.isValidTicker("test"));
  }

  @Test
  public void testIsValidDate() {
    assertTrue(this.model.isValidDate("2022-10-31"));
    assertFalse(this.model.isValidDate("2022-10/31"));
    assertFalse(this.model.isValidDate("2019-02-30"));
    assertFalse(this.model.isValidDate("2022/10/31"));
    assertFalse(this.model.isValidDate("2022g10/31"));
    assertFalse(this.model.isValidDate("fhda"));
    assertFalse(this.model.isValidDate("2022-10-324"));
    assertFalse(this.model.isValidDate("202235-10-31"));
  }

}