1) Run the .jar file, this will also create the dependent local storage XML file in the same
directory. Follow the given instructions hereon.

2) Portfolios can be created manually or by providing an XML file (format predefined and provided in
 the res folder) with the portfolio details. Enter 1 on the main menu to create a portfolio.

    2a) To create the portfolio manually, follow the instructions on the screen. You will be
    prompted to enter the name of the portfolio. As of now, spaces in the name are not supported.
    Please enter a character sequence with no spaces. You will also be asked to enter the number of
    stocks you want to add to this portfolio. The ticker symbol of each stock should be entered in
    uppercase letters. Invalid tickers will be pointed out (Allowed ticker - https://raw.githubusercontent.com/rreichel3/US-Stock-Symbols/main/all/all_tickers.txt).
    Stock quantities must be positive integer values.

    Example flow:
    Please enter the menu item number when requested.

    What would you like to do?
    1. Create portfolio
    2. Get portfolio composition
    3. Get portfolio value
    4. Exit
    Select action: 1

    How would you like to enter the portfolio details?
    1. Enter manually
    2. Load from file
    3. Go back
    Select action: 1

    Enter portfolio name: Portfolio_1
    Enter number of stocks: 3
    Stock 1 ticker: AAPL
    Quantity : 40
    Stock 2 ticker: GOOG
    Quantity : 63
    Stock 3 ticker: V
    Quantity : 52

    New portfolio (Portfolio_1) has been recorded!


    2b) To create a portfolio using the provided XML file, populate with following with values:
        - "title" in the portfolio node
        - "quantity" in the stock node
        - "symbol" in the stock node
        Add/Remove stock nodes as required.

    portfolioSample.xml
    <portfolio title="Portfolio_2">
        <stocks>
            <stock symbol="V" quantity="20" />
            <stock symbol="AAPL" quantity="10" />
        </stocks>
    </portfolio>

    Example flow:
    What would you like to do?
    1. Create portfolio
    2. Get portfolio composition
    3. Get portfolio value
    4. Exit
    Select action: 1

    How would you like to enter the portfolio details?
    1. Enter manually
    2. Load from file
    3. Go back
    Select action: 2

    Enter path to XML: portfolioSample.xml

    New portfolio (Portfolio_2) has been recorded!

3) To view the composition of the portfolios, enter 2 on the main menu. Follow the prompts.

4) To view the total value of any portfolio, enter 3 on the main menu. You will be asked to select
a portfolio for which you want the value for, along with a date. Date format is YYYY-MM-DD.
Currently, the supported date range is [2011-03-02, Yesterday's date] (Both inclusive). Current and
future dates are not handled.
