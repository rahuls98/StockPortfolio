package views;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


/**
 * Test suite for the PortfolioViewImpl.
 */
public class PortfolioViewImplTest {

  private OutputStream out;
  private PortfolioView textBasedUI;

  @Before
  public void setup() {
    this.out = new ByteArrayOutputStream();
    this.textBasedUI = new PortfolioViewImpl(new PrintStream(out));
  }

  private String prepareString(String s) {
    return s.replace("\r", "").
            replace("\n", "");
  }

  @Test
  public void testPortfolioViewInstantiation() {
    assertTrue(textBasedUI instanceof PortfolioView);
  }

  @Test
  public void testDisplayActions() {
    String expectedOutput = ""
            .concat("1. Create portfolio\r\n")
            .concat("2. Get portfolio composition\r\n")
            .concat("3. Get portfolio value\r\n")
            .concat("4. Exit\r\n");

    String[] actions = new String[]{"Create portfolio", "Get portfolio composition",
        "Get portfolio value", "Exit"};
    textBasedUI.displayActions(actions);
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  @Test
  public void testDisplayPortfoliosWithInvalidInput() {
    try {
      textBasedUI.displayPortfolios(null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Input cannot be null!", e.getMessage());
    }
  }

  @Test
  public void testDisplayPortfoliosWithEmptyInput() {
    try {
      textBasedUI.displayPortfolios(new String[]{});
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Input cannot be empty!", e.getMessage());
    }
  }

  @Test
  public void testDisplayPortfolios() {
    String[] portfolios = new String[]{"Portfolio 1", "Portfolio 2", "Portfolio 3"};
    String expectedOutput = ""
            .concat("1. Portfolio 1\r\n")
            .concat("2. Portfolio 2\r\n")
            .concat("3. Portfolio 3\r\n");
    textBasedUI.displayPortfolios(portfolios);
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  @Test
  public void testDisplayPortfolioCompositionWithInvalidInput() {
    try {
      textBasedUI.displayPortfolioComposition(null, null);
    } catch (IllegalArgumentException e) {
      assertEquals("Input cannot be null!", e.getMessage());
    }
  }

  @Test
  public void testDisplayPortfolioCompositionWithEmptyInput() {
    try {
      textBasedUI.displayPortfolioComposition("", new HashMap<>());
    } catch (IllegalArgumentException e) {
      assertEquals("Input cannot be empty!", e.getMessage());
    }
  }

  @Test
  public void testDisplayPortfolioComposition() {
    String portfolioName = "Test portfolio";
    HashMap<String, Integer> stockQuantities = new HashMap<>();
    stockQuantities.put("AAPL", 10);
    String expectedOutput = ""
            .concat("Portfolio: ").concat(portfolioName).concat("\n")
            .concat("---------------------------\n")
            .concat("Stock       |  Quantity    \n")
            .concat("---------------------------\n")
            .concat("AAPL        |  10          \n")
            .concat("---------------------------\n");
    textBasedUI.displayPortfolioComposition(portfolioName, stockQuantities);
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  @Test
  public void testDisplayPortfolioValueWithInvalidInput() {
    try {
      textBasedUI.displayPortfolioValue(null, null);
    } catch (IllegalArgumentException e) {
      assertEquals("Input cannot be null!", e.getMessage());
    }
  }

  @Test
  public void testDisplayPortfolioValueWithEmptyInput() {
    try {
      textBasedUI.displayPortfolioValue("", new HashMap<>());
    } catch (IllegalArgumentException e) {
      assertEquals("Input cannot be empty!", e.getMessage());
    }
  }

  @Test
  public void testDisplayPortfolioValue() {
    String portfolioName = "Test portfolio";
    HashMap<String, Float> portfolioValues = new HashMap<>();
    portfolioValues.put("AAPL", 106.5f);
    String expectedOutput = ""
            .concat("Portfolio: ").concat(portfolioName).concat("\n")
            .concat("---------------------------\n")
            .concat("Stock       |  Value       \n")
            .concat("---------------------------\n")
            .concat("AAPL        |  106.5       \n")
            .concat("---------------------------\n");
    textBasedUI.displayPortfolioValue(portfolioName, portfolioValues);
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }
}