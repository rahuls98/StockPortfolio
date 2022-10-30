package views;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;

import static org.junit.Assert.*;

public class PortfolioViewImplTest {

  private OutputStream out;
  private PortfolioView textBasedUI;

  @Before
  public void setup() {
    this.out = new ByteArrayOutputStream();
    this.textBasedUI = new PortfolioViewImpl(new PrintStream(out));
  }

  @Test
  public void testPortfolioViewInstantiation() {
    assertTrue(textBasedUI instanceof PortfolioView);
  }

  @Test
  public void testDisplayActions() {
    String expectedOutput = ""
            .concat("1. Create portfolio")
            .concat("\n2. Get portfolio composition")
            .concat("\n3. Get portfolio value")
            .concat("\n4. Exit")
            .concat("\n");
    textBasedUI.displayActions();
    assertEquals(expectedOutput, out.toString());
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
            .concat("1. Portfolio 1\n")
            .concat("2. Portfolio 2\n")
            .concat("3. Portfolio 3\n");
    textBasedUI.displayPortfolios(portfolios);
    assertEquals(expectedOutput, out.toString());
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
    assertEquals(expectedOutput, out.toString());
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
    assertEquals(expectedOutput, out.toString());
  }
}