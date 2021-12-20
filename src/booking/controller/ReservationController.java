package booking.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;

import booking.dao.MemberDAO;
import booking.dto.MemberDTO;
import booking.dto.UserMember;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class ReservationController implements Initializable {

	@FXML private VBox passengerList;
	@FXML private TextFlow peopleText;
	@FXML private Button nextBtn;
	@FXML private BorderPane reservationBorder;
	
	@FXML private TextField firstNameTF;
	@FXML private TextField nameNameTF;
	@FXML private TextField emailFrontTF;
	@FXML private ComboBox emailAddressCB;
	@FXML private ComboBox firstPhoneCB;
	@FXML private TextField secondPhoneTF;
	@FXML private TextField thirdPhoneTF;
	
	private HBox hBox;
	private Label numLabel;
	private TextField ageTF;
	private TextField nameTF;
	private TextField engFirstNameTF;
	private TextField engNameTF;
	private TextField birthTF;
	private ComboBox genderComboBox;
	
	private MemberDAO dao;
	private MemberDTO dto;
	
	private static String userPhone;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		passengerCreate(0);
		
		FileInputStream fis = null;
		InputStreamReader is = null;
		BufferedReader br = null;
		
		try {
			fis = new FileInputStream("C:\\pmProject\\myJAVA\\booking\\src\\booking\\textFile\\people.txt");
			is = new InputStreamReader(fis);
			br = new BufferedReader(is);
			
			String str = null;
			
			while((str = br.readLine()) != null) {
				peopleText.getChildren().add(new Text(str));
				peopleText.getChildren().add(new Text(System.lineSeparator()));
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
	private void nextBtnAddAction(ActionEvent event) {
		
		if(firstNameTF.getText().trim().equals("") || nameNameTF.getText().trim().equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("이름 미입력");
			alert.setHeaderText("이름을 입력하세요");
			alert.setContentText("이름을 입력하세요");
			alert.showAndWait();
			return;
		} else if(emailFrontTF.getText().trim().equals("") || emailAddressCB.getValue() == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("이메일 미입력");
			alert.setHeaderText("이메일을 입력하세요");
			alert.setContentText("이메일을 입력하세요");
			alert.showAndWait();
			return;
		} else if(firstPhoneCB.getValue() == null || secondPhoneTF.getText().trim().equals("") || thirdPhoneTF.getText().trim().equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("전화번호 미입력");
			alert.setHeaderText("전화번호를 입력하세요");
			alert.setContentText("전화번호를 입력하세요");
			alert.showAndWait();
			return;
		}
		
		String name = firstNameTF.getText().trim() + nameNameTF.getText().trim();
		String email = emailFrontTF.getText().trim() + "@" + emailAddressCB.getValue();
		String phone = firstPhoneCB.getValue() + "-" + secondPhoneTF.getText().trim() + "-" + thirdPhoneTF.getText().trim();
		
		dto = new MemberDTO(name, email, phone);
		
		dao = new MemberDAO();
		dao.reservation(dto);
		
		userPhone = phone;
		
		FxmlLoader object = new FxmlLoader();
		Pane view = object.getPage("seat3");
		reservationBorder.setCenter(view);
	}
	
	private void passengerCreate(int people) {
		
		for(int i=1; i<UserMember.getAdult() + UserMember.getChildren(); i++) {
			hBox = new HBox();
			
			numLabel = new Label(String.valueOf(i+1));
				numLabel.setStyle("-fx-background-color: #eeeeee; -fx-min-width: 50; -fx-min-height: 30; -fx-alignment: CENTER;"
						+ "-fx-font-family: consolas; -fx-font-size: 15;");
			hBox.getChildren().add(numLabel);
			
			ageTF = new TextField();
				ageTF.setStyle("-fx-max-width: 100; -fx-max-height: 30; -fx-alignment: CENTER; -fx-font-family: consolas; -fx-font-size: 15;");
			hBox.getChildren().add(ageTF);
			
			nameTF = new TextField();		
				nameTF.setStyle("-fx-pref-width: 150; -fx-max-height: 30; -fx-font-family: consolas; -fx-font-size: 15;");
				nameTF.setPromptText("예) 이정빈");
			hBox.getChildren().add(nameTF);
			
			engFirstNameTF = new TextField();		
				engFirstNameTF.setStyle("-fx-pref-width: 150; -fx-max-height: 30; -fx-font-family: consolas; -fx-font-size: 15;");
				engFirstNameTF.setPromptText("예) LEE");
			hBox.getChildren().add(engFirstNameTF);
			
			engNameTF = new TextField();		
				engNameTF.setStyle("-fx-pref-width: 150; -fx-max-height: 30; -fx-font-family: consolas; -fx-font-size: 15;");
				engNameTF.setPromptText("예) JUNGBEEN");
			hBox.getChildren().add(engNameTF);
			
			birthTF = new TextField();		
				birthTF.setStyle("-fx-pref-width: 150; -fx-max-height: 30; -fx-font-family: consolas; -fx-font-size: 15;");
				birthTF.setPromptText("예) 19930209");
			hBox.getChildren().add(birthTF);
			
			genderComboBox = new ComboBox();		
				genderComboBox.setStyle("-fx-pref-width: 100; -fx-max-height: 30; -fx-font-family: consolas; -fx-font-size: 15;");
				genderComboBox.getItems().add(new String("남성"));
				genderComboBox.getItems().add(new String("여성"));
			hBox.getChildren().add(genderComboBox);
			
			hBox.setStyle("-fx-padding: 10, 15, 10, 15;");
			hBox.setMargin(numLabel, new Insets(0, 20, 0, 0));
			hBox.setMargin(ageTF, new Insets(0, 20, 0, 0));
			hBox.setMargin(nameTF, new Insets(0, 20, 0, 0));
			hBox.setMargin(engFirstNameTF, new Insets(0, 20, 0, 0));
			hBox.setMargin(engNameTF, new Insets(0, 20, 0, 0));
			hBox.setMargin(birthTF, new Insets(0, 20, 0, 0));
			hBox.setMargin(genderComboBox, new Insets(0, 20, 0, 0));
			passengerList.getChildren().add(hBox);
			
		}
	}
	
	public static String getUserPhone() {
		return userPhone;
	}
}
