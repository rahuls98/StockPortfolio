package models;

import entities.User;

/**
 * Represents classes that interact with storage mechanisms, for example: file storages,
 * databases, etc., and defines required associated operations.
 */
public interface StorageModel {

  /**
   * Retrieves a user from storage by username.
   *
   * @return User object containing all the user's portfolios and stocks.
   */
  User readUser(String userName);

  /**
   * Inserts/Modifies an existing user in storage.
   *
   * @param user User to insert/update.
   */
  void writeUser(User user);
}
