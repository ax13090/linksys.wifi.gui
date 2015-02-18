package fr.axel.linksys.wifi.gui;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LinksysWifiGuiApplication extends Application {

	final LinksysWebClient linkSysClient;
	final VBox vBox = new VBox();

	private LinksysWifiGuiApplication() {
		try {
			linkSysClient = LinksysWebClient.create();
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(final String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage stage) throws Exception {
		
		final Button refreshButton = new Button("Refresh");
		final StackPane root = new StackPane();
		root.getChildren().add(refreshButton);
		root.getChildren().add(vBox);
		
		final EventHandler<ActionEvent> refreshButtonAction = new RefreshButtonAction();
		refreshButton.setOnAction(refreshButtonAction);
		
		stage.setScene(new Scene(root));
		stage.sizeToScene();
		stage.setTitle("Linksys Wifi GUI");
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
			
			vBox.getChildren().clear();
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
				vBox.getChildren().add(choiceButton);
			}
		}
	}
}
