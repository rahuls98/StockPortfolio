package models;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import controllers.PortfolioController;
import controllers.PortfolioControllerImpl;
import views.PortfolioView;
import views.PortfolioViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Test suite for the PortfolioModelImpl.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PortfolioModelImplTest {

  private PortfolioModel model;

  @Before
  public void setUp() {
    File file = new File("localStorage.xml");
    if (file.exists()) {
      file.delete();
    }
    OutputStream out = new ByteArrayOutputStream();
    String userName = "Test";
    try {
      this.model = new PortfolioModelImpl(userName);
    } catch (Exception e) {
      e.printStackTrace();
    }
    PortfolioView view = new PortfolioViewImpl(new PrintStream(out));
    String testPortfolio = "testPortfolio";
    String s = "1\n1\n" + testPortfolio + "\n2\nAAPL\n2\nGOOG\n2\n4\n";
    InputStream input = new ByteArrayInputStream(s.getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, input,
            new PrintStream(out));
    controller.run();
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
    PortfolioInstanceModel portfolio = model.getPortfolio("testPortfolio");
    assertEquals("testPortfolio", portfolio.getName());
    assertEquals(2, portfolio.getStocks().size());
  }

  @Test
  public void testGetPortfolios() {
    String[] portfolios = model.getPortfolios();
    assertEquals(1, portfolios.length);
  }

  @Test
  public void testGetPortfolioValues() {
    assertEquals(model.getPortfolioValues("testPortfolio", "2022-10-31")
                    .get("AAPL")
            , 306.67, 0.01f);
    assertEquals(model.getPortfolioValues("testPortfolio", "2022-10-31")
                    .get("GOOG")
            , 189.32, 0.01f);
    assertEquals(model.getPortfolioValues("testPortfolio", "2022-10-25")
                    .get("AAPL")
            , 304.67, 0.01f);
    assertEquals(model.getPortfolioValues("testPortfolio", "2022-10-25")
                    .get("GOOG")
            , 209.86, 0.01f);
  }

  @Test
  public void testGetPortfolioTotal() {
    assertEquals(model.getPortfolioTotal("testPortfolio", "2022-10-31"),
            496.0, 0.01f);
    assertEquals(model.getPortfolioTotal("testPortfolio", "2022-10-25"),
            514.53, 0.01f);
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