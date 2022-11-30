package models;

public interface PortfolioOperation <T> {
  T operate(PortfolioModel model);
}
