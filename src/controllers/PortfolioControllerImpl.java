package controllers;

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
}
