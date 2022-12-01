package models;

/**
 * Represents classes that execute high-level, time-based long-term investment strategies.
 */
public interface PortfolioOperation<T> {

  /**
   * Method that executes the investment strategy based on the provided information.
   *
   * @param model Instance of the PortfolioModel.
   * @return Generic T.
   */
  T operate(PortfolioModel model);
}
