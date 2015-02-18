package fr.axel;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class LinksysWifiGuiApplication extends Application {


	public static void main(final String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage stage) throws Exception {

		final Button btn = new Button(">> Click <<");
		btn.setOnAction(e -> System.out.println("Hello JavaFX 8"));
		final StackPane root = new StackPane();
		root.getChildren().add(btn);
		stage.setScene(new Scene(root));
		stage.setWidth(300);
		stage.setHeight(300);
		stage.setTitle("JavaFX 8 app");
		stage.show();
	}

}
