package views;

/**
 * Description of class.
 */
public class PortfolioViewImpl implements PortfolioView {

  @Override
  public void displayActions() {
    String[] actions = new String[]{"Create portfolio", "Get portfolio composition",
            "Get portfolio value", "Exit"};
    for (int i = 0; i < actions.length; i++) {
      System.out.println((i + 1) + ". " + actions[i]);
    }
  }
}
