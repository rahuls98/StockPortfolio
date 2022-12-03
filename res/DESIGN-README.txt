This project uses the MVC design pattern, where the responsibilities are broken up as:

CHANGES:
Upon seeing this new assignment, we considered creating a new portfolio interface that extends the old interface with new functionalities. We would have then created a new class
to implement this new interface, leaving us with 2 classes for portfolio.
Instead of juggling between these 2 implementations, we decided it would be more efficient to have 1 portfolio interface & class that supports not only flexible & inflexible portfolios
but also all future versions of portfolio.
To support this, we introduced a new feature called 'order book' that contains a list of orders(containing stock transactions). This enables us to perform lazy execution efficiently.

CHANGES (Assignment 6):
We now have GUI support for the application. The old text based interface is still supported.
We created a new model to support long term investment operations using the visitor design pattern.
We have created an interface with a single operation method that is implemented by the new functionality classes that the application now supports(Dollar Cost Averaging, etc.)
This enables us to support any number of future investment operations on the portfolio without
editing the existing portfolio model.
We also enabled Buying & Selling of partial stocks.


1. Model - This is where all the computation and functionality resides. The PortfolioModel is the main model of our implementation and is the only public class in the model folder.
           All the other classes are package private. The controller calls the multiple methods of this class, that in turn call the methods in the other model classes.
           It calls methods from several package private classes which are as follows:
           1. Stock -          The smallest Entity that stores details of a single stock and can perform operations
                               such as getting its price on a particular date.

           2. Portfolio -      A portfolio has a name and a collection of orders performed in that portfolio.
                               It has a Hashmap of Stock names to Stock objects to cache API data and avoid multiple calls.
                               Further, it supports all the new methods of this assignment such as get cost basis, get composition on date,
                               get portfolio value on date, etc.
                               It can be of 2 types, either flexible or inflexible.

           3. User -           A User has unique usernames and has multiple portfolios of different types.
                               Our storage design supports multiple users and is ready for when the application requires the same.

           4. APIModel -       This Interface is used to deal with any API calls to get data. Currently,
                               it's class has methods to get stock data and valid ticker symbols.

           5. FileModel -      This interface handles all file related operations and can have implementations based
                               on different file types as the need emerges. Currently, it is used to implement an XML file handler.

           6. StorageModel -   This interface handles all operations involving storage mechanisms like Databases, file storages, etc. Currently, it has an implementation
                               which defines operations on an XML file storage.

           7. PortfolioModel - This is the main model of our implementation and is the only public class in the model folder. All the other classes are package private.
                               The controller calls the multiple methods of this class, that in turn call the methods in the other model classes.

          8. Order   -         A Class representing order. It contains the date, action(BUY or SELL), commission & finally stocks.

          9. Action     -      An enum that contains the order action, i.e, BUY or SELL.

          10. PortfolioType -  An enum that represents the types of portfolios supported by the application,i.e, INFLEXIBLE or FLEXIBLE.

          11. PortfolioOperation Interface & Implementations - Supports any current or future long term investing strategies.

2. View - View is responsible for displaying outputs to the user. Currently, it implements a text based user interface.

3. Controller - It is implemented as an asynchronous controller and is responsible for knowing the state, getting user input, calling the appropriate
                functions on the model and finally calling view methods to display result.

4. GUI Controller Interface & Class - This controller now supports the GUI.



