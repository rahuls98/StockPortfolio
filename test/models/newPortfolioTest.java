package models;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class newPortfolioTest {
  private PortfolioInstanceModel portfolio;

  @Before
  public void initTest() {
    ArrayList<Order> init = new ArrayList<>();
    HashMap<String, Integer> stocks = new HashMap<>();
    stocks.put("GOOG", 1);
    init.add(new Order(Action.BUY, LocalDate.of(2020, 1, 8), stocks));
    portfolio = new newPortfolio("test1", init);
  }

  @Test
  public void testBuy() {

  }

}