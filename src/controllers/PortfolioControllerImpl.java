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
          System.out.println(map);
          break;
        case 2:
          System.out.println("\nGet portfolio composition\n");
          break;
        case 3:
          System.out.println("\nGet portfolio value\n");
          break;
        case 4:
          return;
        default:
          System.out.println("\nInvalid choice\n");
          break;
      }
    }
  }

  @Override
  public HashMap<String, HashMap<String, Integer>> readPortfolioStocks() {
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
    System.out.println("We have recorded your Entry to Portfolio: "+name);
    map.put(name, innerMap);
    return map;
  }
}
