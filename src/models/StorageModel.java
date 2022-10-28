package models;

import java.util.HashMap;

import entities.User;

/**
 * Description of interface.
 */
interface StorageModel {

  /**
   * Description of method.
   *
   * @return Hashmap.
   */
  User read(String userName);

  /**
   * Description of method.
   *
   * @param data Hashmap.
   */
  void write(User user);
}
