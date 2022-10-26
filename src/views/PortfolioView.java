package views;

import java.util.HashMap;

/**
 * Description of interface.
 */
public interface PortfolioView {

  void displayActions();

  void displayPortfolioComposition(String name, HashMap<String, Integer> portfolio);

}
