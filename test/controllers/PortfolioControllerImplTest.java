package controllers;

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
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.TreeMap;

import models.Order;
import models.Portfolio;
import models.PortfolioInstanceModel;
import models.PortfolioModel;
import models.PortfolioModelImpl;
import views.PortfolioView;
import views.PortfolioViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test suite for the PortfolioControllerImpl.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PortfolioControllerImplTest {
  private OutputStream out;
  private InputStream input;
  private PortfolioModel model;
  private PortfolioView view;

  private final String startingPrompt = "Please enter the menu item number when requested.";
  private final String startingMenu = "What would you like to do?\n" +
          "1. Create Portfolio\n" +
          "2. Create Order\n" +
          "3. Get portfolio composition\n" +
          "4. Get portfolio value\n" +
          "5. Get Cost Basis\n" +
          "6. Get Performance\n" +
          "7. Exit\n" +
          "Select action: ";

  private final String exitOption = "7";

  private final String invalidInteger = "Please enter a valid integer value: ";

  private final String portfolioDetailEntryOptions = "How would you like to " +
          "enter the portfolio details?\n" +
          "1. Enter manually\n" +
          "2. Load from file\n" +
          "3. Go back\n" +
          "Select action: ";

  private final String portfolioTypeOptions = "What type of portfolio would you like to create?\n" +
          "1. Flexible\n" +
          "2. Inflexible\n" +
          "3. Go back\n" +
          "Select action: ";

  private final String stockTickerEntry = "Stock 1 ticker: ";
  private final String stockQuantityEntry = "Quantity : ";

  @Before
  public void setUp() {
    File file = new File("localStorage.xml");
    if (file.exists()) {
      file.delete();
    }
    this.out = new ByteArrayOutputStream();
    String userName = genRandomString();

    try {
      this.model = new PortfolioModelImpl(userName);
    } catch (Exception e) {
      e.printStackTrace();
    }
    this.view = new PortfolioViewImpl(new PrintStream(out));
  }

  @After
  public void teardown() {
    File file = new File("localStorage.xml");
    if (file.exists()) {
      file.delete();
    }
  }

  private String genRandomString() {
    int leftLimit = 97; // letter 'a'
    int rightLimit = 122; // letter 'z'
    int targetStringLength = 10;
    Random random = new Random();
    StringBuilder buffer = new StringBuilder(targetStringLength);
    for (int i = 0; i < targetStringLength; i++) {
      int randomLimitedInt = leftLimit + (int)
              (random.nextFloat() * (rightLimit - leftLimit + 1));
      buffer.append((char) randomLimitedInt);
    }
    return buffer.toString();
  }

  private String prepareString(String s) {
    return s.replace("\r", "")
            .replace("\n", "");
  }

  @Test
  public void testInvalidInput() {
    try {
      PortfolioController controller = new PortfolioControllerImpl(null, view, input,
              new PrintStream(out));
      fail("Should Throw Exception");
    } catch (IllegalArgumentException e) {
      //do nothing
    }
    try {
      PortfolioController controller = new PortfolioControllerImpl(model, null, input,
              new PrintStream(out));
      fail("Should Throw Exception");
    } catch (IllegalArgumentException e) {
      //do nothing
    }
    try {
      PortfolioController controller = new PortfolioControllerImpl(model, view, null,
              new PrintStream(out));
      fail("Should Throw Exception");
    } catch (IllegalArgumentException e) {
      //do nothing
    }
    try {
      PortfolioController controller = new PortfolioControllerImpl(model, view, input, null);
      fail("Should Throw Exception");
    } catch (IllegalArgumentException e) {
      //do nothing
    }
  }

  @Test
  public void testStartingMenu() {
    InputStream input = new ByteArrayInputStream(this.exitOption.getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, input,
            new PrintStream(out));
    controller.run();
    String expectedOutput = this.startingPrompt + this.startingMenu;
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  @Test
  public void testInputWithNonIntAction() {
    InputStream input = new ByteArrayInputStream(("a\n" + this.exitOption + "\n").getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, input,
            new PrintStream(out));
    controller.run();
    String expectedOutput = this.startingPrompt + this.startingMenu + this.invalidInteger;
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));

  }

  @Test
  public void testInvalidChoice() {
    InputStream input = new ByteArrayInputStream(("10\n" + this.exitOption + "\n").getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, input,
            new PrintStream(out));
    controller.run();
    String expectedOutput = this.startingPrompt + this.startingMenu
            + "Invalid choice, please try again!" + this.startingMenu;
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  @Test
  public void testNonIntChoice() {
    InputStream input = new ByteArrayInputStream(("a\n" + this.exitOption + "\n").getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, input,
            new PrintStream(out));
    controller.run();
    String expectedOutput = this.startingPrompt + this.startingMenu + this.invalidInteger;
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  @Test
  public void testGoWithExit() {
    InputStream input = new ByteArrayInputStream(this.exitOption.getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, input,
            new PrintStream(out));
    controller.run();
    String expectedOutput = this.startingPrompt + this.startingMenu;
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  @Test
  public void testCreateInflexiblePortfolioRegularFlow() {
    String startingMenuEntry = "1";
    String portfolioDetailEntry = "1";
    String portfolioType = "2";
    String portfolioName = this.genRandomString();
    String numberOfStocks = "1";
    String stockTicker = "GOOG";
    String stockQuantity = "12";
    String s = startingMenuEntry.concat("\n")
            .concat(portfolioDetailEntry).concat("\n")
            .concat(portfolioType).concat("\n")
            .concat(portfolioName).concat("\n")
            .concat(numberOfStocks).concat("\n")
            .concat(stockTicker).concat("\n")
            .concat(stockQuantity).concat("\n")
            .concat(exitOption).concat("\n");
    InputStream input = new ByteArrayInputStream(s.getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, input,
            new PrintStream(out));
    controller.run();
    String expectedOutput = this.startingPrompt + this.startingMenu
            + this.portfolioDetailEntryOptions
            + this.portfolioTypeOptions
            + "Enter portfolio name: "
            + "Enter number of stocks: "
            + this.stockTickerEntry
            + this.stockQuantityEntry
            + "New portfolio (" + portfolioName + ") has been recorded!"
            + this.startingMenu;
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  @Test
  public void testCreateInflexiblePortfolioInvalidFlow() {
    String startingMenuEntry = "1";
    String portfolioDetailEntry = "1";
    String portfolioType = "2";
    String portfolioName = this.genRandomString();
    String invalidNumberOfStocks = "-1";
    String validNumberOfStocks = "1";
    String stockTicker = "GOOG";
    String stockQuantity = "12";
    String s = startingMenuEntry.concat("\n")
            .concat(portfolioDetailEntry).concat("\n")
            .concat(portfolioType).concat("\n")
            .concat(portfolioName).concat("\n")
            .concat(invalidNumberOfStocks).concat("\n")
            .concat(validNumberOfStocks).concat("\n")
            .concat(stockTicker).concat("\n")
            .concat(stockQuantity).concat("\n")
            .concat(exitOption).concat("\n");
    InputStream input = new ByteArrayInputStream(s.getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, input,
            new PrintStream(out));
    controller.run();
    String expectedOutput = this.startingPrompt + this.startingMenu
            + this.portfolioDetailEntryOptions
            + this.portfolioTypeOptions
            + "Enter portfolio name: "
            + "Enter number of stocks: "
            + "Please enter a valid number of stocks: "
            + this.stockTickerEntry
            + this.stockQuantityEntry
            + "New portfolio (" + portfolioName + ") has been recorded!"
            + this.startingMenu;
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  @Test
  public void testCreateInflexiblePortfolioWithFractional() {
    String startingMenuEntry = "1";
    String portfolioDetailEntry = "1";
    String portfolioType = "2";
    String portfolioName = this.genRandomString();
    String numberOfStocks = "1";
    String stockTicker = "GOOG";
    String invalidStockQuantity = "1.5";
    String validStockQuantity = "1";
    String s = startingMenuEntry.concat("\n")
            .concat(portfolioDetailEntry).concat("\n")
            .concat(portfolioType).concat("\n")
            .concat(portfolioName).concat("\n")
            .concat(numberOfStocks).concat("\n")
            .concat(stockTicker).concat("\n")
            .concat(invalidStockQuantity).concat("\n")
            .concat(validStockQuantity).concat("\n")
            .concat(exitOption).concat("\n");
    InputStream input = new ByteArrayInputStream(s.getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, input,
            new PrintStream(out));
    controller.run();
    String expectedOutput = this.startingPrompt + this.startingMenu
            + this.portfolioDetailEntryOptions
            + this.portfolioTypeOptions
            + "Enter portfolio name: "
            + "Enter number of stocks: "
            + this.stockTickerEntry
            + this.stockQuantityEntry
            + "Please enter a valid integer value: "
            + "New portfolio (" + portfolioName + ") has been recorded!"
            + this.startingMenu;
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  @Test
  public void testCreateInflexiblePortfolioInvalidNonIntFlow() {
    String startingMenuEntry = "1";
    String portfolioDetailEntry = "1";
    String portfolioType = "2";
    String portfolioName = this.genRandomString();
    String numberOfStocks = "1";
    String stockTicker = "GOOG";
    String invalidStockQuantity = "a";
    String validStockQuantity = "1";
    String s = startingMenuEntry.concat("\n")
            .concat(portfolioDetailEntry).concat("\n")
            .concat(portfolioType).concat("\n")
            .concat(portfolioName).concat("\n")
            .concat(numberOfStocks).concat("\n")
            .concat(stockTicker).concat("\n")
            .concat(invalidStockQuantity).concat("\n")
            .concat(validStockQuantity).concat("\n")
            .concat(exitOption).concat("\n");
    InputStream input = new ByteArrayInputStream(s.getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, input,
            new PrintStream(out));
    controller.run();
    String expectedOutput = this.startingPrompt + this.startingMenu
            + this.portfolioDetailEntryOptions
            + this.portfolioTypeOptions
            + "Enter portfolio name: "
            + "Enter number of stocks: "
            + this.stockTickerEntry
            + this.stockQuantityEntry
            + "Please enter a valid integer value: "
            + "New portfolio (" + portfolioName + ") has been recorded!"
            + this.startingMenu;
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  @Test
  public void testGoWithCreatePortfolioWithPath() {
    InputStream input = new ByteArrayInputStream("1\n2\ntestFile.xml\n4\n".getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, input,
            new PrintStream(out));
    controller.run();
    String expectedOutput = "Please enter the menu item number when requested." +
            "What would you like to do?" +
            "1. Create portfolio" +
            "2. Get portfolio composition" +
            "3. Get portfolio value" +
            "4. ExitSelect action: " +
            "How would you like to enter the portfolio details?" +
            "1. Enter manually" +
            "2. Load from file" +
            "3. Go backSelect action: Enter path to XML: New portfolio (TestFile) has been " +
            "recorded!" +
            "What would you like to do?" +
            "1. Create portfolio" +
            "2. Get portfolio composition" +
            "3. Get portfolio value" +
            "4. ExitSelect action: ";
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  @Test
  public void testGoWithCreatePortfolioWithIllegalPath() {
    InputStream input = new ByteArrayInputStream("1\n2\ntestFile1.xml\ntestFile.xml\n4\n"
            .getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, input,
            new PrintStream(out));
    controller.run();
    String expectedOutput = "Please enter the menu item number when requested." +
            "What would you like to do?" +
            "1. Create portfolio" +
            "2. Get portfolio composition" +
            "3. Get portfolio value" +
            "4. ExitSelect action: " +
            "How would you like to enter the portfolio details?" +
            "1. Enter manually" +
            "2. Load from file" +
            "3. Go backSelect action: Enter path to XML:" +
            " No such file exists! Please enter a valid path:" +
            " New portfolio (TestFile) has been recorded!" +
            "What would you like to do?" +
            "1. Create portfolio" +
            "2. Get portfolio composition" +
            "3. Get portfolio value" +
            "4. ExitSelect action: ";
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  @Test
  public void testPortfolioCompositionAfterCreate() {
    String generatedString = this.genRandomString();
    String s = "1\n1\n" + generatedString + "\n1\nAAPL\n1\n2\n1\n4\n";
    InputStream input = new ByteArrayInputStream(s.getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, input,
            new PrintStream(out));
    controller.run();
    String expectedOutput = "Please enter the menu item number when requested." +
            "What would you like to do?" +
            "1. Create portfolio" +
            "2. Get portfolio composition" +
            "3. Get portfolio value" +
            "4. Exit" +
            "Select action: How would you like to enter the portfolio details?" +
            "1. Enter manually" +
            "2. Load from file" +
            "3. Go backSelect action: Enter portfolio name: Enter number of stocks: " +
            "Stock 1 ticker: Quantity : New portfolio (" + generatedString + ") has been " +
            "recorded!" +
            "What would you like to do?1. Create portfolio2. Get portfolio composition" +
            "3. Get portfolio value4. ExitSelect action: Which portfolio would you like to " +
            "explore?" +
            "1. " + generatedString + "Select portfolio: Portfolio: " + generatedString +
            "------------" +
            "---------------Stock       |  Quantity    ---------------------------AAPL " +
            "       |  1           ---------------------------What would you like to do?" +
            "1. Create portfolio2. Get portfolio composition3. Get portfolio value" +
            "4. ExitSelect action: ";
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  @Test
  public void testPortfolioValueAfterCreate() {
    String generatedString = this.genRandomString();
    String s = "1\n1\n" + generatedString + "\n1\nAAPL\n1\n3\n1\n2022-10-31\n4\n";
    InputStream input = new ByteArrayInputStream(s.getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, input,
            new PrintStream(out));
    controller.run();
    String expectedOutput = "Please enter the menu item number when requested." +
            "What would you like to do?" +
            "1. Create portfolio" +
            "2. Get portfolio composition" +
            "3. Get portfolio value" +
            "4. ExitSelect action: How would you like to enter the portfolio details?" +
            "1. Enter manually" +
            "2. Load from file" +
            "3. Go backSelect action: Enter portfolio name: Enter number of stocks: Stock 1 " +
            "ticker" +
            ": Quantity : New portfolio (" + generatedString + ") has been recorded!" +
            "What would you like to do?1. Create portfolio2. Get portfolio composition" +
            "3. Get portfolio value4. ExitSelect action: Which portfolio would you like to " +
            "explore?" +
            "1. " + generatedString + "Select portfolio: Enter the date for which you want the " +
            "value: " +
            "Portfolio: " + generatedString + "---------------------------Stock       |  " +
            "Value      " +
            " ---------------------------AAPL        |  153.34      -------------------" +
            "--------Total value of portfolio on is 153.3400What would you like to do?" +
            "1. Create portfolio2. Get portfolio composition3. Get portfolio value4." +
            " ExitSelect action: ";
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  @Test
  public void testPortfolioValueForInvalidDateAfterCreate() {
    String generatedString = this.genRandomString();
    String s = "1\n1\n" + generatedString + "\n1\nAAPL\n1\n3\n1\n202222-10-31\n2022-10-31\n4\n";
    InputStream input = new ByteArrayInputStream(s.getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, input,
            new PrintStream(out));
    controller.run();
    String expectedOutput = "Please enter the menu item number when requested." +
            "What would you like to do?" +
            "1. Create portfolio" +
            "2. Get portfolio composition" +
            "3. Get portfolio value" +
            "4. ExitSelect action: How would you like to enter the portfolio details?" +
            "1. Enter manually" +
            "2. Load from file" +
            "3. Go backSelect action: Enter portfolio name: Enter number of stocks: Stock 1 " +
            "ticker" +
            ": Quantity : New portfolio (" + generatedString + ") has been recorded!" +
            "What would you like to do?1. Create portfolio2. Get portfolio composition" +
            "3. Get portfolio value4. ExitSelect action: Which portfolio would you like to " +
            "explore?" +
            "1. " + generatedString + "Select portfolio: Enter the date for which you want the " +
            "value:" +
            " Please enter a valid date: " +
            "Portfolio: " + generatedString + "---------------------------Stock       |  " +
            "Value      " +
            " ---------------------------AAPL        |  153.34      -------------------" +
            "--------Total value of portfolio on is 153.3400What would you like to do?" +
            "1. Create portfolio2. Get portfolio composition3. Get portfolio value4." +
            " ExitSelect action: ";
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  @Test
  public void testPortfolioValueForInvalidTomorrowDateAfterCreate() {
    String generatedString = this.genRandomString();
    LocalDate today = LocalDate.now();
    LocalDate tomorrow = today.plusDays(1);
    String s = "1\n1\n" + generatedString + "\n1\nAAPL\n1\n3\n1\n" + tomorrow.toString() + "\n2022-10-31\n4\n";
    InputStream input = new ByteArrayInputStream(s.getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, input,
            new PrintStream(out));
    controller.run();
    String expectedOutput = "Please enter the menu item number when requested." +
            "What would you like to do?" +
            "1. Create portfolio" +
            "2. Get portfolio composition" +
            "3. Get portfolio value" +
            "4. ExitSelect action: How would you like to enter the portfolio details?" +
            "1. Enter manually" +
            "2. Load from file" +
            "3. Go backSelect action: Enter portfolio name: Enter number of stocks: Stock 1 " +
            "ticker" +
            ": Quantity : New portfolio (" + generatedString + ") has been recorded!" +
            "What would you like to do?1. Create portfolio2. Get portfolio composition" +
            "3. Get portfolio value4. ExitSelect action: Which portfolio would you like to " +
            "explore?" +
            "1. " + generatedString + "Select portfolio: Enter the date for which you want the " +
            "value:" +
            " Please enter a valid date: " +
            "Portfolio: " + generatedString + "---------------------------Stock       |  " +
            "Value      " +
            " ---------------------------AAPL        |  153.34      -------------------" +
            "--------Total value of portfolio on is 153.3400What would you like to do?" +
            "1. Create portfolio2. Get portfolio composition3. Get portfolio value4." +
            " ExitSelect action: ";
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  @Test
  public void testPortfolioValueForWeekendAfterCreate() {
    String generatedString = this.genRandomString();
    String s = "1\n1\n" + generatedString + "\n1\nAAPL\n1\n3\n1\n2022-10-30\n4\n";
    InputStream input = new ByteArrayInputStream(s.getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, input,
            new PrintStream(out));
    controller.run();
    String expectedOutput = "Please enter the menu item number when requested." +
            "What would you like to do?" +
            "1. Create portfolio" +
            "2. Get portfolio composition" +
            "3. Get portfolio value" +
            "4. ExitSelect action: How would you like to enter the portfolio details?" +
            "1. Enter manually" +
            "2. Load from file" +
            "3. Go backSelect action: Enter portfolio name: Enter number of stocks: Stock 1 " +
            "ticker" +
            ": Quantity : New portfolio (" + generatedString + ") has been recorded!" +
            "What would you like to do?1. Create portfolio2. Get portfolio composition" +
            "3. Get portfolio value4. ExitSelect action: Which portfolio would you like to " +
            "explore?" +
            "1. " + generatedString + "Select portfolio: Enter the date for which you want the " +
            "value:" +
            " Entered day is a weekend.Calculating value for the previous Friday (2022-10-28)" +
            "Portfolio: " + generatedString + "---------------------------Stock       |  " +
            "Value      " +
            " ---------------------------AAPL        |  155.74      -------------------" +
            "--------Total value of portfolio on is 155.7400What would you like to do?" +
            "1. Create portfolio2. Get portfolio composition3. Get portfolio value4." +
            " ExitSelect action: ";
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  @Test
  public void testMockComposition() {
    String s = "3\n1\n2022-11-14\n7\n";
    InputStream input = new ByteArrayInputStream(s.getBytes());
    StringBuilder log = new StringBuilder();
    PortfolioController controller = new PortfolioControllerImpl(new MockModel(log, 1234), view, input,
            new PrintStream(out));

    controller.run();
    assertEquals("Portfolio 12022-11-14", log.toString());
  }

  class MockModel implements PortfolioModel {
    private StringBuilder log;
    private int uniqueCode;

    public MockModel(StringBuilder log, int uniqueCode) {
      this.log = log;
      this.uniqueCode = uniqueCode;
    }

    @Override
    public void addPortfolio(String portfolioName) {

    }

    @Override
    public void addPortfolioToUser(Portfolio portfolio, String portfolioName) {

    }

    @Override
    public void addFlexiblePortfolio(String portfolioName) {

    }

    @Override
    public void addInflexiblePortfolio(String portfolioName, HashMap<String, Integer> stocks) {

    }

    @Override
    public PortfolioInstanceModel getPortfolio(String portfolioName) {
      log.append(portfolioName);
      return null;
    }

    @Override
    public String[] getPortfolios() {
      String[] arr = new String[]{"Portfolio 1", "Portfolio 2"};
      return arr;
    }

    @Override
    public String[] getFlexiblePortfolios() {
      return new String[0];
    }

    @Override
    public String[] getInflexiblePortfolios() {
      return new String[0];
    }

    @Override
    public HashMap<String, Float> getPortfolioValues(String portfolioName, String date) {
      return null;
    }

    @Override
    public Float getPortfolioTotal(String portfolioName, String date) {
      return null;
    }

    @Override
    public Boolean isValidTicker(String ticker) {
      return null;
    }

    @Override
    public void loadPortfolioFromXml(String pathToXml) {
    }

    @Override
    public String loadPortfolioNameFromXML(String pathToXml) {
      return null;
    }

    @Override
    public boolean isValidDate(String date) {
      return true;
    }

    @Override
    public void addStock(String portfolioName, String stockTicker, int stockQuantity) {

    }

    @Override
    public void persist() {

    }

    @Override
    public HashSet<String> getStockTickersInPortfolio(String portfolioName) {
      return null;
    }

    @Override
    public HashMap<String, Integer> getStockQuantitiesInPortfolio(String portfolioName, String date) {
      log.append(portfolioName);
      log.append(date.toString());
      return new HashMap<String, Integer>();
    }

    @Override
    public Order createOrder(String date, String action, float c, HashMap<String, Integer> stocks) {
      return null;
    }

    @Override
    public Boolean addOrderToPortfolio(String portfolio, Order o) {
      return null;
    }

    @Override
    public Float getCostBasis(String portfolioName, String date) {
      return null;
    }

    @Override
    public TreeMap<String, Float> getPerformanceValues(String portfolioName, String date1, String date2) {
      return null;
    }
  }
}