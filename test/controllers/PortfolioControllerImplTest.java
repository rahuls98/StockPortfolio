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
import java.util.Random;

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
    return s.replace("\r", "").replace("\n", "");
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
    InputStream input = new ByteArrayInputStream("4".getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, input,
            new PrintStream(out));
    controller.run();
    String expectedOutput = "Please enter the menu item number when requested." +
            "What would you like to do?1. Create portfolio2." +
            " Get portfolio composition3. Get portfolio value4." +
            " ExitSelect action: ";
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  @Test
  public void testInputWithNonIntAction() {
    InputStream input = new ByteArrayInputStream("a\n4\n".getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, input,
            new PrintStream(out));
    controller.run();
    String expectedOutput = "Please enter the menu item number when requested." +
            "What would you like to do?1. Create portfolio2. Get portfolio composition" +
            "3. Get portfolio value4. ExitSelect action: Please enter a valid integer value: ";
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));

  }

  @Test
  public void testInvalidChoice() {
    InputStream input = new ByteArrayInputStream("6\n4\n".getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, input,
            new PrintStream(out));
    controller.run();
    String expectedOutput = "Please enter the menu item number when requested." +
            "What would you like to do?1. Create portfolio2. Get portfolio composition" +
            "3. Get portfolio value4. ExitSelect action: Invalid choice, please try again!" +
            "What would you like to do?1. Create portfolio2. Get portfolio composition" +
            "3. Get portfolio value4. ExitSelect action: ";
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  @Test
  public void testNonIntChoice() {
    InputStream input = new ByteArrayInputStream("a\n4\n".getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, input,
            new PrintStream(out));
    controller.run();
    String expectedOutput = "Please enter the menu item number when requested.\n" +
            "\n" +
            "What would you like to do?\n" +
            "1. Create portfolio\n" +
            "2. Get portfolio composition\n" +
            "3. Get portfolio value\n" +
            "4. Exit\n" +
            "Select action: \n" +
            "Please enter a valid integer value: \n";
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  @Test
  public void testGoWithExit() {
    InputStream input = new ByteArrayInputStream("4".getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, input,
            new PrintStream(out));
    controller.run();
    String expectedOutput = "Please enter the menu item number when requested." +
            "What would you like to do?1. Create portfolio" +
            "2. Get portfolio composition3. Get portfolio value" +
            "4. ExitSelect action: ";
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  @Test
  public void testGoWithCreatePortfolioRegularFlow() {
    String generatedString = this.genRandomString();
    String s = "1\n1\n" + generatedString + "\n1\nAAPL\n1\n4\n";
    InputStream input = new ByteArrayInputStream(s.getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, input,
            new PrintStream(out));
    controller.run();
    String expectedOutput = "Please enter the menu item number when requested." +
            "What would you like to do?1. Create portfolio2. Get portfolio composition" +
            "3. Get portfolio value4. ExitSelect action: How would you like to enter the " +
            "portfolio details?1. Enter manually2. Load from file3. Go backSelect " +
            "action: Enter portfolio name: Enter number of stocks: Stock 1 ticker: Quantity : " +
            "New portfolio (" + generatedString + ") has been recorded!What would you like to do?" +
            "1. Create portfolio2. Get portfolio composition3. Get portfolio value" +
            "4. ExitSelect action: ";
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  @Test
  public void testGoWithCreatePortfolioInvalidFlow() {
    String generatedString = this.genRandomString();
    String s = "1\n1\n" + generatedString + "\n-1\n1\nAAPL\n1\n4\n";
    InputStream input = new ByteArrayInputStream(s.getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, input,
            new PrintStream(out));
    controller.run();
    String expectedOutput = "Please enter the menu item number when requested.What would you" +
            " like to do?1. Create portfolio2. Get portfolio composition3. Get portfolio value" +
            "4. ExitSelect action: How would you like to enter the portfolio details?1. Enter " +
            "manually2. Load from file3. Go backSelect action: Enter portfolio name: Enter num" +
            "ber of stocks: Please enter a valid number of stocks: Stock 1 ticker: Quantity : " +
            "New portfolio (" + generatedString + ") has been recorded!What would you like to do?" +
            "1. Create " +
            "portfolio2. Get portfolio composition3. Get portfolio value4. ExitSelect action: ";
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  @Test
  public void testGoWithCreatePortfolioWithFractional() {
    String generatedString = this.genRandomString();
    String s = "1\n1\n" + generatedString + "\n1\nAAPL\n1.5\n1\n4\n";
    InputStream input = new ByteArrayInputStream(s.getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, input,
            new PrintStream(out));
    controller.run();
    String expectedOutput = "Please enter the menu item number when requested.\n" +
            "\n" +
            "What would you like to do?\n" +
            "1. Create portfolio\n" +
            "2. Get portfolio composition\n" +
            "3. Get portfolio value\n" +
            "4. Exit\n" +
            "Select action: \n" +
            "\n" +
            "How would you like to enter the portfolio details?\n" +
            "1. Enter manually\n" +
            "2. Load from file\n" +
            "3. Go back\n" +
            "Select action: \n" +
            "\n" +
            "Enter portfolio name: \n" +
            "Enter number of stocks: \n" +
            "Stock 1 ticker: \n" +
            "Quantity : \n" +
            "Please enter a valid integer value: \n" +
            "\n" +
            "New portfolio (" + generatedString + ") has been recorded!\n" +
            "\n" +
            "What would you like to do?\n" +
            "1. Create portfolio\n" +
            "2. Get portfolio composition\n" +
            "3. Get portfolio value\n" +
            "4. Exit\n" +
            "Select action: \n";
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  @Test
  public void testGoWithCreatePortfolioInvalidNonIntFlow() {
    String generatedString = this.genRandomString();
    String s = "1\n1\n" + generatedString + "\na\n1\nAAPL\n1\n4\n";
    InputStream input = new ByteArrayInputStream(s.getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, input,
            new PrintStream(out));
    controller.run();
    String expectedOutput = "Please enter the menu item number when requested.\n" +
            "\n" +
            "What would you like to do?\n" +
            "1. Create portfolio\n" +
            "2. Get portfolio composition\n" +
            "3. Get portfolio value\n" +
            "4. Exit\n" +
            "Select action: \n" +
            "\n" +
            "How would you like to enter the portfolio details?\n" +
            "1. Enter manually\n" +
            "2. Load from file\n" +
            "3. Go back\n" +
            "Select action: \n" +
            "\n" +
            "Enter portfolio name: \n" +
            "Enter number of stocks: \n" +
            "Please enter a valid integer value: \n" +
            "Stock 1 ticker: \n" +
            "Quantity : \n" +
            "\n" +
            "New portfolio (" + generatedString + ") has been recorded!\n" +
            "\n" +
            "What would you like to do?\n" +
            "1. Create portfolio\n" +
            "2. Get portfolio composition\n" +
            "3. Get portfolio value\n" +
            "4. Exit\n" +
            "Select action: \n" +
            "\n";
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
    String s = "1\n1\n" + generatedString + "\n1\nAAPL\n1\n3\n1\n2022-11-03\n2022-10-31\n4\n";
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
}