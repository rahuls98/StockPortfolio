package models;

/**
 * Represents classes that perform file related operations on specific file types.
 */
interface FileModel {

  /**
   * Reads contents from a file.
   *
   * @param pathToFile Path to the file to read from.
   */
  void readFile(String pathToFile);

  /**
   * Writes content to a file.
   *
   * @param pathToFile Path to the file to write to.
   */
  void writeFile(String pathToFile);
}
