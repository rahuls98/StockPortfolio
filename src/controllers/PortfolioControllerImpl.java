package controllers;

import java.io.File;
import java.io.PrintStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

import models.PortfolioModel;
import views.PortfolioView;

/**
 * Represents a controller class that manages a portfolio. It takes user inputs, performs
 * operations based on those inputs, and provides outputs.
 */
public class PortfolioControllerImpl implements PortfolioController {

  private final PortfolioModel model;
  private final PortfolioView view;
  private final Scanner input;
  private final PrintStream output;

  /**
   * Returns a PortfolioController object.
   *
   * @param model Object of the PortfolioModel.
   * @param view  Object of the PortfolioView.
   * @param input Where to receive the program inputs from.
   * @param out   Where to push outputs to.
   */
  public PortfolioControllerImpl(PortfolioModel model, PortfolioView view, InputStream input,
                                 PrintStream out) {
    if ((model == null) || (view == null) || (input == null) || (out == null)) {
      throw new IllegalArgumentException("Null Values not allowed");
    }
    this.model = model;
    this.view = view;
    this.input = new Scanner(input);
    this.output = out;
  }

  @Override
  public void run() {
    this.output.println("\nPlease enter the menu item number when requested.");
    String[] actions = new String[]{"Create Portfolio", "Create Order", "Get portfolio "
            + "composition", "Get portfolio value", "Get Cost Basis", "Get Performance", "Exit"};
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
          this.createForExistingPortfolio();
          break;
        case 3:
          this.getComposition();
          break;
        case 4:
          this.getPortfolioValue();
          break;
        case 5:
          this.getCostBasis();
          break;
        case 6:
          this.getPerformance();
          break;
        case 7:
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

  private Float getFloatFromUser() {
    Float choice;
    while (true) {
      try {
        choice = this.input.nextFloat();
        if (choice < 0) {
          throw new RuntimeException();
        }
        break;
      } catch (Exception e) {
        //To Consume the new Line entered by user.
        this.input.nextLine();
        this.output.print("Please enter a valid positive float value: ");
      }
    }
    return choice;
  }

  private void createForExistingPortfolio() {
    String[] flexiblePortfolios = this.model.getFlexiblePortfolios();
    if (flexiblePortfolios.length == 0) {
      this.output.println("\nYou have no flexible portfolios currently!");
      return;
    }
    String portfolioName = this.displayPortfoliosAndTakeUserInput(flexiblePortfolios);
    this.output.print("\nHow many orders would you like to create?");
    int n = this.getIntegerFromUser();
    for (int i = 0; i < n; i++) {
      this.createOrder(portfolioName);
    }
    this.model.persist();
  }

  private void createPortfolio() {
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

  private void createOrder(String portfolioName) {
    String action = "";
    while (true) {
      String[] actions = new String[]{"BUY order", "SELL order"};
      this.output.println("What type of order would you like to create?");
      this.view.displayActions(actions);
      this.output.print("Select action: ");
      Boolean exitLoop = false;
      switch (this.getIntegerFromUser()) {
        case 1:
          action = "BUY";
          exitLoop = true;
          break;
        case 2:
          action = "SELL";
          exitLoop = true;
          break;
        default:
          this.output.println("Invalid Choice, please enter 1 or 2");
          break;
      }
      if (exitLoop) {
        break;
      }
    }
    this.output.print("Enter date for the order in YYYY-MM-DD format: ");
    String date = this.getWeekdayFromUser();
    this.output.print("Enter commission for this transaction: ");
    Float com = this.getFloatFromUser();
    this.output.print("Enter number of " + action + " transactions: ");
    int numTransactions = this.getIntegerFromUser();
    while (numTransactions <= 0) {
      this.output.print("Please enter a valid number of transactions: ");
      numTransactions = this.getIntegerFromUser();
    }
    String stockName;
    int stockQuantity;
    HashMap<String, Integer> stocks = new HashMap<>();
    for (int i = 0; i < numTransactions; i++) {
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
      stocks.put(stockName, stockQuantity);
    }
    if (!(model.addOrderToPortfolioFromController(portfolioName, date, action, com, stocks))) {
      this.output.println("Invalid Order");
    } else {
      this.output.println("Order Recorded");
    }
  }

  private void createPortfolioManually() {
    String[] actions = new String[]{"Flexible", "Inflexible", "Go back"};
    this.output.println();
    this.output.println("What type of portfolio would you like to create?");
    this.view.displayActions(actions);
    this.output.print("Select action: ");
    int ch = this.getIntegerFromUser();
    switch (ch) {
      case 1:
        this.createFlexiblePortfolioManually();
        break;
      case 2:
        this.createInflexiblePortfolioManually();
        break;
      default:
        break;
    }
  }

  private void createInflexiblePortfolioManually() {
    this.output.println();
    this.output.print("Enter portfolio name: ");
    String portfolioName = this.input.next();
    while (Arrays.stream(this.model.getPortfolios()).anyMatch(portfolioName::equals)) {
      this.output.print("This portfolio already exists, please enter another name: ");
      portfolioName = this.input.next();
    }
    this.output.print("Enter number of stocks: ");
    int n = this.getIntegerFromUser();
    while (n <= 0) {
      this.output.print("Please enter a valid number of stocks: ");
      n = this.getIntegerFromUser();
    }
    HashMap<String, Integer> stocks = new HashMap<>();
    String stockName;
    int stockQuantity;
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
      if (stocks.containsKey(stockName)) {
        i--;
      }
      stocks.put(stockName, stockQuantity);
    }
    this.model.addInflexiblePortfolio(portfolioName, stocks);
    this.model.persist();
    this.output.print("\nNew portfolio (" + portfolioName + ") has been recorded!\n");
  }

  private void createFlexiblePortfolioManually() {
    this.output.println();
    this.output.print("Enter portfolio name: ");
    String portfolioName = this.input.next();
    while (Arrays.stream(this.model.getPortfolios()).anyMatch(portfolioName::equals)) {
      this.output.print("This portfolio already exists, please enter another name: ");
      portfolioName = this.input.next();
    }
    this.model.addFlexiblePortfolio(portfolioName);
    this.output.println("\nA flexible portfolio can have multiple BUY/SELL orders, each "
            + "pertaining to a set of stock transactions.");
    this.output.print("How many orders would you like to create? ");
    int n = this.getIntegerFromUser();
    while (n <= 0) {
      this.output.print("Please enter a valid number of orders: ");
      n = this.getIntegerFromUser();
    }
    for (int i = 0; i < n; i++) {
      this.output.println("\nORDER " + (i + 1));
      this.createOrder(portfolioName);
    }
    this.model.persist();
    this.output.print("\nNew portfolio (" + portfolioName + ") has been recorded!\n");
  }

  private void createPortfolioFromFile() {
    this.output.println();
    this.output.print("Enter path to XML: ");
    String pathToXml = this.input.next();
    File file = new File(pathToXml);
    while (!file.exists()) {
      this.output.print("No such file exists! Please enter a valid path: ");
      pathToXml = this.input.next();
      file = new File(pathToXml);
    }
    String portfolioName;
    try {
      portfolioName = this.model.loadPortfolioNameFromXML(pathToXml);
    } catch (Exception e) {
      this.output.println("\nXML File Invalid!");
      return;
    }
    while (Arrays.stream(this.model.getPortfolios()).anyMatch(portfolioName::equals)) {
      this.output.print("This portfolio already exists! Please try another name: ");
      portfolioName = this.input.next();
    }
    try {
      this.model.loadPortfolioFromXml(pathToXml);
    } catch (Exception e) {
      this.output.println("\nXML File Invalid!");
      return;
    }
    this.model.persist();
    this.output.print("\nNew portfolio (" + portfolioName + ") has been recorded!\n");
  }

  private void getComposition() {
    String[] portfolios = model.getPortfolios();
    if (portfolios.length == 0) {
      this.output.println("\nYou have no portfolios currently!");
      return;
    }
    String portfolioName = displayPortfoliosAndTakeUserInput(portfolios);
    String date;
    if (Arrays.asList(model.getInflexiblePortfolios()).contains(portfolioName)) {
      //It is an inflexible portfolio
      date = LocalDate.now().toString();
    } else {
      //It is a flexible portfolio
      this.output.print("\nEnter a date for viewing the composition: ");
      date = this.getDateFromUser();
    }
    this.output.println();
    view.displayPortfolioComposition(portfolioName,
            this.model.getStockQuantitiesInPortfolio(portfolioName, date));
  }

  private void getPortfolioValue() {
    String[] portfolios = model.getPortfolios();
    if (portfolios.length == 0) {
      this.output.println("\nYou have no portfolios currently!");
      return;
    }
    String portfolioName = displayPortfoliosAndTakeUserInput(portfolios);
    String date = this.getDate();
    this.output.println();
    view.displayPortfolioValue(portfolioName, model.getPortfolioValues(portfolioName, date));
    this.output.println("Total value of portfolio on is $" + String.format("%.4f",
            model.getPortfolioTotal(portfolioName, date)));
  }

  private void getCostBasis() {
    String[] portfolios = model.getFlexiblePortfolios();
    if (portfolios.length == 0) {
      this.output.println("\nYou have no flexible portfolios currently!");
      return;
    }
    String portfolioName = displayPortfoliosAndTakeUserInput(portfolios);
    this.output.print("Enter the date to calculate the cost basis for: ");
    String date = this.getDateFromUser();
    this.output.println();
    view.displayCostBasis(portfolioName, date, model.getCostBasis(portfolioName, date));
  }

  private void getPerformance() {
    String[] portfolios = model.getPortfolios();
    if (portfolios.length == 0) {
      this.output.println("\nYou have no portfolios currently!");
      return;
    }
    String portfolioName = this.displayPortfoliosAndTakeUserInput(portfolios);
    this.output.print("Enter lower date: ");
    String d1 = this.getWeekdayFromUser();
    this.output.print("Enter upper date: ");
    String d2 = this.getWeekdayFromUser();
    TreeMap<String, Float> performanceValues = this.model.getPerformanceValues(portfolioName, d1,
            d2);
    this.view.displayPerformance(portfolioName, performanceValues,
            this.model.getScale(performanceValues));
  }

  private String displayPortfoliosAndTakeUserInput(String[] portfolios) {
    this.output.println("\nWhich portfolio would you like to use?");
    view.displayPortfolios(portfolios);
    this.output.print("Select portfolio: ");
    int choice = this.input.nextInt();
    while ((choice <= 0) || (choice > portfolios.length)) {
      this.output.print("Please make a valid entry from the choices above: ");
      choice = this.input.nextInt();
    }
    return portfolios[choice - 1];
  }

  private String getDate() {
    this.output.print("Enter the date for which you want the value: ");
    String strDate = this.input.next();
    while ((!(this.model.isValidDate(strDate))) ||
            (LocalDate.parse(strDate).compareTo(LocalDate.now()) >= 0) ||
            (LocalDate.parse(strDate).compareTo(LocalDate.parse("2011-03-01")) <= 0)) {
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

  private String getWeekdayFromUser() {
    String strDate = this.input.next();
    while ((!(this.model.isValidDate(strDate)))
            || (LocalDate.parse(strDate).compareTo(LocalDate.now()) >= 0)
            || (LocalDate.parse(strDate).compareTo(LocalDate.parse("2011-03-01")) <= 0)
            || (DayOfWeek.of(LocalDate.parse(strDate).get(ChronoField.DAY_OF_WEEK))
            == DayOfWeek.SATURDAY)
            || (DayOfWeek.of(LocalDate.parse(strDate).get(ChronoField.DAY_OF_WEEK))
            == DayOfWeek.SUNDAY)) {
      this.output.print("Please enter a valid date that isn't a weekend: ");
      strDate = this.input.next();
    }
    return strDate;
  }

  private String getDateFromUser() {
    String strDate = this.input.next();
    while ((!(this.model.isValidDate(strDate)))
            || (LocalDate.parse(strDate).compareTo(LocalDate.now()) >= 0)
            || (LocalDate.parse(strDate).compareTo(LocalDate.parse("2011-03-01")) <= 0)) {
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
      this.output.println("Considering Date for the previous Friday (" + date.toString() + ")");
    }
    return date.toString();
  }
}
