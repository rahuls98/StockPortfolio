package models;

import java.util.HashMap;

/**
 * Description of interface.
 */
public interface StorageModel {

  /**
   * Description of method.
   *
   * @return Hashmap.
   */
  HashMap<String, HashMap<String, String[]>> read();

  /**
   * Description of method.
   *
   * @param data Hashmap.
   */
  void write(HashMap<String, HashMap<String, String[]>> data);
}
