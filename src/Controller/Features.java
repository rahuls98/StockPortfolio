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
                   HashMap<String, Integer> stocks);
}
