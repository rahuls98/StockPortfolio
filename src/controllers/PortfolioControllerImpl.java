package controllers;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

import entities.Portfolio;
import entities.Stock;
import entities.User;
import models.PortfolioModel;
import views.PortfolioView;

/**
 * Dethis.inputription of class.
 */
public class PortfolioControllerImpl implements PortfolioController {

  private final PortfolioModel model;
  private final PortfolioView view;
  private final User user;
  private final Scanner input;

  /**
   * Dethis.inputription of constructor.
   *
   * @param model dethis.input.
   * @param view  dethis.input.
   * @param user  dethis.input.
   */
  public PortfolioControllerImpl(PortfolioModel model, PortfolioView view, User user,
                                 InputStream input) {
    this.model = model;
    this.view = view;
    this.user = user;
    this.input = new Scanner(input);
  }

  @Override
  public void go() {
    while (true) {
      view.displayActions();
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
    String portfolioName = this.input.nextLine();
    // TODO : validate name
    Portfolio portfolio = new Portfolio(portfolioName);
    System.out.print("Enter number of stocks: ");
    int n = this.input.nextInt();
    String stockName;
    int stockQuantity;
    Stock stock;
    for (int i = 0; i < n; i++) {
      System.out.print("Stock " + (i + 1) + " ticker: ");
      stockName = this.input.next();
      System.out.print("Quantity : ");
      stockQuantity = this.input.nextInt();
      stock = new Stock(stockName);
      portfolio.addStock(stock, stockQuantity);
    }
    user.addPortfolio(portfolio);
    model.updatePortfolio(user);
    System.out.println("\nNew portfolio (" + portfolioName + ") has been recorded!");
  }

  private void getComposition() {
    String[] portfolios = model.getPortfolios();
    view.displayPortfolios(portfolios);
    int choice = this.input.nextInt();
    String portfolioName = portfolios[choice - 1];
    view.displayPortfolioComposition(portfolioName, model.getPortfolio(portfolioName));
  }

  private void getPortfolioValue() {
    String[] portfolios = model.getPortfolios();
    view.displayPortfolios(portfolios);
    int choice = this.input.nextInt();
    String portfolioName = portfolios[choice - 1];
    System.out.print("Enter the date for which you want the value: ");
    String date = this.input.next();
    view.displayPortfolioValue(portfolioName, model.getPortfolioValues(portfolioName, date));
    System.out.println("Total value of portfolio on is " + String.format("%.2f",
            model.getPortfolioTotal(portfolioName, date)));
  }
}
