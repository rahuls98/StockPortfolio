package models;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.HashMap;

public class DollarCostAveraging<T> implements PortfolioOperation<T> {

  private final String portfolioName;
  private final int investmentAmount;
  private final LocalDate startDate;
  private final LocalDate endDate;
  private final int interval;
  private final HashMap<String, Float> stocks;
  private final float commission;

  public DollarCostAveraging(String portfolioName, int investmentAmount, LocalDate startDate,
                             LocalDate endDate, int interval, HashMap<String, Float> stocks,
                             float commission) {
    this.portfolioName = portfolioName;
    this.investmentAmount = investmentAmount;
    this.startDate = startDate;
    this.endDate = endDate;
    this.interval = interval;
    this.stocks = stocks;
    this.commission = commission;
  }

  @Override
  public T operate(PortfolioModel model) {
    try {
      LocalDate ld1 = this.startDate;
      LocalDate ld2 = this.endDate;
      int i;
      int j = 0;
      LocalDate date = null;
      PortfolioOperation<Void> fixedAmountStrategy = null;
      for (i = 0; i < this.interval; i++) {
        date = LocalDate.ofEpochDay(ld1.toEpochDay() + ((long) this.interval * i));
        if (date.isAfter(ld2)) {
          break;
        }
        j = 0;
        boolean flag = true;
        while (true) {
          try {
            model.getPortfolioValues(portfolioName, date.toString());
            break;
          } catch (NullPointerException e) {
            j++;
            date = LocalDate.ofEpochDay(ld1.toEpochDay() + j + ((long) this.interval * i));
            if (date.isAfter(ld2)) {
              flag = false;
              break;
            }
          }
        }
        if (flag) {
          fixedAmountStrategy = new FixedAmountStrategy<>(this.portfolioName,
                  this.investmentAmount, date, this.stocks, this.commission);
          fixedAmountStrategy.operate(model);
        } else {
          break;
        }
      }
      date = LocalDate.ofEpochDay(ld1.toEpochDay() + j + ((long) this.interval * i));
      if (!date.isAfter(ld2)) {
        fixedAmountStrategy = new FixedAmountStrategy<>(this.portfolioName,
                this.investmentAmount, date, this.stocks, this.commission);
        fixedAmountStrategy.operate(model);
      }
    } catch (DateTimeException e) {
      System.out.println(e.getMessage());
    }
    return null;
  }
}
