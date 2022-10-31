package controllers;

import java.io.OutputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.io.InputStream;
import java.util.Scanner;

import entities.Portfolio;
import entities.Stock;
import entities.User;
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

  /**
   * Description of constructor.
   *
   * @param model desc.
   * @param view  desc.
   * @param user  desc.
   */
  public PortfolioControllerImpl(PortfolioModel model, PortfolioView view, User user,
                                 InputStream input) {
    // TODO : handle nulls
    this.model = model;
    this.view = view;
    this.user = user;
    this.input = new Scanner(input);
  }

  @Override
  public void go() {
    while (true) {
      view.displayActions();
      System.out.print("Enter action: ");
      int choice = this.input.nextInt();
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
          System.out.println("\nInvalid choice\n");
          break;
      }
    }
  }

  private void createPortfolio() {
    System.out.print("\nEnter portfolio name: ");
    String portfolioName = this.input.next();
    Portfolio portfolio = new Portfolio(portfolioName);
    System.out.print("Enter number of stocks: ");
    int n = this.input.nextInt();
    while (n <= 0) {
      System.out.println("Enter a valid number of Stocks ");
      n = this.input.nextInt();
    }
    String stockName;
    int stockQuantity;
    Stock stock;
    for (int i = 0; i < n; i++) {
      System.out.print("Stock " + (i + 1) + " ticker: ");
      stockName = this.input.next();
      while (!(model.isValidTicker(stockName))) {
        System.out.println("Enter Valid Ticker Name: ");
        stockName = this.input.next();
      }
      System.out.print("Quantity : ");
      stockQuantity = this.input.nextInt();
      // TODO : Validate not 0, negative, fractional
      stock = new Stock(stockName);
      portfolio.addStock(stock, stockQuantity);
    }
    user.addPortfolio(portfolio);
    model.addPortfolio(user);
    System.out.println("\nNew portfolio (" + portfolioName + ") has been recorded!");
  }

  private void getComposition() {
    String[] portfolios = model.getPortfolios();
    String portfolioName = displayPortfoliosAndTakeUserInput(portfolios);
    view.displayPortfolioComposition(portfolioName, model.getPortfolio(portfolioName).getStockQuantities());
  }

  private void getPortfolioValue() {
    String[] portfolios = model.getPortfolios();
    if( portfolios.length == 0) {
      System.out.println("No portfolios to display, Try entering portfolios first");
      return;
    }
    String portfolioName = displayPortfoliosAndTakeUserInput(portfolios);
    String date = this.getDate();
    view.displayPortfolioValue(portfolioName, model.getPortfolioValues(portfolioName, date));
    System.out.println("Total value of portfolio on is " + String.format("%.4f",
            model.getPortfolioTotal(portfolioName, date)));
  }

  private String displayPortfoliosAndTakeUserInput(String[] portfolios) {
    view.displayPortfolios(portfolios);
    System.out.print("Select Portfolio: ");
    int choice = this.input.nextInt();
    while ((choice <= 0) || (choice > portfolios.length)) {
      System.out.println("Enter a valid number for Portfolio ");
      choice = this.input.nextInt();
    }
    String portfolioName = portfolios[choice - 1];
    return portfolioName;
  }

  private String getDate() {
    //TODO: Perform Validation on input date.
    System.out.println("Enter the date for which you want the value");
    Scanner sc = new Scanner(System.in);
    String strDate = sc.next();
    LocalDate date = LocalDate.parse(strDate);
    DayOfWeek day = DayOfWeek.of(date.get(ChronoField.DAY_OF_WEEK));
    if ((day == DayOfWeek.SUNDAY) || (day == DayOfWeek.SATURDAY)) {
      System.out.println("Entered day is a weekend");
      if (day == DayOfWeek.SATURDAY) {
        date = date.minusDays(1);
      } else {
        date = date.minusDays(2);
      }
      System.out.println("Calculating Value on Friday before the weekend on " + date.toString());
    }
    return date.toString();
  }
}
