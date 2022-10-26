package models;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class StorageModelLocalImplTest {

  private StorageModel localStorage;

  @Before
  public void setup() {
    try {
      localStorage = new StorageModelLocalImpl();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  @Test
  public void testLocalStorageInstanceOfStorageModel() {
    assertTrue(localStorage instanceof StorageModel);
  }

  @Test
  public void testLocalStorageCreation() {
    File localStorageFile = new File("./localStorage.xml");
    if (localStorageFile.delete()) {
      setup();
      assertTrue(localStorageFile.exists());
      assertEquals(0, localStorageFile.length());
    } else {
      fail();
    }
  }

  @Test
  public void testReadWithEmptyLocalStorage() {
    File localStorageFile = new File("./localStorage.xml");
    if (localStorageFile.delete()) {
      setup();
      assertNull(localStorage.read());
    } else {
      fail();
    }
  }

  @Test
  public void testRead() {
    System.out.println(localStorage.read());
  }

  @Test
  public void testWrite() {
    HashMap<String, HashMap<String, Integer>> data = new HashMap<>();
    HashMap<String, Integer> stock1 = new HashMap<>();
    stock1.put("C", 56);
    stock1.put("B", 36);
    stock1.put("A", 46);
    data.put("College savings 1", stock1);

    HashMap<String, Integer> stock2 = new HashMap<>();
    stock2.put("E", 100);
    stock2.put("F", 12);
    data.put("College savings 2", stock2);

    localStorage.write(data);
  }

}