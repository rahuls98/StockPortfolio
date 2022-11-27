package Controller;

import java.io.File;

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
}
