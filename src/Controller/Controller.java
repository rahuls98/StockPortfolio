package Controller;

import java.io.File;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import View.*;
import models.*;
import models.PortfolioModel;

public class Controller implements Features {
  private PortfolioModel model;
  private IView view;

  public Controller(PortfolioModel m) {
    model = m;
    this.setView(new HomeScreen("Stock Application"));
  }

  private void setView(IView v) {
    view = v;
    //provide view with all the callbacks
    view.addFeatures(this);
  }

  @Override
  public void exitProgram() {
    System.exit(0);
  }

  @Override
  public void goToCreatePortfolio() {
    view.disappear();
    this.setView(new CreatePortfolioScreen());
  }

  @Override
  public void goToHome() {
    view.disappear();
    this.setView(new HomeScreen("Stock Application"));
  }

  @Override
  public void getFile(File file) {
    String portfolioName;
    try {
      portfolioName = this.model.loadPortfolioNameFromXML(file.toString());
    } catch (Exception e) {
      view.displayDialog(false, "Invalid XML");
      this.goToHome();
      return;
    }
    if (Arrays.stream(this.model.getPortfolios()).anyMatch(portfolioName::equals)) {
      view.displayDialog(false, "Portfolio Name already Exists");
      this.goToHome();
      return;
    }
    try {
      this.model.loadPortfolioFromXml(file.toString());
    } catch (Exception e) {
      view.displayDialog(false, "Invalid XML");
      this.goToHome();
      return;
    }
    this.model.persist();
    view.displayDialog(true, "File loaded successfully!!");
    this.goToHome();
  }

  @Override
  public void goToDisplayPortfoliosComp() {
    String[] portfolios = model.getPortfolios();
    if (portfolios.length == 0) {
      view.displayDialog(false, "You have no portfolios currently");
      this.goToHome();
      return;
    }
    view.disappear();
    this.setView(new DisplayComposition(portfolios));
  }

  @Override
  public void displayPortfolioComposition(String pName, String date) {
    if (pName == null) {
      return;
    }
    //TODO: Handle date validation
    HashMap<String, Integer> hMap = this.model.getStockQuantitiesInPortfolio(pName, date);
    String[] columnNames = {"Stock", "Quantity"};


    Object[][] data = new Object[hMap.size()][2];
    int i = 0;
    Object[] row;
    for (Map.Entry<String, Integer> comp : hMap.entrySet()) {
      row = new Object[]{comp.getKey(), comp.getValue()};
      data[i] = row;
      i++;
    }
    new DisplayTable(columnNames, data);
  }

  @Override
  public void goToDisplayValue() {
    String[] portfolios = model.getPortfolios();
    if (portfolios.length == 0) {
      view.displayDialog(false, "You have no portfolios currently");
      this.goToHome();
      return;
    }
    view.disappear();
    this.setView(new DisplayValue(portfolios));
  }

  @Override
  public void displayPortfolioValue(String pName, String date) {
    if (pName == null) {
      return;
    }
    //TODO: Handle date validation
    HashMap<String, Float> hMap = this.model.getPortfolioValues(pName, date);
    String[] columnNames = {"Stock", "Value"};


    Object[][] data = new Object[hMap.size()][2];
    int i = 0;
    Object[] row;
    for (Map.Entry<String, Float> comp : hMap.entrySet()) {
      row = new Object[]{comp.getKey(), comp.getValue()};
      data[i] = row;
      i++;
    }
//    this.view.displayTable(columnNames, data);
    new DisplayTable(columnNames, data);
  }

  @Override
  public void createNewPortfolio(String pName) {
    if (Arrays.stream(this.model.getPortfolios()).anyMatch(pName::equals)) {
      new DisplayDialogMessage(false, "Name exists in portfolio, please try again");
      return;
    }
    if (pName.length() == 0) {
      return;
    }
    this.model.addFlexiblePortfolio(pName);
    this.model.persist();
    new DisplayDialogMessage(true, "Portfolio created successfully, Please create orders for it.");
    this.goToHome();
  }

  @Override
  public void goToCreatePortfolioManually() {
    view.disappear();
    this.setView(new CreateManualPortfolio());
  }

  @Override
  public void goToCreateOrder() {
    String[] portfolios = model.getPortfolios();
    if (portfolios.length == 0) {
      view.displayDialog(false, "You have no portfolios currently");
      this.goToHome();
      return;
    }
    view.disappear();
    this.setView(new CreateOrderScreen("Create Order", portfolios));
  }

  @Override
  public void createOrder(String portfolio, String date, String action, float c, HashMap<String, Integer> stocks) {
    //TODO: Perform validation on  Stocks

    if (!(this.model.isValidDate(date))) {
      new DisplayDialogMessage(false, "Invalid Date!");
      return;
    }

    if (!(model.addOrderToPortfolioFromController(portfolio, date, action, c, stocks))) {
      new DisplayDialogMessage(false, "Invalid Order, cannot be processed.");
      this.goToHome();
    } else {
      this.model.persist();
      new DisplayDialogMessage(true, "Order created successfully!");
      this.goToHome();
    }
  }
}
