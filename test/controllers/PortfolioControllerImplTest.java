package controllers;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import entities.User;
import models.PortfolioModel;
import models.PortfolioModelImpl;
import views.PortfolioView;
import views.PortfolioViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    String expectedOutput = "1. Create portfolio\r\n" +
            "2. Get portfolio composition\r\n" +
            "3. Get portfolio value\r\n" +
            "4. Exit\r\n" +
            "Enter action: ";
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  // testGo action input with non int action
  @Test
  public void testInputWithNonIntAction() throws FileNotFoundException {
    InputStream input = new ByteArrayInputStream("a\n4\n".getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, defaultUser, input, new PrintStream(out));
    controller.go();
    String expectedOutput = "1. Create portfolio\r\n" +
            "2. Get portfolio composition\r\n" +
            "3. Get portfolio value\r\n" +
            "4. Exit\r\n" +
            "Enter action: Enter Valid Integer:\n";

    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));

  }

  //testGo action with invalid choice
  @Test
  public void testInvalidChoice() {
    InputStream input = new ByteArrayInputStream("6\n4\n".getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, defaultUser, input, new PrintStream(out));
    controller.go();
    String expectedOutput = "1. Create portfolio\r\n" +
            "2. Get portfolio composition\r\n" +
            "3. Get portfolio value\r\n" +
            "4. Exit\r\n" +
            "Enter action: \r\n" +
            "Invalid choice\r\n\n" +
            "1. Create portfolio\r\n" +
            "2. Get portfolio composition\r\n" +
            "3. Get portfolio value\r\n" +
            "4. Exit\r\n" +
            "Enter action: ";
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
    ;
  }

  // testGo with Exit
  @Test
  public void testGoWithExit() {
    InputStream input = new ByteArrayInputStream("4".getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, defaultUser, input, new PrintStream(out));
    controller.go();
    String expectedOutput = "1. Create portfolio\r\n" +
            "2. Get portfolio composition\r\n" +
            "3. Get portfolio value\r\n" +
            "4. Exit\r\n" +
            "Enter action: ";
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }
  // testGo with input action create portfolio
  @Test
  public void testGoWithCreatePortfolioRegularFlow() {
    InputStream input = new ByteArrayInputStream("1\n1\ntestFromJunit4\n1\nAAPL\n1\n4\n".getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, defaultUser, input, new PrintStream(out));
    controller.go();
    String expectedOutput = "1. Create portfolio2." +
            " Get portfolio composition3. Get portfolio value4." +
            " ExitEnter action: 1. Enter manually2. Load from fileSelect action:" +
            " Enter portfolio name: Enter number of stocks: Stock 1 ticker: Quantity : N" +
            "ew portfolio (testFromJunit4) has been recorded!1. Create portfolio2. G" +
            "et portfolio composition3. Get portfolio value4. ExitEnter action: ";
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

  // test create portfolio invalid submenu
  @Test
  public void testGoWithCreatePortfolioInvalidFlow() {
    InputStream input = new ByteArrayInputStream("1\n1\ntestFromJunit1\n-1\n1\nAAPL\n1\n4\n".getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, defaultUser, input, new PrintStream(out));
    controller.go();
    String expectedOutput = "1. Create portfolio2." +
            " Get portfolio composition3. Get portfolio value4." +
            " ExitEnter action: 1. Enter manually2. Load from fileSelect action:" +
            " Enter portfolio name: Enter number of stocks: Enter a valid number of Stocks:" +
            " Stock 1 ticker: Quantity : N" +
            "ew portfolio (testFromJunit1) has been recorded!1. Create portfolio2. G" +
            "et portfolio composition3. Get portfolio value4. ExitEnter action: ";
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

  // todo : test portFolioValue
  @Test
  public void testPortfolioValue() {
    InputStream input = new ByteArrayInputStream("3\n1\n2022-10-31\n4\n".getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, defaultUser, input, new PrintStream(out));
    controller.go();
    String expectedOutput = "1. Create portfolio2. Get portfolio composition3. " +
            "Get portfolio value4. ExitEnter action: 1. Enter manually2. " +
            "Load from fileSelect action: Path to XML: New portfolio (Test from BJ File) has been recorded!1. " +
            "Create portfolio2. Get portfolio composition3. Get portfolio value4. ExitEnter action: ";
    assertEquals(prepareString(expectedOutput), prepareString(out.toString()));
  }

}