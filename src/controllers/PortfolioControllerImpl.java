package controllers;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
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

  /**
   * Description of constructor.
   *
   * @param model desc.
   * @param view  desc.
   * @param user  desc.
   */
  public PortfolioControllerImpl(PortfolioModel model, PortfolioView view, User user) {
    this.model = model;
    this.view = view;
    this.user = user;
  }

  @Override
  public void go() {
    while (true) {
      view.displayActions();
      Scanner sc = new Scanner(System.in);
      int choice = sc.nextInt();
      switch (choice) {
        case 1:
          this.createPortfolio();
          break;
        case 2:
          String[] portfolios = model.getPortfolios();
          view.displayPortfolios(portfolios);
          choice = sc.nextInt();
          String portfolioName = portfolios[choice - 1];
          view.displayPortfolioComposition(portfolioName, model.getPortfolio(portfolioName));
          break;
        case 3:
          portfolios = model.getPortfolios();
          view.displayPortfolios(portfolios);
          choice = sc.nextInt();
          portfolioName = portfolios[choice - 1];
          String date = this.getDate();
          view.displayPortfolioValue(portfolioName, model.getPortfolioValues(portfolioName, date));
          System.out.println("Total Price of Portfolio is " + String.format("%.2f", model.getPortfolioTotal(portfolioName, date)));
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
    Scanner sc = new Scanner(System.in);
    System.out.print("\nEnter portfolio name: ");
    String portfolioName = sc.nextLine();
    // TODO : validate name
    Portfolio portfolio = new Portfolio(portfolioName);
    System.out.print("Enter number of stocks: ");
    int n = sc.nextInt();
    String stockName;
    int stockQuantity;
    Stock stock;
    for (int i = 0; i < n; i++) {
      System.out.print("Stock " + (i + 1) + " ticker: ");
      stockName = sc.next();
      System.out.print("Quantity : ");
      stockQuantity = sc.nextInt();
      stock = new Stock(stockName);
      portfolio.addStock(stock, stockQuantity);
    }
    user.addPortfolio(portfolio);
    model.updatePortfolio(user);
    System.out.println("\nNew portfolio (" + portfolioName + ") has been recorded!");
  }


  private HashMap<String, HashMap<String, Integer>> readPortfolioStocks() {
    HashMap<String, HashMap<String, Integer>> map = new HashMap<>();
    HashMap<String, Integer> innerMap = new HashMap<>();
    Scanner sc = new Scanner(System.in);

    System.out.println("Enter Name of entities.Portfolio");
    String name = sc.nextLine();
    System.out.println("Enter Stocks one by one followed by Quantity");
    System.out.println("Enter done to exit after done");
    while (true) {
      //Read line by line
      String line = sc.nextLine();
      if (line.equals("done")) {
        break;
      }
      String[] arr = line.split(" ");
      innerMap.put(arr[0], Integer.parseInt(arr[1]));
    }
    System.out.println("We have recorded your Entry to entities.Portfolio: " + name);
    map.put(name, innerMap);
    return map;
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
