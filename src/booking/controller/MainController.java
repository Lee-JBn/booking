package booking.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class MainController implements Initializable {
	
	@FXML private BorderPane mainBorder;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	@FXML
	private void noLoginBtnAction(ActionEvent event) {
		FxmlLoader object = new FxmlLoader();
		Pane view = object.getPage("userMain");
		mainBorder.setCenter(view);
	}
}

