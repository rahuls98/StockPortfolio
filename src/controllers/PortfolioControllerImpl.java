package controllers;

import java.io.File;
import java.io.PrintStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

import entities.Portfolio;
import entities.Stock;
import entities.User;
import models.PortfolioModel;
import views.PortfolioView;

/**
 * Represents a controller class that manages a portfolio. It takes user inputs, performs
 * operations based on those inputs, and provides outputs.
 */
public class PortfolioControllerImpl implements PortfolioController {

  private final PortfolioModel model;
  private final PortfolioView view;
  private final User user;
  private final Scanner input;
  private final PrintStream output;

  /**
   * Returns a PortfolioController object.
   *
   * @param model Object of the PortfolioModel.
   * @param view  Object of the PortfolioView.
   * @param user  Current user.
   * @param input Where to receive the program inputs from.
   * @param out   Where to push outputs to.
   */
  public PortfolioControllerImpl(PortfolioModel model, PortfolioView view, User user,
                                 InputStream input, PrintStream out) {
    // TODO : handle nulls
    this.model = model;
    this.view = view;
    this.user = user;
    this.input = new Scanner(input).useDelimiter("\n");
    this.output = out;
  }

  @Override
  public void go() {
    this.output.println("\nPlease enter the menu item number when requested.");
    String[] actions = new String[]{"Create portfolio", "Get portfolio composition",
            "Get portfolio value", "Exit"};
    while (true) {
      this.output.println();
      this.output.println("What would you like to do?");
      view.displayActions(actions);
      this.output.print("Select action: ");
      int choice = this.getIntegerFromUser();
      switch (choice) {
        case 1:
          this.createPortfolio();
          break;
        case 2:
          this.getComposition();
          break;
        case 3:
          this.getPortfolioValue();
          break;
        case 4:
          return;
        default:
          this.output.print("\nInvalid choice, please try again!\n");
          break;
      }
    }
  }

  private int getIntegerFromUser() {
    int choice;
    while (true) {
      try {
        choice = this.input.nextInt();
        break;
      } catch (Exception e) {
        //To Consume the new Line entered by user.
        this.input.nextLine();
        this.output.print("Please enter a valid integer value: ");
      }
    }
    return choice;
  }

  private void createPortfolio() {
    Portfolio portfolio = null;
    while (true) {
      String[] actions = new String[]{"Enter manually", "Load from file", "Go back"};
      this.output.println();
      this.output.println("How would you like to enter the portfolio details?");
      view.displayActions(actions);
      this.output.print("Select action: ");
      int choice = this.getIntegerFromUser();
      switch (choice) {
        case 1:
          this.createPortfolioManually();
          return;
        case 2:
          this.createPortfolioFromFile();
          return;
        case 3:
          return;
        default:
          this.output.print("Invalid choice, please try again!\n");
          break;
      }
    }
  }

  private void createPortfolioManually() {
    Portfolio portfolio = null;
    this.output.println();
    this.output.print("Enter portfolio name: ");
    String portfolioName = this.input.next();
    while (Arrays.stream(this.model.getPortfolios()).anyMatch(portfolioName::equals)) {
      this.output.print("This portfolio already exists, please enter another name: ");
      portfolioName = this.input.next();
    }
    portfolio = new Portfolio(portfolioName);
    this.output.print("Enter number of stocks: ");
    int n = this.getIntegerFromUser();
    ;
    while (n <= 0) {
      this.output.print("Please enter a valid number of stocks: ");
      n = this.getIntegerFromUser();
      ;
    }
    String stockName;
    int stockQuantity;
    Stock stock;
    for (int i = 0; i < n; i++) {
      this.output.print("Stock " + (i + 1) + " ticker: ");
      stockName = this.input.next();
      while (!(model.isValidTicker(stockName))) {
        this.output.print("Please enter a valid ticker name: ");
        stockName = this.input.next();
      }
      this.output.print("Quantity : ");
      stockQuantity = this.getIntegerFromUser();
      while (stockQuantity <= 0) {
        this.output.print("Please enter a valid quantity: ");
        stockQuantity = this.getIntegerFromUser();
      }
      if (portfolio.getStockNames().contains(stockName)) {
        i--;
      }
      stock = new Stock(stockName);
      portfolio.addStock(stock, stockQuantity);
    }
    user.addPortfolio(portfolio);
    model.addPortfolio(user);
    this.output.print("\nNew portfolio (" + portfolio.getName() + ") has been recorded!\n");
  }

