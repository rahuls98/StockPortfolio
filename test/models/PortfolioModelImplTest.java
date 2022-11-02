package models;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class PortfolioModelImplTest {

  private PortfolioModel model;

  @Before
  public void setUp() throws IOException {
    model = new PortfolioModelImpl("test");
  }

  @Test
  public void testGetPortfolio() {
    Portfolio portfolio = model.getPortfolio("P1");
    assertEquals("P1", portfolio.getName());
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
    //TODO: Test more extensively
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