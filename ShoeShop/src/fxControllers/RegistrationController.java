package fxControllers;

import hibernateControllers.UserHib;
import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Customer;
import model.Manager;
import model.User;

import java.io.IOException;

public class RegistrationController {

    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public PasswordField repeatPasswordField;
    @FXML
    public TextField nameField;
    @FXML
    public TextField surnameField;
    @FXML
    public RadioButton customerCheckbox;
    @FXML
    public ToggleGroup userType;
    @FXML
    public RadioButton managerCheckbox;
    @FXML
    public TextField addressField;
    @FXML
    public TextField cardNoField;
    @FXML
    public DatePicker birthDateField;
    @FXML
    public TextField employeeIdField;
    @FXML
    public TextField medCertificateField;
    @FXML
    public DatePicker employmentDateField;
    @FXML
    public CheckBox isAdminCheck;

    private EntityManagerFactory entityManagerFactory;
    private UserHib userHib;

    public void setData(EntityManagerFactory entityManagerFactory, boolean showManagerFields) {
        this.entityManagerFactory = entityManagerFactory;
        disableFields(showManagerFields);
    }

    private void disableFields(boolean showManagerFields) {
        if (showManagerFields) {
            addressField.setDisable(false);
            cardNoField.setDisable(false);
            employeeIdField.setDisable(true);
            medCertificateField.setDisable(true);
            employmentDateField.setDisable(true);
            isAdminCheck.setDisable(true);
        }
    }

    @FXML
    private void toggleFields() {
        if (!customerCheckbox.isSelected()) {
            addressField.setDisable(false);
            cardNoField.setDisable(false);
            employeeIdField.setDisable(false);
            medCertificateField.setDisable(false);
            employmentDateField.setDisable(false);
            isAdminCheck.setDisable(false);
        } else {
            addressField.setDisable(false);
            cardNoField.setDisable(false);
            employeeIdField.setDisable(true);
            medCertificateField.setDisable(true);
            employmentDateField.setDisable(true);
            isAdminCheck.setDisable(true);
        }
    }


    public void createUser() {
        if (loginField.getText().isEmpty() || passwordField.getText().isEmpty() ||
                repeatPasswordField.getText().isEmpty() || nameField.getText().isEmpty() ||
                surnameField.getText().isEmpty() || birthDateField.getValue() == null ||
                cardNoField.getText().isEmpty() || addressField.getText().isEmpty() ||
                !passwordField.getText().equals(repeatPasswordField.getText())) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Mandatory Fields Missing");
            alert.setContentText("Please fill all the mandatory fields and ensure passwords match.");
            alert.showAndWait();
            return;
        }

        userHib = new UserHib(entityManagerFactory);

        if (customerCheckbox.isSelected()) {
            Customer user = new Customer(loginField.getText(), "", birthDateField.getValue(),
                    nameField.getText(), surnameField.getText(),
                    addressField.getText(), cardNoField.getText());
            user.setPassword(passwordField.getText());
            userHib.createUser(user);
            JavaFxCustomUtils.showAlert("Success", "User created successfully");
        } else if (managerCheckbox.isSelected()) {
            Manager user = new Manager(loginField.getText(), "", birthDateField.getValue(),
                    nameField.getText(), surnameField.getText(),
                    employeeIdField.getText(), medCertificateField.getText(),
                    employmentDateField.getValue(), isAdminCheck.isSelected());
            user.setPassword(passwordField.getText());
            userHib.createUser(user);
        } else {
            JavaFxCustomUtils.showAlert("Error", "User type not selected");
        }
    }

    public void returnToLogin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("../view/login.fxml"));
        Parent parent = fxmlLoader.load();
        LoginController loginController = fxmlLoader.getController();
        loginController.setData(entityManagerFactory);
        Scene scene = new Scene(parent);
        Stage stage = (Stage) loginField.getScene().getWindow();
        stage.setTitle("Shop");
        stage.setScene(scene);
        stage.show();
    }
}
