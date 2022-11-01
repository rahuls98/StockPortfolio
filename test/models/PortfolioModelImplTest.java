package models;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class PortfolioModelImplTest {

  private PortfolioModel model;

  @Before
  public void setUp() throws IOException {
    model = new PortfolioModelImpl("default");
  }

  @Test
  public void testIsValidDate() {
    //TODO: Test more extensively
    assertTrue(this.model.isValidDate("2022-10-31"));
    assertFalse(this.model.isValidDate("2022-10/31"));
    assertFalse(this.model.isValidDate("2019-02-30"));
    assertFalse(this.model.isValidDate("2022/10/31"));
    assertFalse(this.model.isValidDate("2022g10/31"));
    assertFalse(this.model.isValidDate("fhda"));
    assertFalse(this.model.isValidDate("2022-10-324"));
    assertFalse(this.model.isValidDate("202235-10-31"));
  }

}