  private void createPortfolioFromFile() {
    Portfolio portfolio = null;
    this.output.println();
    this.output.print("Enter path to XML: ");
    String pathToXml = this.input.next();
    File file = new File(pathToXml);
    while (!file.exists()) {
      this.output.print("No such file exists! Please enter a valid path: ");
      pathToXml = this.input.next();
      file = new File(pathToXml);
    }
    try {
      portfolio = model.loadPortfolioFromXml(pathToXml);
    } catch (IllegalArgumentException e) {
      this.output.println("\nXML File Invalid");
      return;
    }
    String portfolioName = portfolio.getName();
    while (Arrays.stream(this.model.getPortfolios()).anyMatch(portfolioName::equals)) {
      this.output.print("This portfolio already exists! Please try another name: ");
      portfolioName = this.input.next();
    }
    portfolio.setName(portfolioName);
    user.addPortfolio(portfolio);
    model.addPortfolio(user);
    this.output.print("\nNew portfolio (" + portfolio.getName() + ") has been recorded!\n");
  }

  private void getComposition() {
    String[] portfolios = model.getPortfolios();
    if (portfolios.length == 0) {
      this.output.println("\nYou have no portfolios currently!");
      return;
    }
    this.output.println("\nWhich portfolio would you like to explore?");
    String portfolioName = displayPortfoliosAndTakeUserInput(portfolios);
    this.output.println();
    view.displayPortfolioComposition(portfolioName, model.getPortfolio(portfolioName).getStockQuantities());
  }

  private void getPortfolioValue() {
    String[] portfolios = model.getPortfolios();
    if (portfolios.length == 0) {
      this.output.println("\nYou have no portfolios currently!");
      return;
    }
    this.output.println("\nWhich portfolio would you like to explore?");
    String portfolioName = displayPortfoliosAndTakeUserInput(portfolios);
    String date = this.getDate();
    this.output.println();
    view.displayPortfolioValue(portfolioName, model.getPortfolioValues(portfolioName, date));
    this.output.println("Total value of portfolio on is " + String.format("%.4f",
            model.getPortfolioTotal(portfolioName, date)));
  }

  private String displayPortfoliosAndTakeUserInput(String[] portfolios) {
    view.displayPortfolios(portfolios);
    this.output.print("Select portfolio: ");
    int choice = this.input.nextInt();
    while ((choice <= 0) || (choice > portfolios.length)) {
      this.output.print("Please make a valid entry from the choices above: ");
      choice = this.input.nextInt();
    }
    String portfolioName = portfolios[choice - 1];
    return portfolioName;
  }

  private String getDate() {
    //TODO: Perform Validation on input date.
    this.output.print("Enter the date for which you want the value: ");
    String strDate = this.input.next();
    while (!(this.model.isValidDate(strDate))) {
      this.output.print("Please enter a valid date: ");
      strDate = this.input.next();
    }
    LocalDate date = LocalDate.parse(strDate);
    DayOfWeek day = DayOfWeek.of(date.get(ChronoField.DAY_OF_WEEK));
    if ((day == DayOfWeek.SUNDAY) || (day == DayOfWeek.SATURDAY)) {
      this.output.println("\nEntered day is a weekend.");
      if (day == DayOfWeek.SATURDAY) {
        date = date.minusDays(1);
      } else {
        date = date.minusDays(2);
      }
      this.output.println("Calculating value for the previous Friday (" + date.toString() + ")");
    }
    return date.toString();
  }
}
