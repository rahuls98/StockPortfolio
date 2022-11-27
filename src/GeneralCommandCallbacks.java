import Controller.Controller;
import models.*;

/**
 * This example shows how to specify the (Key,Runnable) keyboard map using shorter syntax
 * using the ability of java 8 to support lambda expressions.
 */
public class GeneralCommandCallbacks
{
  public static void main(String []args){
    PortfolioModel model = null;
    try {
      model = new PortfolioModelImpl("Test");
    } catch (Exception e) {
      System.out.println("Storage invalid/corrupted!");
      return;
    }
    Controller controller = new Controller(model);
  }
}
