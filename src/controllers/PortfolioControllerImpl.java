package controllers;

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
    System.out.println("Controller go!");
  }
}
