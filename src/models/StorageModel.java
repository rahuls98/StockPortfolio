package models;

import java.util.HashMap;

/**
 * Description of interface.
 */
interface StorageModel {

  /**
   * Description of method.
   *
   * @return Hashmap.
   */
  HashMap<String, HashMap<String, Integer>> read();

  /**
   * Description of method.
   *
   * @param data Hashmap.
   */
  void write(HashMap<String, HashMap<String, Integer>> data);
}
