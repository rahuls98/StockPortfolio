This project uses the MVC design pattern, where the responsibilities are broken up as:

1. Model - This is where all the computation and functionality resides. The PortfolioModel is the main model of our implementation and is the only public class in the model folder.
           All the other classes are package private. The controller calls the multiple methods of this class, that in turn call the methods in the other model classes.
           It calls methods from several package private classes which are as follows:
           1. Stock - The smallest Entity that stores details of a single stock and can perform operations
                      such as getting its price on a particular date.
           2. Portfolio - A portfolio has a name and a collection of stocks & their respective quantities.
                          It can perform operations like returning all its constituent stocks, its total
                          value on a particular date, etc.
           3. User - A User has unique usernames and has multiple portfolios.
                     Our storage design supports multiple users and is ready for when the application requires the same.
           4. APIModel - This Interface is used to deal with any API calls to get data. Currently,
                         it's class has methods to get stock data and valid ticker symbols.
           5. FileModel - This interface handles all file related operations and can have implementations based
                          on different file types as the need emerges. Currently, it is used to implement an XML file handler.
           6. StorageModel - This interface handles all operations involving storage mechanisms like Databases, file storages, etc. Currently, it has an implementation
                              which defines operations on an XML file storage.
           7. PortfolioModel - This is the main model of our implementation and is the only public class in the model folder. All the other classes are package private.
                               The controller calls the multiple methods of this class, that in turn call the methods in the other model classes.

2. View - View is responsible for displaying outputs to the user. Currently, it implements a text based user interface.

3. Controller - It is implemented as an asynchronous controller and is responsible for knowing the state, getting user input, calling the appropriate
                functions on the model and finally calling view methods to display results.


