package booking.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class TermsController implements Initializable {
	
	@FXML private Button nextBtn;
	@FXML private TextFlow termsText;
	@FXML private BorderPane termsBorder;
	@FXML private RadioButton agreeBtn;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		FileInputStream fis = null;
		InputStreamReader is = null;
		BufferedReader br = null;
		
		try {
			fis = new FileInputStream("C:\\pmProject\\myJAVA\\booking\\src\\booking\\textFile\\terms.txt");
			is = new InputStreamReader(fis);
			br = new BufferedReader(is);
			
			String str = null;
			
			while((str = br.readLine()) != null) {
				termsText.getChildren().add(new Text(str));
				termsText.getChildren().add(new Text(System.lineSeparator()));
			}
			
			
		} catch(FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if(br != null) {
					br.close();
				}
			} catch(IOException e) {}
			
			try {
				if(is != null) {
					is.close();
				}
			} catch(IOException e) {}
			
			try {
				if(fis != null) {
					fis.close();
				}
			} catch(IOException e) {}
		}
	}
	
	@FXML
	private void nextBtnAction(ActionEvent event) {
		if(agreeBtn.isSelected() == false) {
			return;
		}
		
		FxmlLoader object = new FxmlLoader();
		Pane view = object.getPage("reservation");
		termsBorder.setCenter(view);
	}

}
