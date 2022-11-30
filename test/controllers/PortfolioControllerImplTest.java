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

import models.Portfolio;
import models.PortfolioModel;
import models.PortfolioModelImpl;
import views.PortfolioView;
import views.PortfolioViewImpl;

import static org.junit.Assert.assertEquals;
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
  private final String startingMenu = "What would you like to do?\n"
          + "1. Create Portfolio\n"
          + "2. Create Order\n"
          + "3. Get portfolio composition\n"
          + "4. Get portfolio value\n"
          + "5. Get Cost Basis\n"
          + "6. Get Performance\n"
          + "7. Fixed amount strategy\n"
          + "8. Exit\n"
          + "Select action: ";

  private final String exitOption = "8";

  private final String invalidInteger = "Please enter a valid integer value: ";

  private final String portfolioDetailEntryOptions = "How would you like to "
          + "enter the portfolio details?\n"
          + "1. Enter manually\n"
          + "2. Load from file\n"
          + "3. Go back\n"
          + "Select action: ";

  private final String portfolioTypeOptions = "What type of portfolio would you like to create?\n"
          + "1. Flexible\n"
          + "2. Inflexible\n"
          + "3. Go back\n"
          + "Select action: ";

  private final String orderTypeOptions = "What type of order would you like to create?\n"
          + "1. BUY order\n"
          + "2. SELL order\n"
          + "Select action: ";

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
  public void testCreateInflexiblePortfolioWithPath() {
    String startingMenuEntry = "1";
    String portfolioDetailEntry = "2";
    String xmlFilePath = "testFile.xml";
    String portfolioName = "TestFile";
    String s = startingMenuEntry.concat("\n")
            .concat(portfolioDetailEntry).concat("\n")
            .concat(xmlFilePath).concat("\n")
            .concat(exitOption).concat("\n");
    InputStream input = new ByteArrayInputStream(s.getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, input,
            new PrintStream(out));
    controller.run();
    String expectedOutput = this.startingPrompt + this.startingMenu
            + this.portfolioDetailEntryOptions
            + "Enter path to XML: "
            + "New portfolio (" + portfolioName + ") has been recorded!"
            + this.startingMenu;
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  @Test
  public void testCreateFlexiblePortfolioWithPath() {
    String startingMenuEntry = "1";
    String portfolioDetailEntry = "2";
    String xmlFilePath = "testFileFlex.xml";
    String portfolioName = "TestFileFlex";
    String s = startingMenuEntry.concat("\n")
            .concat(portfolioDetailEntry).concat("\n")
            .concat(xmlFilePath).concat("\n")
            .concat(exitOption).concat("\n");
    InputStream input = new ByteArrayInputStream(s.getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, input,
            new PrintStream(out));
    controller.run();
    String expectedOutput = this.startingPrompt + this.startingMenu
            + this.portfolioDetailEntryOptions
            + "Enter path to XML: "
            + "New portfolio (" + portfolioName + ") has been recorded!"
            + this.startingMenu;
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  @Test
  public void testCreateInflexiblePortfolioWithIllegalPath() {
    String startingMenuEntry = "1";
    String portfolioDetailEntry = "2";
    String invalidXmlFilePath = "testFile1.xml";
    String validXmlFilePath = "testFile.xml";
    String s = startingMenuEntry.concat("\n")
            .concat(portfolioDetailEntry).concat("\n")
            .concat(invalidXmlFilePath).concat("\n")
            .concat(validXmlFilePath).concat("\n")
            .concat(exitOption).concat("\n");
    InputStream input = new ByteArrayInputStream(s.getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, input,
            new PrintStream(out));
    controller.run();
    String expectedOutput = this.startingPrompt + this.startingMenu
            + this.portfolioDetailEntryOptions
            + "Enter path to XML: "
            + "No such file exists! Please enter a valid path: "
            + "New portfolio (TestFile) has been recorded!"
            + this.startingMenu;
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  @Test
  public void testInflexiblePortfolioCompositionAfterCreate() {
    String xmlFilePath = "testFile.xml";
    String compositionDate = "2022-11-15";
    String portfolioName = "TestFile";
    String s = "1".concat("\n")
            .concat("2").concat("\n")
            .concat(xmlFilePath).concat("\n")
            .concat("3").concat("\n")
            .concat("1").concat("\n")
            .concat(exitOption).concat("\n");
    InputStream input = new ByteArrayInputStream(s.getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, input,
            new PrintStream(out));
    controller.run();
    String expectedOutput = this.startingPrompt + this.startingMenu
            + this.portfolioDetailEntryOptions
            + "Enter path to XML: "
            + "New portfolio (TestFile) has been recorded!"
            + this.startingMenu
            + "Which portfolio would you like to use?"
            + "1. " + portfolioName
            + "Select portfolio: "
            + "Portfolio: " + portfolioName
            + "---------------------------\n"
            + "Stock       |  Quantity    \n"
            + "---------------------------\n"
            + "GOOG        |  2.0         \n"
            + "AAPL        |  2.0         \n"
            + "---------------------------"
            + this.startingMenu;
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  @Test
  public void testInflexiblePortfolioValueAfterCreate() {
    String xmlFilePath = "testFile.xml";
    String valueDate = "2022-11-02";
    String portfolioName = "TestFile";
    String s = "1".concat("\n")
            .concat("2").concat("\n")
            .concat(xmlFilePath).concat("\n")
            .concat("4").concat("\n")
            .concat("1").concat("\n")
            .concat(valueDate).concat("\n")
            .concat(exitOption).concat("\n");
    InputStream input = new ByteArrayInputStream(s.getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, input,
            new PrintStream(out));
    controller.run();
    String expectedOutput = this.startingPrompt + this.startingMenu
            + this.portfolioDetailEntryOptions
            + "Enter path to XML: "
            + "New portfolio (TestFile) has been recorded!"
            + this.startingMenu
            + "Which portfolio would you like to use?"
            + "1. " + portfolioName
            + "Select portfolio: "
            + "Enter the date for which you want the value: "
            + "Portfolio: " + portfolioName
            + "---------------------------\n"
            + "Stock       |  Value       \n"
            + "---------------------------\n"
            + "GOOG        |  $174.14     \n"
            + "AAPL        |  $290.06     \n"
            + "---------------------------"
            + "Total value of portfolio on is $464.2000"
            + this.startingMenu;
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  @Test
  public void testInflexiblePortfolioValueForInvalidDateAfterCreate() {
    String xmlFilePath = "testFile.xml";
    String validValueDate = "2022-10-31";
    String invalidValueDate = "202222-10-31";
    String portfolioName = "TestFile";
    String s = "1".concat("\n")
            .concat("2").concat("\n")
            .concat(xmlFilePath).concat("\n")
            .concat("4").concat("\n")
            .concat("1").concat("\n")
            .concat(invalidValueDate).concat("\n")
            .concat(validValueDate).concat("\n")
            .concat(exitOption).concat("\n");
    InputStream input = new ByteArrayInputStream(s.getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, input,
            new PrintStream(out));
    controller.run();
    String expectedOutput = this.startingPrompt + this.startingMenu
            + this.portfolioDetailEntryOptions
            + "Enter path to XML: "
            + "New portfolio (TestFile) has been recorded!"
            + this.startingMenu
            + "Which portfolio would you like to use?"
            + "1. " + portfolioName
            + "Select portfolio: "
            + "Enter the date for which you want the value: "
            + "Please enter a valid date: "
            + "Portfolio: " + portfolioName
            + "---------------------------\n"
            + "Stock       |  Value       \n"
            + "---------------------------\n"
            + "GOOG        |  $189.32     \n"
            + "AAPL        |  $306.68     \n"
            + "---------------------------"
            + "Total value of portfolio on is $496.0000"
            + this.startingMenu;
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  @Test
  public void testPortfolioValueForInvalidTomorrowDateAfterCreate() {
    String xmlFilePath = "testFile.xml";
    String validValueDate = "2022-10-31";
    String invalidValueDate = LocalDate.now().plusDays(1).toString();
    String portfolioName = "TestFile";
    String s = "1".concat("\n")
            .concat("2").concat("\n")
            .concat(xmlFilePath).concat("\n")
            .concat("4").concat("\n")
            .concat("1").concat("\n")
            .concat(invalidValueDate).concat("\n")
            .concat(validValueDate).concat("\n")
            .concat(exitOption).concat("\n");
    InputStream input = new ByteArrayInputStream(s.getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, input,
            new PrintStream(out));
    controller.run();
    String expectedOutput = this.startingPrompt + this.startingMenu
            + this.portfolioDetailEntryOptions
            + "Enter path to XML: "
            + "New portfolio (TestFile) has been recorded!"
            + this.startingMenu
            + "Which portfolio would you like to use?"
            + "1. " + portfolioName
            + "Select portfolio: "
            + "Enter the date for which you want the value: "
            + "Please enter a valid date: "
            + "Portfolio: " + portfolioName
            + "---------------------------\n"
            + "Stock       |  Value       \n"
            + "---------------------------\n"
            + "GOOG        |  $189.32     \n"
            + "AAPL        |  $306.68     \n"
            + "---------------------------"
            + "Total value of portfolio on is $496.0000"
            + this.startingMenu;
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  @Test
  public void testPortfolioValueForWeekendAfterCreate() {
    String xmlFilePath = "testFile.xml";
    String valueDate = "2022-10-30";
    String portfolioName = "TestFile";
    String s = "1".concat("\n")
            .concat("2").concat("\n")
            .concat(xmlFilePath).concat("\n")
            .concat("4").concat("\n")
            .concat("1").concat("\n")
            .concat(valueDate).concat("\n")
            .concat(exitOption).concat("\n");
    InputStream input = new ByteArrayInputStream(s.getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, input,
            new PrintStream(out));
    controller.run();
    String expectedOutput = this.startingPrompt + this.startingMenu
            + this.portfolioDetailEntryOptions
            + "Enter path to XML: "
            + "New portfolio (TestFile) has been recorded!"
            + this.startingMenu
            + "Which portfolio would you like to use?"
            + "1. " + portfolioName
            + "Select portfolio: "
            + "Enter the date for which you want the value: "
            + "Entered day is a weekend.Calculating value for the previous Friday (2022-10-28)"
            + "Portfolio: " + portfolioName
            + "---------------------------\n"
            + "Stock       |  Value       \n"
            + "---------------------------\n"
            + "GOOG        |  $193.16     \n"
            + "AAPL        |  $311.48     \n"
            + "---------------------------"
            + "Total value of portfolio on is $504.6400"
            + this.startingMenu;
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  @Test
  public void testFlexiblePortfolioCreateOrdersWithInvalidInputs() {
    String portfolioName = "flex_test_1";
    String s = "1".concat("\n") // Create portfolio
            .concat("1").concat("\n") // Enter manually
            .concat("1").concat("\n") // Create flexible portfolio
            .concat(portfolioName).concat("\n")
            .concat("3").concat("\n") // Number of orders
            // ORDER 1
            .concat("1").concat("\n") // BUY order
            .concat("20222-10-02").concat("\n") // Invalid date
            .concat("abs").concat("\n") // Invalid date
            .concat("-1").concat("\n") // Valid date
            .concat("2022-09-30").concat("\n") // Valid date
            .concat("5.6").concat("\n") // Commission
            .concat("1").concat("\n") // Transactions
            .concat("AAPL").concat("\n") // Transactions
            .concat("50").concat("\n") // Transactions
            // ORDER 2
            .concat("1").concat("\n") // BUY order
            .concat("2022-10-05").concat("\n") // Valid date
            .concat("-5.6").concat("\n") // Invalid commission
            .concat("5").concat("\n") // Valid commission
            .concat("1").concat("\n") // Transactions
            .concat("AAPL").concat("\n") // Transactions
            .concat("48").concat("\n") // Transactions
            // ORDER 3
            .concat("2").concat("\n") // SELL order
            .concat("2022-10-07").concat("\n") // Valid date
            .concat("abs").concat("\n") // Invalid commission
            .concat("6.0").concat("\n") // Valid commission
            .concat("1").concat("\n") // Transactions
            .concat("AAPL").concat("\n") // Transactions
            .concat("60").concat("\n") // Transactions
            .concat(exitOption).concat("\n");
    InputStream input = new ByteArrayInputStream(s.getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, input,
            new PrintStream(out));
    controller.run();
    String expectedOutput = this.startingPrompt + this.startingMenu
            + this.portfolioDetailEntryOptions
            + this.portfolioTypeOptions
            + "Enter portfolio name: "
            + "A flexible portfolio can have multiple BUY/SELL orders, each pertaining to a set of "
            + "stock transactions.\n"
            + "How many orders would you like to create? "
            + "ORDER 1"
            + this.orderTypeOptions
            + "Enter date for the order in YYYY-MM-DD format: "
            + "Please enter a valid date that isn't a weekend: "
            + "Please enter a valid date that isn't a weekend: "
            + "Please enter a valid date that isn't a weekend: "
            + "Enter commission for this transaction: "
            + "Enter number of BUY transactions: "
            + "Stock 1 ticker: "
            + "Quantity : "
            + "Order Recorded"
            + "ORDER 2"
            + this.orderTypeOptions
            + "Enter date for the order in YYYY-MM-DD format: "
            + "Enter commission for this transaction: "
            + "Please enter a valid positive float value: "
            + "Enter number of BUY transactions: "
            + "Stock 1 ticker: "
            + "Quantity : "
            + "Order Recorded"
            + "ORDER 3"
            + this.orderTypeOptions
            + "Enter date for the order in YYYY-MM-DD format: "
            + "Enter commission for this transaction: "
            + "Please enter a valid positive float value: "
            + "Enter number of SELL transactions: "
            + "Stock 1 ticker: "
            + "Quantity : "
            + "Order Recorded"
            + "New portfolio (" + portfolioName + ") has been recorded!"
            + this.startingMenu;
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  @Test
  public void testFlexiblePortfolioCostBasisAfterCreate() {
    String costBasisDate = "2022-10-11";
    String portfolioName = "flex_test_1";
    String s = "1".concat("\n") // Create portfolio
            .concat("1").concat("\n") // Enter manually
            .concat("1").concat("\n") // Create flexible portfolio
            .concat(portfolioName).concat("\n")
            .concat("3").concat("\n") // Number of orders
            // ORDER 1
            .concat("1").concat("\n") // BUY order
            .concat("2022-09-30").concat("\n") // Date
            .concat("5.6").concat("\n") // Commission
            .concat("1").concat("\n") // Transactions
            .concat("AAPL").concat("\n") // Transactions
            .concat("50").concat("\n") // Transactions
            // ORDER 2
            .concat("1").concat("\n") // BUY order
            .concat("2022-10-05").concat("\n") // Valid date
            .concat("5").concat("\n") // Commission
            .concat("1").concat("\n") // Transactions
            .concat("AAPL").concat("\n") // Transactions
            .concat("48").concat("\n") // Transactions
            // ORDER 3
            .concat("2").concat("\n") // SELL order
            .concat("2022-10-07").concat("\n") // Valid date
            .concat("6.0").concat("\n") // Caommission
            .concat("1").concat("\n") // Transactions
            .concat("AAPL").concat("\n") // Transactions
            .concat("60").concat("\n") // Transactions
            // Cost basis
            .concat("5").concat("\n") // Get cost basis
            .concat("1").concat("\n") // Select portfolio
            .concat(costBasisDate).concat("\n") // Date
            .concat(exitOption).concat("\n");
    InputStream input = new ByteArrayInputStream(s.getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, input,
            new PrintStream(out));
    controller.run();
    String expectedOutput = this.startingPrompt + this.startingMenu
            + this.portfolioDetailEntryOptions
            + this.portfolioTypeOptions
            + "Enter portfolio name: "
            + "A flexible portfolio can have multiple BUY/SELL orders, each pertaining to a set of "
            + "stock transactions.\n"
            + "How many orders would you like to create? "
            + "ORDER 1"
            + this.orderTypeOptions
            + "Enter date for the order in YYYY-MM-DD format: "
            + "Enter commission for this transaction: "
            + "Enter number of BUY transactions: "
            + "Stock 1 ticker: "
            + "Quantity : "
            + "Order Recorded"
            + "ORDER 2"
            + this.orderTypeOptions
            + "Enter date for the order in YYYY-MM-DD format: "
            + "Enter commission for this transaction: "
            + "Enter number of BUY transactions: "
            + "Stock 1 ticker: "
            + "Quantity : "
            + "Order Recorded"
            + "ORDER 3"
            + this.orderTypeOptions
            + "Enter date for the order in YYYY-MM-DD format: "
            + "Enter commission for this transaction: "
            + "Enter number of SELL transactions: "
            + "Stock 1 ticker: "
            + "Quantity : "
            + "Order Recorded"
            + "New portfolio (" + portfolioName + ") has been recorded!"
            + this.startingMenu
            + "Which portfolio would you like to use?"
            + "1. " + portfolioName
            + "Select portfolio: "
            + "Enter the date to calculate the cost basis for: "
            + "Cost basis of " + portfolioName + " as of " + costBasisDate + " is $13953.8"
            + startingMenu;
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  @Test
  public void testFlexiblePortfolioIllegalOrders() {
    String costBasisDate = "2022-10-11";
    String portfolioName = "flex_test_1";
    String s = "1".concat("\n") // Create portfolio
            .concat("1").concat("\n") // Enter manually
            .concat("1").concat("\n") // Create flexible portfolio
            .concat(portfolioName).concat("\n")
            .concat("3").concat("\n") // Number of orders
            // ORDER 1
            .concat("1").concat("\n") // BUY order
            .concat("2022-09-30").concat("\n") // Date
            .concat("5.6").concat("\n") // Commission
            .concat("1").concat("\n") // Transactions
            .concat("AAPL").concat("\n") // Transactions
            .concat("50").concat("\n") // Transactions
            // ORDER 2
            .concat("2").concat("\n") // SELL order
            .concat("2022-10-05").concat("\n") // Valid date
            .concat("5").concat("\n") // Commission
            .concat("1").concat("\n") // Transactions
            .concat("AAPL").concat("\n") // Transactions
            .concat("50").concat("\n") // Transactions
            // ORDER 3
            .concat("2").concat("\n") // SELL order
            .concat("2022-10-07").concat("\n") // Valid date
            .concat("6.0").concat("\n") // Caommission
            .concat("1").concat("\n") // Transactions
            .concat("AAPL").concat("\n") // Transactions
            .concat("60").concat("\n") // Transactions
            .concat(exitOption).concat("\n");
    InputStream input = new ByteArrayInputStream(s.getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, input,
            new PrintStream(out));
    controller.run();
    String expectedOutput = this.startingPrompt + this.startingMenu
            + this.portfolioDetailEntryOptions
            + this.portfolioTypeOptions
            + "Enter portfolio name: "
            + "A flexible portfolio can have multiple BUY/SELL orders, each pertaining to a set of "
            + "stock transactions.\n"
            + "How many orders would you like to create? "
            + "ORDER 1"
            + this.orderTypeOptions
            + "Enter date for the order in YYYY-MM-DD format: "
            + "Enter commission for this transaction: "
            + "Enter number of BUY transactions: "
            + "Stock 1 ticker: "
            + "Quantity : "
            + "Order Recorded"
            + "ORDER 2"
            + this.orderTypeOptions
            + "Enter date for the order in YYYY-MM-DD format: "
            + "Enter commission for this transaction: "
            + "Enter number of SELL transactions: "
            + "Stock 1 ticker: "
            + "Quantity : "
            + "Order Recorded"
            + "ORDER 3"
            + this.orderTypeOptions
            + "Enter date for the order in YYYY-MM-DD format: "
            + "Enter commission for this transaction: "
            + "Enter number of SELL transactions: "
            + "Stock 1 ticker: "
            + "Quantity : "
            + "Invalid Order"
            + "New portfolio (" + portfolioName + ") has been recorded!"
            + this.startingMenu;
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  @Test
  public void testFlexiblePortfolioIllegalOrderInMiddle() {
    String costBasisDate = "2022-10-11";
    String portfolioName = "flex_test_1";
    String s = "1".concat("\n") // Create portfolio
            .concat("1").concat("\n") // Enter manually
            .concat("1").concat("\n") // Create flexible portfolio
            .concat(portfolioName).concat("\n")
            .concat("3").concat("\n") // Number of orders
            // ORDER 1
            .concat("1").concat("\n") // BUY order
            .concat("2022-09-30").concat("\n") // Date
            .concat("5.6").concat("\n") // Commission
            .concat("1").concat("\n") // Transactions
            .concat("AAPL").concat("\n") // Transactions
            .concat("50").concat("\n") // Transactions
            // ORDER 2
            .concat("2").concat("\n") // SELL order
            .concat("2022-10-07").concat("\n") // Valid date
            .concat("5").concat("\n") // Commission
            .concat("1").concat("\n") // Transactions
            .concat("AAPL").concat("\n") // Transactions
            .concat("50").concat("\n") // Transactions
            // ORDER 3
            .concat("2").concat("\n") // SELL order
            .concat("2022-10-05").concat("\n") // Valid date
            .concat("6.0").concat("\n") // Caommission
            .concat("1").concat("\n") // Transactions
            .concat("AAPL").concat("\n") // Transactions
            .concat("60").concat("\n") // Transactions
            .concat(exitOption).concat("\n");
    InputStream input = new ByteArrayInputStream(s.getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, input,
            new PrintStream(out));
    controller.run();
    String expectedOutput = this.startingPrompt + this.startingMenu
            + this.portfolioDetailEntryOptions
            + this.portfolioTypeOptions
            + "Enter portfolio name: "
            + "A flexible portfolio can have multiple BUY/SELL orders, each pertaining to a set of "
            + "stock transactions.\n"
            + "How many orders would you like to create? "
            + "ORDER 1"
            + this.orderTypeOptions
            + "Enter date for the order in YYYY-MM-DD format: "
            + "Enter commission for this transaction: "
            + "Enter number of BUY transactions: "
            + "Stock 1 ticker: "
            + "Quantity : "
            + "Order Recorded"
            + "ORDER 2"
            + this.orderTypeOptions
            + "Enter date for the order in YYYY-MM-DD format: "
            + "Enter commission for this transaction: "
            + "Enter number of SELL transactions: "
            + "Stock 1 ticker: "
            + "Quantity : "
            + "Order Recorded"
            + "ORDER 3"
            + this.orderTypeOptions
            + "Enter date for the order in YYYY-MM-DD format: "
            + "Enter commission for this transaction: "
            + "Enter number of SELL transactions: "
            + "Stock 1 ticker: "
            + "Quantity : "
            + "Invalid Order"
            + "New portfolio (" + portfolioName + ") has been recorded!"
            + this.startingMenu;
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  @Test
  public void testMockComposition() {
    String s = "3\n1\n2022-11-14\n8\n";
    InputStream input = new ByteArrayInputStream(s.getBytes());
    StringBuilder log = new StringBuilder();
    PortfolioController controller = new PortfolioControllerImpl(
            new MockModel(log, 1234), view, input, new PrintStream(out));
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
      return;
    }

    @Override
    public void addPortfolioToUser(Portfolio portfolio, String portfolioName) {
      return;
    }

    @Override
    public void addFlexiblePortfolio(String portfolioName) {
      return;
    }

    @Override
    public void addInflexiblePortfolio(String portfolioName, HashMap<String, Float> stocks) {
      return;
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
      return;
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
    public void persist() {
      return;
    }

    @Override
    public HashSet<String> getStockTickersInPortfolio(String portfolioName) {
      return null;
    }

    @Override
    public HashMap<String, Float> getStockQuantitiesInPortfolio(String portfolioName,
                                                                String date) {
      log.append(portfolioName);
      log.append(date);
      return new HashMap<String, Float>();
    }

    @Override
    public Boolean addOrderToPortfolioFromController(String portfolio, String date, String action,
                                                     float c, HashMap<String, Float> stocks) {
      return null;
    }

    @Override
    public Float getCostBasis(String portfolioName, String date) {
      return null;
    }

    @Override
    public TreeMap<String, Float> getPerformanceValues(String portfolioName, String date1,
                                                       String date2) {
      return null;
    }

    @Override
    public Float getScale(TreeMap<String, Float> values) {
      return null;
    }
  }
}