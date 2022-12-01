package controllers;

import java.io.File;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import models.DollarCostAveraging;
import models.FixedAmountStrategy;
import models.PortfolioModel;
import models.PortfolioOperation;
import views.CreateManualPortfolio;
import views.CreateOrderScreen;
import views.CreatePortfolioScreen;
import views.DisplayComposition;
import views.DisplayDialogMessage;
import views.DisplayTable;
import views.DisplayValue;
import views.GetCostBasisScreen;
import views.HomeScreen;
import views.IView;
import views.InvestByPercentage;
import views.InvestSip;
import views.InvestmentPlanScreen;
import views.PerformanceChart;
import views.PortfolioPerformanceScreen;

/**
 * Represents a controller class that manages a portfolio GUI application. It takes user inputs,
 * performs operations based on those inputs, and provides outputs.
 */
public class PortfolioGUIControllerImpl implements PortfolioGUIController {
  private PortfolioModel model;
  private IView view;

  public PortfolioGUIControllerImpl(PortfolioModel m) {
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
    if (!model.isValidDate(date)) {
      view.displayDialog(false, "Invalid date!");
      this.goToHome();
      return;
    }
    HashMap<String, Float> hMap = this.model.getStockQuantitiesInPortfolio(pName, date);
    String[] columnNames = {"Stock", "Quantity"};


    Object[][] data = new Object[hMap.size()][2];
    int i = 0;
    Object[] row;
    for (Map.Entry<String, Float> comp : hMap.entrySet()) {
      row = new Object[]{comp.getKey(), String.format("%.2f", comp.getValue())};
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
    if (!model.isValidDate(date)) {
      view.displayDialog(false, "Invalid date!");
      this.goToHome();
      return;
    }
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
    String[] portfolios = model.getFlexiblePortfolios();
    if (portfolios.length == 0) {
      view.displayDialog(false, "You have no portfolios currently");
      this.goToHome();
      return;
    }
    view.disappear();
    this.setView(new CreateOrderScreen("Create Order", portfolios));
  }

  @Override
  public void createOrder(String portfolio, String date, String action, String c, HashMap<String,
          String> stocks) {

    if (!(this.model.isValidDate(date))) {
      new DisplayDialogMessage(false, "Invalid Date!");
      return;
    }
    HashMap<String, Float> stockMap = new HashMap<>();
    for (Map.Entry<String, String> stock : stocks.entrySet()) {
      try {
        Integer.parseInt(stock.getValue());
      } catch (IllegalArgumentException e) {
        new DisplayDialogMessage(false, "Invalid Quantity");
        return;
      }
      if (!(this.model.isValidTicker(stock.getKey()))) {
        new DisplayDialogMessage(false, "Invalid Stock");
        return;
      }
      stockMap.put(stock.getKey(), Float.parseFloat(stock.getValue()));
    }

    try {
      Float.parseFloat(c);
    } catch (Exception e) {
      new DisplayDialogMessage(false, "Invalid commission");
      return;
    }

    if (!(model.addOrderToPortfolioFromController(portfolio, date, action, Float.parseFloat(c),
            stockMap))) {
      new DisplayDialogMessage(false, "Invalid Order, cannot be processed.");
      this.goToHome();
    } else {
      this.model.persist();
      new DisplayDialogMessage(true, "Order created successfully!");
      this.goToHome();
    }
  }

  @Override
  public void goToInvestmentPlanScreen() {
    String[] portfolios = model.getFlexiblePortfolios();
    if (portfolios.length == 0) {
      view.displayDialog(false, "You have no portfolios currently");
      this.goToHome();
      return;
    }
    view.disappear();
    this.setView(new InvestmentPlanScreen(portfolios));
  }

  @Override
  public void goToInvestByPercentageScreen(String portfolioName) {
    //TODO:Check what date to be used for displaying composition
    if (portfolioName == null) {
      new DisplayDialogMessage(false, "Select a portfolio!");
      this.goToHome();
    }
    HashMap<String, Float> stocks = model.getStockQuantitiesInPortfolio(portfolioName,
            LocalDate.now().toString());
    String[] stockNames = new String[stocks.size()];
    int i = 0;
    for (Map.Entry<String, Float> stock : stocks.entrySet()) {
      stockNames[i] = stock.getKey();
      i++;
    }
    view.disappear();
    this.setView(new InvestByPercentage(portfolioName, stockNames));
  }

  @Override
  public void goToPortfolioPerformance() {
    String[] portfolios = model.getPortfolios();
    if (portfolios.length == 0) {
      view.displayDialog(false, "You have no portfolios currently");
      this.goToHome();
      return;
    }
    view.disappear();
    this.setView(new PortfolioPerformanceScreen(portfolios));
  }

  @Override
  public void investByPercentage(String portfolioName,
                                 String amount,
                                 String date,
                                 HashMap<String, String> stocks,
                                 String commission) {
    if (portfolioName == null) {
      new DisplayDialogMessage(false, "Select a portfolio!");
      this.goToHome();
    }
    float amount1;
    try {
      amount1 = Float.parseFloat(amount);
    } catch (Exception e) {
      new DisplayDialogMessage(false, "Invalid amount.");
      return;
    }
    LocalDate date1;
    // TODO : Check for weekends
    if (!this.model.isValidDate(date)) {
      new DisplayDialogMessage(false, "Invalid date.");
      return;
    }
    date1 = LocalDate.parse(date);
    HashMap<String, Float> stocks1 = new HashMap<>();
    for (Map.Entry<String, String> stock : stocks.entrySet()) {
      if (stock.getValue().equals("0")) {
        continue;
      }
      try {
        Float.parseFloat(stock.getValue());
      } catch (IllegalArgumentException e) {
        new DisplayDialogMessage(false, "Invalid Quantity");
        return;
      }
      if (!(this.model.isValidTicker(stock.getKey()))) {
        new DisplayDialogMessage(false, "Invalid Stock");
        return;
      }
      stocks1.put(stock.getKey(), Float.parseFloat(stock.getValue()));
    }
    float commission1;
    try {
      commission1 = Float.parseFloat(commission);
    } catch (Exception e) {
      new DisplayDialogMessage(false, "Invalid commission.");
      return;
    }
    PortfolioOperation<Void> fas;
    try {
      fas = new FixedAmountStrategy<>(portfolioName, amount1, date1, stocks1,
              commission1);
    } catch (IllegalArgumentException e) {
      new DisplayDialogMessage(false, e.getMessage());
      return;
    }
    fas.operate(this.model);
    this.model.persist();
    new DisplayDialogMessage(true, "Order created successfully!");
    this.goToHome();
  }

  @Override
  public void investBySIP(String portfolioName,
                          String amount,
                          String startDate,
                          String endDate,
                          String interval,
                          HashMap<String, String> stocks,
                          String commission) {
    if (portfolioName == null) {
      new DisplayDialogMessage(false, "Select a portfolio!");
      this.goToHome();
    }
    float amount1;
    try {
      amount1 = Float.parseFloat(amount);
    } catch (Exception e) {
      new DisplayDialogMessage(false, "Invalid amount.");
      return;
    }
    LocalDate startDate1;
    if (!this.model.isValidDate(startDate)) {
      new DisplayDialogMessage(false, "Invalid start date.");
      return;
    }
    startDate1 = LocalDate.parse(startDate);
    LocalDate endDate1;
    if (!this.model.isValidDate(endDate)) {
      new DisplayDialogMessage(false, "Invalid end date.");
      return;
    }
    endDate1 = LocalDate.parse(endDate);
    int interval1;
    try {
      interval1 = Integer.parseInt(interval);
    } catch (Exception e) {
      new DisplayDialogMessage(false, "Invalid interval.");
      return;
    }
    HashMap<String, Float> stocks1 = new HashMap<>();
    for (Map.Entry<String, String> stock : stocks.entrySet()) {
      if (stock.getValue().equals("0")) {
        continue;
      }
      try {
        Float.parseFloat(stock.getValue());
      } catch (IllegalArgumentException e) {
        new DisplayDialogMessage(false, "Invalid Quantity");
        return;
      }
      if (!(this.model.isValidTicker(stock.getKey()))) {
        new DisplayDialogMessage(false, "Invalid Stock");
        return;
      }
      stocks1.put(stock.getKey(), Float.parseFloat(stock.getValue()));
    }
    float commission1;
    try {
      commission1 = Float.parseFloat(commission);
    } catch (Exception e) {
      new DisplayDialogMessage(false, "Invalid commission.");
      return;
    }
    PortfolioOperation<Void> dca;
    try {
      dca = new DollarCostAveraging<>(portfolioName, amount1, startDate1,
              endDate1, interval1, stocks1, commission1);
    } catch (IllegalArgumentException e) {
      new DisplayDialogMessage(false, e.getMessage());
      return;
    }
    dca.operate(this.model);
    this.model.persist();
    new DisplayDialogMessage(true, "Order created successfully!");
    this.goToHome();
  }

  @Override
  public void goToInvestSipScreen(String portfolioName) {
    if (portfolioName == null) {
      new DisplayDialogMessage(false, "Select a portfolio!");
      this.goToHome();
    }
    HashMap<String, Float> stocks = model.getStockQuantitiesInPortfolio(portfolioName,
            LocalDate.now().toString());
    String[] stockNames = new String[stocks.size()];
    int i = 0;
    for (Map.Entry<String, Float> stock : stocks.entrySet()) {
      stockNames[i] = stock.getKey();
      i++;
    }
    view.disappear();
    this.setView(new InvestSip(portfolioName, stockNames));
  }

  @Override
  public void goToCostBasis() {
    String[] portfolios = model.getFlexiblePortfolios();
    if (portfolios.length == 0) {
      view.displayDialog(false, "You have no portfolios currently");
      this.goToHome();
      return;
    }
    view.disappear();
    this.setView(new GetCostBasisScreen(portfolios));
  }

  @Override
  public void getCostBasis(String portfolioName, String date) {
    if (portfolioName == null) {
      new DisplayDialogMessage(false, "Select a portfolio!");
      return;
    }
    if (!(this.model.isValidDate(date))) {
      new DisplayDialogMessage(false, "Invalid Date!");
      return;
    }
    new DisplayDialogMessage(true,
            "Cost basis of " + portfolioName + " as of "
                    + date + " is $" + model.getCostBasis(portfolioName, date));
  }

  @Override
  public void displayPortfolioPerformance(String portfolioName, String startDate, String endDate) {
    if (portfolioName == null) {
      new DisplayDialogMessage(false, "Select a portfolio!");
      this.goToHome();
    }
    if (!this.model.isValidDate(startDate)) {
      new DisplayDialogMessage(false, "Invalid start date.");
      return;
    }
    LocalDate startDate1 = LocalDate.parse(startDate);
    LocalDate endDate1;
    if (!this.model.isValidDate(endDate)) {
      new DisplayDialogMessage(false, "Invalid end date.");
      return;
    }
    endDate1 = LocalDate.parse(endDate);
    TreeMap<String, Float> performance = this.model.getPerformanceValues(portfolioName, startDate,
            endDate);
    view.disappear();
    this.setView(new PerformanceChart(performance));
  }
}