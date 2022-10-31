package controllers;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import entities.User;
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
    this.model = new PortfolioModelImpl(userName);
    this.view = new PortfolioViewImpl(new PrintStream(out));
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
    assertEquals(expectedOutput, out.toString());
  }

  // testGo action input with non int action
  @Test
  public void testInputWithNonIntAction() {
    InputStream input = new ByteArrayInputStream("a\n 4\n".getBytes());
    PortfolioController controller = new PortfolioControllerImpl(model, view, defaultUser, input, new PrintStream(out));
    controller.go();
    String expectedOutput = "1. Create portfolio\r\n" +
            "2. Get portfolio composition\r\n" +
            "3. Get portfolio value\r\n" +
            "4. Exit\r\n" +
            "Enter action: ";
    assertEquals(expectedOutput, out.toString());

  }
  // todo : testGo action with invalid choice
  // todo : testGo with Exit
  // todo : testGo with input action create portfolio
  // todo : test create portfolio shows submenu
  // todo : test create portfolio invalid submenu
  // todo : test create portfolio non int submenu
  // todo : test create portfolio create manually validate output
  // todo : test create portfolio create from file validate ask for path
  // todo : test create portfolio manually with existing portfolio
  // todo : test create portfolio
  // todo : test create portfolio
  // todo : test create portfolio
  // todo : testGo
  // todo : testGo
}