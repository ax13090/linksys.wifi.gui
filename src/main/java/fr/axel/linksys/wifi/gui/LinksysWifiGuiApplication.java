package fr.axel.linksys.wifi.gui;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class LinksysWifiGuiApplication extends Application {

	final LinksysWebClient linkSysClient;
	final HBox hBox = new HBox();

	public LinksysWifiGuiApplication() {
		try {
			linkSysClient = LinksysWebClient.create();
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(final String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(final Stage stage) throws Exception {
		
		final Button refreshButton = new Button("Refresh");
		final VBox root = new VBox();
		root.getChildren().add(refreshButton);
		root.getChildren().add(hBox);
		
		final EventHandler<ActionEvent> refreshButtonAction = new RefreshButtonAction();
		refreshButton.setOnAction(refreshButtonAction);
		
		final Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Linksys Wifi GUI");

		refreshButtonAction.handle(null);
		
		final Screen screen = Screen.getPrimary();
		final Rectangle2D bounds = screen.getVisualBounds();
		final double halvedScreenWidth = bounds.getWidth() / 2;
		final double halvedScreenHeight = bounds.getHeight() / 2;
		stage.setX(halvedScreenWidth / 2);
		stage.setY(halvedScreenHeight / 2);
		stage.setWidth(halvedScreenWidth);
		stage.setHeight(halvedScreenHeight);
		stage.show();
	}

	class RefreshButtonAction implements EventHandler<ActionEvent> {
		@Override
		public void handle(final ActionEvent actionEvent) {
			
			final Map<String, String> availableWifiModeChoices;
			try {
				availableWifiModeChoices = linkSysClient.fetchAvailableWifiModeChoices();
			} catch (final IOException e) {
				throw new RuntimeException(e);
			}
			
			hBox.getChildren().clear();
			for (final Entry<String, String> mode : availableWifiModeChoices.entrySet()) {
				final Button choiceButton = new Button();
				choiceButton.setText(mode.getKey());
				choiceButton.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(final ActionEvent event) {
						try {
							linkSysClient.switchWifiMode(mode.getValue());
						} catch (final IOException e) {
							throw new RuntimeException(e);
						}
						RefreshButtonAction.this.handle(null);
					}
				});
				hBox.getChildren().add(choiceButton);
			}
		}
	}
}
