ASSIGNMENT 6
---------------
Both text based interface and GUI are supported by the application.
1. To run the application using the text based interface : Select option 1
2. To run the application using the GUI : Select option 2

Sample dates for Portfolio Performance(Charts)
Select PortfolioName from drop down
Start Date: 2022-11-25
End Date: 2022-11-30
Click Display

OLD
-----
1) Run the .jar file, this will also create the dependent local storage XML file in the same
directory. Follow the given instructions hereon.
(java -jar .\StockPortfolio.jar)

2) Portfolios can be created manually or by providing an XML file (format predefined and provided in
 the res folder) with the portfolio details. Enter 1 on the main menu to create a portfolio.

    2a) To create the portfolio manually, follow the instructions on the screen. You will be
    prompted to enter the name of the portfolio. As of now, spaces in the name are not supported.
    Please enter a character sequence with no spaces. For flexible portfolios, you would have an additional
    step of choosing if it is a BUY or SELL order.
    You will be asked to enter the number of stocks you want to add to this portfolio. The ticker symbol of each stock should be entered in
    uppercase letters. Invalid tickers will be pointed out (Allowed ticker - https://raw.githubusercontent.com/rreichel3/US-Stock-Symbols/main/all/all_tickers.txt).
    Stock quantities must be positive integer values.

    Example flow: (As per assignment)
    You should also include detailed instructions on how to run your program to create a portfolio,
    purchase stocks of at least 3 different companies in that portfolio at different dates and then query the value and cost basis of that portfolio on two specific dates

    Please enter the menu item number when requested.

    What would you like to do?
    1. Create Portfolio
    2. Create Order
    3. Get portfolio composition
    4. Get portfolio value
    5. Get Cost Basis
    6. Get Performance
    7. Exit
    Select action: 1

    How would you like to enter the portfolio details?
    1. Enter manually
    2. Load from file
    3. Go back
    Select action: 1

    What type of portfolio would you like to create?
    1. Flexible
    2. Inflexible
    3. Go back
    Select action: 1

    Enter portfolio name: flex_portfolio

    A flexible portfolio can have multiple BUY/SELL orders, each pertaining to a set of stock transactions.
    How many orders would you like to create? 3

    ORDER 1
    What type of order would you like to create?
    1. BUY order
    2. SELL order
    Select action: 1
    Enter date for the order in YYYY-MM-DD format: 2022-11-04
    Enter commission for this transaction: 3.25
    Enter number of BUY transactions: 1
    Stock 1 ticker: GOOG
    Quantity : 1
    Order Recorded

    ORDER 2
    What type of order would you like to create?
    1. BUY order
    2. SELL order
    Select action: 1
    Enter date for the order in YYYY-MM-DD format: 2022-11-07
    Enter commission for this transaction: 5.67
    Enter number of BUY transactions: 2
    Stock 1 ticker: META
    Quantity : 1
    Stock 2 ticker: AAPL
    Quantity : 1
    Order Recorded

    ORDER 3
    What type of order would you like to create?
    1. BUY order
    2. SELL order
    Select action: 2
    Enter date for the order in YYYY-MM-DD format: 2022-11-14
    Enter commission for this transaction: 3.21
    Enter number of SELL transactions: 1
    Stock 1 ticker: GOOG
    Quantity : 1
    Order Recorded

    New portfolio (flex_portfolio) has been recorded!

    What would you like to do?
    1. Create Portfolio
    2. Create Order
    3. Get portfolio composition
    4. Get portfolio value
    5. Get Cost Basis
    6. Get Performance
    7. Exit
    Select action: 5

    Which portfolio would you like to use?
    1. p1
    2. flex_portfolio
    Select portfolio: 2
    Enter the date to calculate the cost basis for: 2022-11-15

    Cost basis of flex_portfolio as of 2022-11-15 is $334.47

    What would you like to do?
    1. Create Portfolio
    2. Create Order
    3. Get portfolio composition
    4. Get portfolio value
    5. Get Cost Basis
    6. Get Performance
    7. Exit
    Select action: 4

    Which portfolio would you like to use?
    1. p1
    2. flex_portfolio
    Select portfolio: 2
    Enter the date for which you want the value: 2022-11-15

    Portfolio: flex_portfolio
    ---------------------------
    Stock       |  Value
    ---------------------------
    META        |  $117.08
    AAPL        |  $150.04
    ---------------------------
    Total value of portfolio on is $267.1200


    2b) To create a portfolio using XML.
        Please note, our application supports 2 XML formats - the inflexible one we used for our previous assignment
        and a new XML for the flexible portfolios.

    Inflexible portfolio format:

    inflexible.xml
    <portfolio title="Portfolio_2">
        <stocks>
            <stock symbol="V" quantity="20" />
            <stock symbol="AAPL" quantity="10" />
        </stocks>
    </portfolio>


    Flexible Portfolio format:

    flexible.xml
    <portfolio title="flexible_test_1" type="flexible">
    	<orders>
    		<order action="BUY" commission="1.0" date="2022-10-31">
    			<stocks>
    				<stock quantity="10" symbol="AAPL"/>
    			</stocks>
    		</order>
    		<order action="SELL" commission="1.0" date="2022-11-01">
    			<stocks>
    				<stock quantity="9" symbol="AAPL"/>
    			</stocks>
    		</order>
    	</orders>
    </portfolio>


    Example flow:
    Please enter the menu item number when requested.

    What would you like to do?
    1. Create Portfolio
    2. Create Order
    3. Get portfolio composition
    4. Get portfolio value
    5. Get Cost Basis
    6. Get Performance
    7. Exit
    Select action: 1

    How would you like to enter the portfolio details?
    1. Enter manually
    2. Load from file
    3. Go back
    Select action: 2

    Enter path to XML: flexible.xml

    New portfolio (flexible_test_1) has been recorded!

3) To view the composition of the portfolios, enter 3 on the main menu. Follow the prompts.
To display the composition of a flexible portfolio, the application would ask for date on which
to display the portfolio. No such date is asked for inflexible portfolios.

4) To view the total value of any portfolio, enter 4 on the main menu. You will be asked to select
a portfolio for which you want the value for, along with a date. Date format is YYYY-MM-DD.
Currently, the supported date range is [2011-03-02, Yesterday's date] (Both inclusive). Current and
future dates are not handled. We are handling weekends by getting value for the previous friday.
However, we are not handling public holidays and would be added in subsequent iterations.

5) To view the cost basis of the portfolio, Enter 5 and follow the prompts.
You will not be able to view the cost basis of inflexible portfolios. You will be
able to select from any of your flexible portfolios. You will then be prompted to enter a date
in range [2011-03-02, Yesterday's date] to view the cost basis.

6) to get performance of a portfolio, enter 6 and follow the prompts. You will be asked to select
from the list of your portfolios. Then you would be asked to enter lower and upper limit dates that would
be considered in our algorithm. It will then generate a bar chart of at least 5 dates and rows of
at most 50 stars.
