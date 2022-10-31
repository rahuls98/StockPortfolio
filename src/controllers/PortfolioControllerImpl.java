package controllers;

import java.io.OutputStream;
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
import models.FileModelXmlImpl;
import models.PortfolioModel;
import views.PortfolioView;

/**
 * Description of class.
 */
public class PortfolioControllerImpl implements PortfolioController {

  private final PortfolioModel model;
  private final PortfolioView view;
  private final User user;
  private final Scanner input;
  private final PrintStream output;

  /**
   * Description of constructor.
   *
   * @param model desc.
   * @param view  desc.
   * @param user  desc.
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
    while (true) {
      String[] actions = new String[]{"Create portfolio", "Get portfolio composition",
              "Get portfolio value", "Exit"};
      view.displayActions(actions);
      this.output.print("Enter action: ");
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
          this.output.println("\nInvalid choice\n");
          break;
      }
    }
  }

  /**
   * Loops if User enters a non Integer, prompting to try again,
   * Else breaks and return Int.
   * @return User entered Integer.
   */
  private int getIntegerFromUser() {
    int choice;
    while (true) {
      try {
        choice = this.input.nextInt();
        break;
      } catch (Exception e) {
        //To Consume the new Line entered by user.
        this.input.nextLine();
        System.out.println("Enter Valid Integer:");
      }
    }
    return choice;
  }

  private void createPortfolio() {
    Portfolio portfolio = null;
    while (true) {
      String[] actions = new String[]{"Enter manually", "Load from file"};
      view.displayActions(actions);
      this.output.print("Select action: ");
      int choice = this.getIntegerFromUser();
      switch (choice) {
        case 1:
          this.output.print("\nEnter portfolio name: ");
          String portfolioName = this.input.next();
          while (Arrays.stream(this.model.getPortfolios()).anyMatch(portfolioName::equals)) {
            System.out.print("Portfolio Exists, Enter new name:");
            portfolioName = this.input.next();
          }
          portfolio = new Portfolio(portfolioName);
          this.output.print("Enter number of stocks: ");
          int n = this.getIntegerFromUser();;
          while (n <= 0) {
            this.output.print("Enter a valid number of Stocks: ");
            n = this.getIntegerFromUser();;
          }
          String stockName;
          int stockQuantity;
          Stock stock;
          for (int i = 0; i < n; i++) {
            //TODO: deal with same stock again
            this.output.print("Stock " + (i + 1) + " ticker: ");
            stockName = this.input.next();
            while (!(model.isValidTicker(stockName))) {
              this.output.println("Enter Valid Ticker Name: ");
              stockName = this.input.next();
            }
            this.output.print("Quantity : ");
            stockQuantity = this.input.nextInt();
            // TODO : Validate not 0, negative, fractional
            stock = new Stock(stockName);
            portfolio.addStock(stock, stockQuantity);
          }
          user.addPortfolio(portfolio);
          model.addPortfolio(user);
          this.output.println("\nNew portfolio (" + portfolio.getName() + ") has been recorded!");
          return;
        case 2:
          this.output.print("Path to XML: ");
          String pathToXml = this.input.next();
          portfolio = model.readPortfolioFromXml(pathToXml);
          // todo : check if portfolio with same name already exists
          user.addPortfolio(portfolio);
          model.addPortfolio(user);
          this.output.println("\nNew portfolio (" + portfolio.getName() + ") has been recorded!");
          return;
        default:
          this.output.println("Invalid choice");
          break;
      }
    }
  }

  private void getComposition() {
    String[] portfolios = model.getPortfolios();
    String portfolioName = displayPortfoliosAndTakeUserInput(portfolios);
    view.displayPortfolioComposition(portfolioName, model.getPortfolio(portfolioName).getStockQuantities());
  }

  private void getPortfolioValue() {
    String[] portfolios = model.getPortfolios();
    if (portfolios.length == 0) {
      this.output.println("No portfolios to display, Try entering portfolios first");
      return;
    }
    String portfolioName = displayPortfoliosAndTakeUserInput(portfolios);
    String date = this.getDate();
    view.displayPortfolioValue(portfolioName, model.getPortfolioValues(portfolioName, date));
    this.output.println("Total value of portfolio on is " + String.format("%.4f",
            model.getPortfolioTotal(portfolioName, date)));
  }

  private String displayPortfoliosAndTakeUserInput(String[] portfolios) {
    view.displayPortfolios(portfolios);
    this.output.print("Select Portfolio: ");
    int choice = this.input.nextInt();
    while ((choice <= 0) || (choice > portfolios.length)) {
      this.output.println("Enter a valid number for Portfolio ");
      choice = this.input.nextInt();
    }
    String portfolioName = portfolios[choice - 1];
    return portfolioName;
  }

  private String getDate() {
    //TODO: Perform Validation on input date.
    this.output.println("Enter the date for which you want the value");
    Scanner sc = new Scanner(System.in);
    String strDate = sc.next();
    LocalDate date = LocalDate.parse(strDate);
    DayOfWeek day = DayOfWeek.of(date.get(ChronoField.DAY_OF_WEEK));
    if ((day == DayOfWeek.SUNDAY) || (day == DayOfWeek.SATURDAY)) {
      this.output.println("Entered day is a weekend");
      if (day == DayOfWeek.SATURDAY) {
        date = date.minusDays(1);
      } else {
        date = date.minusDays(2);
      }
      this.output.println("Calculating Value on Friday before the weekend on " + date.toString());
    }
    return date.toString();
  }
}
