package controllers;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Random;

import models.User;
import models.PortfolioModel;
import models.PortfolioModelImpl;
import views.PortfolioView;
import views.PortfolioViewImpl;

import static org.junit.Assert.assertEquals;

public class PortfolioControllerImplTest {
  private OutputStream out;
  private String userName;
  private InputStream input;
  private User defaultUser;
  private PortfolioModel model;
  private PortfolioView view;

  @Before
  public void setUp() {
    this.out = new ByteArrayOutputStream();
    this.userName = "default";
    this.defaultUser = new User(userName);
    try {
      this.model = new PortfolioModelImpl(userName);
    } catch (Exception e) {
      e.printStackTrace();
    }
    this.view = new PortfolioViewImpl(new PrintStream(out));
    File myObj = new File("localStorage.xml");
    myObj.delete();
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
    String generatedString = buffer.toString();
    return generatedString;
  }

  private String prepareString(String s) {
    return s.replace("\r", "").replace("\n", "");
  }

  // todo : testPortfolioControllerImplInstantiation
  // todo : testPortfolioControllerImplInstantiationWithInvalidInput
  // testGo shows starting menu
  @Test
  public void testStartingMenu() {
    InputStream input = new ByteArrayInputStream("4".getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, defaultUser, input, new PrintStream(out));
    controller.go();
    String expectedOutput = "Please enter the menu item number when requested." +
            "What would you like to do?1. Create portfolio2." +
            " Get portfolio composition3. Get portfolio value4." +
            " ExitSelect action: ";
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  // testGo action input with non int action
  @Test
  public void testInputWithNonIntAction() throws FileNotFoundException {
    InputStream input = new ByteArrayInputStream("a\n4\n".getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, defaultUser, input, new PrintStream(out));
    controller.go();
    String expectedOutput = "Please enter the menu item number when requested." +
            "What would you like to do?1. Create portfolio2. Get portfolio composition" +
            "3. Get portfolio value4. ExitSelect action: Please enter a valid integer value: ";

    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));

  }

  //testGo action with invalid choice
  @Test
  public void testInvalidChoice() {
    InputStream input = new ByteArrayInputStream("6\n4\n".getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, defaultUser, input, new PrintStream(out));
    controller.go();
    String expectedOutput = "Please enter the menu item number when requested." +
            "What would you like to do?1. Create portfolio2. Get portfolio composition" +
            "3. Get portfolio value4. ExitSelect action: Invalid choice, please try again!" +
            "What would you like to do?1. Create portfolio2. Get portfolio composition" +
            "3. Get portfolio value4. ExitSelect action: ";
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
    ;
  }

  // testGo with Exit
  @Test
  public void testGoWithExit() {
    InputStream input = new ByteArrayInputStream("4".getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, defaultUser, input, new PrintStream(out));
    controller.go();
    String expectedOutput = "Please enter the menu item number when requested." +
            "What would you like to do?1. Create portfolio" +
            "2. Get portfolio composition3. Get portfolio value" +
            "4. ExitSelect action: ";
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  // testGo with input action create portfolio
  @Test
  public void testGoWithCreatePortfolioRegularFlow() {
    String generatedString = this.genRandomString();
    String s = "1\n1\n" + generatedString + "\n1\nAAPL\n1\n4\n";
    InputStream input = new ByteArrayInputStream(s.getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, defaultUser, input, new PrintStream(out));
    controller.go();
    String expectedOutput = "Please enter the menu item number when requested." +
            "What would you like to do?1. Create portfolio2. Get portfolio composition" +
            "3. Get portfolio value4. ExitSelect action: How would you like to enter the " +
            "portfolio details?1. Enter manually2. Load from file3. Go backSelect " +
            "action: Enter portfolio name: Enter number of stocks: Stock 1 ticker: Quantity : " +
            "New portfolio ("+generatedString+") has been recorded!What would you like to do?" +
            "1. Create portfolio2. Get portfolio composition3. Get portfolio value" +
            "4. ExitSelect action: ";
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  // test create portfolio invalid submenu
  @Test
  public void testGoWithCreatePortfolioInvalidFlow() {
    String generatedString = this.genRandomString();
    String s = "1\n1\n" + generatedString + "\n-1\n1\nAAPL\n1\n4\n";
    InputStream input = new ByteArrayInputStream(s.getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, defaultUser, input, new PrintStream(out));
    controller.go();
    String expectedOutput = "Please enter the menu item number when requested.What would you" +
            " like to do?1. Create portfolio2. Get portfolio composition3. Get portfolio value" +
            "4. ExitSelect action: How would you like to enter the portfolio details?1. Enter " +
            "manually2. Load from file3. Go backSelect action: Enter portfolio name: Enter num" +
            "ber of stocks: Please enter a valid number of stocks: Stock 1 ticker: Quantity : " +
            "New portfolio ("+generatedString+") has been recorded!What would you like to do?1. Create " +
            "portfolio2. Get portfolio composition3. Get portfolio value4. ExitSelect action: ";
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  //  test create portfolio non int submenu
  @Test
  public void testGoWithCreatePortfolioInvalidNonIntFlow() {
    InputStream input = new ByteArrayInputStream("1\n1\ntestFromJunit3\na\n1\nAAPL\n1\n4\n".getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, defaultUser, input, new PrintStream(out));
    controller.go();
    String expectedOutput = "1. Create portfolio2." +
            " Get portfolio composition3. Get portfolio value4." +
            " ExitEnter action: 1. Enter manually2. Load from fileSelect action:" +
            " Enter portfolio name: Enter number of stocks: Enter Valid Integer:" +
            " Stock 1 ticker: Quantity : N" +
            "ew portfolio (testFromJunit3) has been recorded!1. Create portfolio2. G" +
            "et portfolio composition3. Get portfolio value4. ExitEnter action: ";
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  //  test create portfolio with existing portfolio path
  @Test
  public void testGoWithCreatePortfolioWithPath() {
    InputStream input = new ByteArrayInputStream("1\n2\ntestFile.xml\n4\n".getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, defaultUser, input, new PrintStream(out));
    controller.go();
    String expectedOutput = "1. Create portfolio2. Get portfolio composition3. " +
            "Get portfolio value4. ExitEnter action: 1. Enter manually2. " +
            "Load from fileSelect action: Path to XML: New portfolio (Test from BJ File) has been recorded!1. " +
            "Create portfolio2. Get portfolio composition3. Get portfolio value4. ExitEnter action: ";
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  // test portFolioValue
  @Test
  public void testPortfolioValue() {
    InputStream input = new ByteArrayInputStream("3\n1\n2022-10-31\n4\n".getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, defaultUser, input, new PrintStream(out));
    controller.go();
    String expectedOutput = "1. Create portfolio2. Get portfolio composition3. Get portfolio value4." +
            " ExitEnter action: 1. testFromJunit42. a3. kynudxzlvw4. uvmvdzkyis5. testFromJunit26. " +
            "testFromJunit37. ahuqaivyuc8. -19. Test from BJ File10. testFromJunit1Select Portfolio: " +
            "Enter the date for which you want the valuePortfolio: testFromJunit4---------------" +
            "------------Stock       |  Value       ---------------------------AAPL        |  153.34 " +
            "     ---------------------------Total value of portfolio on is 153.34001. Create" +
            " portfolio2. Get portfolio composition3. Get portfolio value4. ExitEnter action: ";
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  // test portFolioValue
  @Test
  public void testPortfolioValueWeekend() {
    InputStream input = new ByteArrayInputStream("3\n1\n2022-10-30\n4\n".getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, defaultUser, input, new PrintStream(out));
    controller.go();
    String expectedOutput = "1. Create portfolio2. Get portfolio composition3. Get portfolio value" +
            "4. ExitEnter action: 1. testFromJunit42. a3. kynudxzlvw4. uvmvdzkyis5. testFromJunit26." +
            " testFromJunit37. ahuqaivyuc8. -19. Test from BJ File10. testFromJunit1Select Portfolio" +
            ": Enter the date for which you want the valueEntered day is a weekendCalculating Value " +
            "on Friday before the weekend on 2022-10-28Portfolio: testFromJunit4-------------------" +
            "--------Stock       |  Value       ---------------------------AAPL        |  155.74    " +
            "  ---------------------------Total value of portfolio on is 155.74001. Create portfolio" +
            "2. Get portfolio composition3. Get portfolio value4. ExitEnter action: ";
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }
}