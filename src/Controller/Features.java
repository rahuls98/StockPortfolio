package Controller;

import java.io.File;
import java.util.HashMap;

public interface Features {
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
//  void investByPercentage(String portfolioName, )

  void goToPortfolioPerformance();

  void investByPercentage(String portfolioName,
                          String amount,
                          String date,
                          HashMap<String, String> stocks,
                          String commission);

  void investBySIP(String portfolioName,
                          String amount,
                          String startDate,
                          String endDate,
                          String interval,
                          HashMap<String, String> stocks,
                          String commission);

  void goToInvestSipScreen(String portfolioName);
}
