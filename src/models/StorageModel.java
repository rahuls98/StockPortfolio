package models;

/**
 * Description of interface.
 */
interface StorageModel {

  /**
   * Description of method.
   *
   * @return desc.
   */
  User readUser(String userName);

  /**
   * Description of method.
   *
   * @param user desc.
   */
  void writeUser(User user);
}
