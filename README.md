#testjavafx
This testjavafx pom application included UI in JavaFX, back-end using BITSO Rest API for managing Real time Trading and is a jar packaging.

#This Application integrate the Bitso trading platform using following Notations.
1. Major denotes the cryptocurrency, in our case Bitcoin (BTC).
2. Minor denotes fiat currencies, in our case Mexican Peso (MXN)
An order book is always referred to in the API as â€œMajor_Minorâ€�. For example: â€œbtc_mxnâ€�

#Following are the Public REST API which are implemented in this task.
1. Order Book : https://api.bitso.com/v3/order_book/?book=btc_mxn
2. Trades : https://api.bitso.com/v3/trades/?book=btc_mxn
3. Ticker : https://api.bitso.com/v3/ticker/?book=btc_mxn

#Maven Goal for Building Application as.
mvn clean compile assembly:single

#Application description for building and Running.
Above mentioned maven goal will build the pom application as jar file in target folder. 
In target folder generated jar file as 'fxclient-jar-with-dependencies.jar'  is executable. you can just click and run it.

