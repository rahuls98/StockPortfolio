package controllers;


import java.io.File;
import java.util.HashMap;

public interface PortfolioGUIController {
  void exitProgram();
  void goToCreatePortfolio();
  void goToHome();

  void getFile(File file);
  void goToDisplayPortfoliosComp();
  void displayPortfolioComposition(String pName, String date);

  void goToDisplayValue();
  void displayPortfolioValue(String pName, String date);

  void createNewPortfolio(String pName);
  void goToCreatePortfolioManually();
  void goToCreateOrder();

  void createOrder(String portfolio, String date, String action, float c,
                   HashMap<String, String> stocks);

  void goToInvestmentPlanScreen();
  void goToInvestByPercentageScreen(String portfolioName);

  void goToPortfolioPerformance();
}
