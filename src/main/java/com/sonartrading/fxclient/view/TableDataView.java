package com.sonartrading.fxclient.view;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import com.sonartrading.fxclient.helper.BitsoHelper;
import com.sonartrading.fxclient.model.Order;
import com.sonartrading.fxclient.model.Trade;
import com.sonartrading.fxclient.model.TradeTicker;
import com.sonartrading.fxclient.timerservice.OrderBookTimerService;
import com.sonartrading.fxclient.timerservice.TradeTimerService;
import com.sonartrading.fxclient.util.BitsoConstant;

public class TableDataView extends Application {

	private static TableView<Order> bidTableDataView = new TableView<Order>();
	private static TableView<Order> askTableDataView = new TableView<Order>();
	private static TableView<Trade> tradeTableDataView = new TableView<Trade>();
	private static TableView<TradeTicker> imaginaryTradeTableDataView = new TableView<TradeTicker>();
	private static ObservableList<Order> bidsData;
	private static ObservableList<Order> asksData;
	private static ObservableList<Trade> tradeData;
	private static ObservableList<TradeTicker> imaginaryTradeData;
	
	static {
		try {
			TableViewDataUpdate.initFxCollectionsForOrderBook();
			TableViewDataUpdate.initFxCollectionsTrade();
			TableViewDataUpdate.initFxCollectionsTradeTicker();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	@Override
	public void start(Stage stage) {
		Scene scene = new Scene(new Group());
		stage.setTitle("Bitso Table View");
		stage.setMaximized(true);
		
		final Label bidLabel = new Label("Bids from Order Book");
		bidLabel.setFont(new Font("Arial", 20));

		bidTableDataView = getTableDataView(BitsoConstant.getTableColumnTitlesForOrderBook());
		//update TableData view for Order Book's Bids
		if (bidsData !=null && bidsData.size() > 1) {
			bidTableDataView.setItems(bidsData);
		}
		
		final VBox bidVbox = new VBox();
		bidVbox.setSpacing(5);
		bidVbox.setPadding(new Insets(10, 0, 0, 10));
		bidVbox.getChildren().addAll(bidLabel, bidTableDataView);
		
		final Label askLabel = new Label("Asks from Order Book");
		askLabel.setFont(new Font("Arial", 20));
		
		askTableDataView = getTableDataView(BitsoConstant.getTableColumnTitlesForOrderBook());
		//update TableData view for Order Book's Bids
		if (asksData !=null && asksData.size() > 1) {
			askTableDataView.setItems(asksData);
		}
		
		final VBox askVbox = new VBox();
		askVbox.setSpacing(5);
		askVbox.setPadding(new Insets(10, 0, 0, 10));
		askVbox.getChildren().addAll(askLabel, askTableDataView);
		
		final Label tradeLabel = new Label("Most Recent Trades From Bitso");
		tradeLabel.setFont(new Font("Arial", 20));
		
		tradeTableDataView = getTableDataView(BitsoConstant.getTableColumnTitlesForTrade());
		//update TableData view for Order Book's Bids
		if (tradeData !=null && tradeData.size() > 1) {
			tradeTableDataView.setItems(tradeData);
		}
		
		final VBox tradeVbox = new VBox();
		tradeVbox.setSpacing(5);
		tradeVbox.setPadding(new Insets(10, 0, 0, 10));
		tradeVbox.getChildren().addAll(tradeLabel, tradeTableDataView);
		
		final Label imaginaryTradeLabel = new Label("Imaginary Trades Calculated by Tickers");
		imaginaryTradeLabel.setFont(new Font("Arial", 20));
		
		final Label tickerDetails = new Label("Ticker Counts Details\n" + "\t Up: " + BitsoHelper.getInstance().getTicker().getCountUpTicks() + "\n\t Down: "
				+ BitsoHelper.getInstance().getTicker().getCountDownTicks() + "\n\t Zero: " + BitsoHelper.getInstance().getTicker().getCountZeroTicks());
		imaginaryTradeTableDataView = getTableDataView(BitsoConstant.getTableColumnTitlesForImaginaryTrade());
		//update TableData view for Order Book's Bids
		if (imaginaryTradeData !=null && imaginaryTradeData.size() > 1) {
			imaginaryTradeTableDataView.setItems(imaginaryTradeData);
		}
		
		final VBox imaginaryTradeVbox = new VBox();
		imaginaryTradeVbox.setSpacing(5);
		imaginaryTradeVbox.setPadding(new Insets(10, 0, 0, 10));
		imaginaryTradeVbox.getChildren().addAll(tickerDetails, imaginaryTradeLabel, imaginaryTradeTableDataView);
		
		TabPane tabPane = new TabPane();
		BorderPane mainPane = new BorderPane();
		List<Tab> tabs = new ArrayList<Tab>(0);
		tabs.add(new Tab());
		tabs.add(new Tab());
		tabs.add(new Tab());
		tabs.add(new Tab());
		
		// Add something in Tab
		tabs.get(0).setText("Order Book Best Bids");
		tabs.get(0).setContent(bidVbox);
		tabs.get(1).setText("Order Book Best Asks");
		tabs.get(1).setContent(askVbox);
		tabs.get(2).setText("Most Recent Trades");
		tabs.get(2).setContent(tradeVbox);
		tabs.get(3).setText("Imaginary Trades At Tickers");
		tabs.get(3).setContent(imaginaryTradeVbox);
		
		// tabs to disable close control
		tabs.forEach(tab -> {
			tab.setClosable(false);
		});
		tabPane.getTabs().addAll(tabs);
		
		mainPane.setCenter(tabPane);
		mainPane.prefHeightProperty().bind(scene.heightProperty());
		
		((Group) scene.getRoot()).getChildren().addAll(mainPane);
		
		stage.setScene(scene);
		stage.show();
		
		// update OrderBook details after every 5000 milliseconds
		OrderBookTimerService.getInstance().scheduleJobForOrderBook(0, 5000);
		// update Trade details after every 8000 milliseconds
		TradeTimerService.getInstance().scheduleJobForTrade(0, 8000);
	}
	
	@SuppressWarnings("unchecked")
	private static <T> TableView<T> getTableDataView(Map<String, String> mapTableColumns) {
		TableView<T> bidTableDataView = new TableView<T>();
		bidTableDataView.setEditable(true);

		mapTableColumns.entrySet().forEach( entrySet-> {
			TableColumn<T, String> tableColumn = new TableColumn<T,String>(entrySet.getValue());
			if ("createDateTime".equalsIgnoreCase(entrySet.getKey()) || "tradeRemarkOnTicker".equalsIgnoreCase(entrySet.getKey())){
				tableColumn.setMinWidth(180);
			} else {
				tableColumn.setMinWidth(130);
			}
			tableColumn.setCellValueFactory(new PropertyValueFactory<T, String>(entrySet.getKey()));
			bidTableDataView.getColumns().addAll(tableColumn);
		});
		
		return bidTableDataView;
	}
	
	public final static class TableViewDataUpdate{
		public static void updateTableViewForOrderBook() {
			// initialize table view data
			initFxCollectionsForOrderBook();
			//update TableData view for Order Book's Bids
			if (bidsData !=null && bidsData.size() > 1) {
				bidTableDataView.setItems(bidsData);
			}
			//update TableData view for Order Book's Asks
			if (asksData !=null && asksData.size() > 1) {
				askTableDataView.setItems(asksData);
			}
		}
		
		public static void updateTableViewForTrade() {
			// initialize table view data
			initFxCollectionsTrade();
			// update TableData view for Trades
			if (tradeData != null && tradeData.size() > 1) {
				tradeTableDataView.setItems(tradeData);
			}
		}
		
		private static void initFxCollectionsForOrderBook(){
			BitsoHelper.getInstance().sendRequestToBitsoToGetOrderBookData();
			bidsData = FXCollections.observableArrayList(BitsoHelper.getInstance().getOrderBook().getBids());
			asksData = FXCollections.observableArrayList(BitsoHelper.getInstance().getOrderBook().getAsks());
		}
		
		private static void initFxCollectionsTrade(){
			BitsoHelper.getInstance().sendRequestToBitsoToGetTradesData();
			tradeData = FXCollections.observableArrayList(BitsoHelper.getInstance().getTrades());
		}
		
		private static void initFxCollectionsTradeTicker(){
			BitsoHelper.getInstance().sendRequestToBitsoToGetTickerData();
			imaginaryTradeData = FXCollections.observableArrayList(BitsoHelper.getInstance().getTradeTickers());
		}
	}
}
