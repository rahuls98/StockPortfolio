package controllers;

import java.util.HashMap;
import java.util.Scanner;

import models.PortfolioModel;
import views.PortfolioView;

/**
 * Description of class.
 */
public class PortfolioControllerImpl implements PortfolioController {

  private final PortfolioModel model;
  private final PortfolioView view;

  /**
   * Description of constructor.
   *
   * @param model desc.
   * @param view  desc.
   */
  public PortfolioControllerImpl(PortfolioModel model, PortfolioView view) {
    this.model = model;
    this.view = view;
  }

  @Override
  public void go() {
    while (true) {
      view.displayActions();
      Scanner sc = new Scanner(System.in);
      int choice = sc.nextInt();
      switch (choice) {
        case 1:
          System.out.println("\nCreate portfolio\n");
          HashMap<String, HashMap<String, Integer>> map = readPortfolioStocks();
          this.model.updatePortfolio(map);
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
          System.out.println("Enter the date for which you want the value");
          String date = sc.next();
          HashMap<String, Float> portValues = model.getPortfolioValues(portfolioName, date);
          view.displayPortfolioValue(portfolioName, portValues);
          System.out.println("Total Price of Portfolio is " + String.format("%.4f", model.getPortfolioTotal(portValues)));
          break;
        case 4:
          return;
        default:
          System.out.println("\nInvalid choice\n");
          break;
      }
    }
  }


  private HashMap<String, HashMap<String, Integer>> readPortfolioStocks() {
    HashMap<String, HashMap<String, Integer>> map = new HashMap<>();
    HashMap<String, Integer> innerMap = new HashMap<>();
    Scanner sc = new Scanner(System.in);

    System.out.println("Enter Name of Portfolio");
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
    System.out.println("We have recorded your Entry to Portfolio: " + name);
    map.put(name, innerMap);
    return map;
  }
}
