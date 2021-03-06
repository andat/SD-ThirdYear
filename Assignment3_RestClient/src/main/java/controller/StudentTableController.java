package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Student;
import consumer.StudentConsumer;
import consumerContracts.IStudentConsumer;
import model.request.LoginModel;
import model.request.StudentRequestModel;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentTableController implements Initializable{

    private IStudentConsumer studentConsumer;
    private LoginModel credentials;

    @FXML
    TableView<Student> studentTable;

    @FXML
    TableColumn<Student, String> usernameCol;

    @FXML
    TableColumn<Student, String> nameCol;

    @FXML
    TableColumn<Student, String> emailCol;

    @FXML
    TableColumn<Student, String> groupCol;

    @FXML
    TableColumn<Student, String> hobbyCol;

    @FXML
    ComboBox groupComboBox;

    @FXML
    TextField emailField;

    @FXML
    TextField nameField;

    @FXML
    TextField userField;

    @FXML
    TextField hobbyField;

    public StudentTableController(LoginModel credentials){
        this.studentConsumer = new StudentConsumer();
        this.credentials = credentials;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(credentials != null){
            ObservableList<String> groups = FXCollections.observableArrayList("30431", "30432", "30433", "30434");
            this.groupComboBox.setItems(groups);
            refreshTable();
        }
    }

    @FXML
    public void viewBtnClicked(){
        refreshTable();
    }

    @FXML
    public void editBtnClicked(){
        StudentRequestModel stud = getStudentFromFields();
        int id = studentTable.getSelectionModel().getSelectedItem().getId();
        if(stud != null) {
            studentConsumer.editStudent(stud, id, credentials);
            //System.out.println(id);
            refreshTable();
            clearFields();
        }
    }

    @FXML
    public void addBtnClicked(){
        StudentRequestModel s = getStudentFromFields();
        if(s != null) {
//            if(studentConsumer.addStudent(s, credentials) == 500){
//                AlertBox.display("An error occurred", "A laboratory with that number already exists.");
//            }
            String message = studentConsumer.addStudent(s, credentials);
            System.out.println(message);
            refreshTable();
            clearFields();
        }
    }

    private void clearFields(){
        nameField.clear();
        emailField.clear();
        userField.clear();
        hobbyField.clear();
    }

    private StudentRequestModel getStudentFromFields(){
        String name = nameField.getText();
        String email = emailField.getText();
        String username = userField.getText();
        String hobby = hobbyField.getText();
        String group = (String) groupComboBox.getValue();

        if(username.isEmpty() || group == null || email.isEmpty())
            AlertBox.display("Empty fields", "Some fields that are required are empty.");
        else if(!email.matches("^[A-Za-z0-9._-]+@[a-z0-9.]+\\.[a-z]{2,6}$"))
            AlertBox.display("Wrong email format", "Please check that the email is correctly written.");
        else
            return new StudentRequestModel(username, name, email, group, hobby, false);

        return null;
    }

    @FXML
    public void rowSelected() {
        Student selected = studentTable.getSelectionModel().getSelectedItem();

        if (selected != null) {
            String name = selected.getFullName();
            String group = selected.getGroupName();
            String username = selected.getUsername();
            String email = selected.getEmail();
            String hobby = selected.getHobby();

            this.nameField.setText(name);
            this.groupComboBox.setValue(group);
            this.userField.setText(username);
            this.emailField.setText(email);
            this.hobbyField.setText(hobby);
        }
    }

    @FXML
    public void deleteBtnClicked(){
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if(selectedStudent != null){
            studentConsumer.deleteStudent(selectedStudent.getId(), credentials);
            clearFields();
        }
        refreshTable();
    }

    private void refreshTable(){
        studentTable.setItems(FXCollections.observableArrayList(studentConsumer.getAllStudents(credentials)));
    }

    public void setCredentials(LoginModel credentials){
        this.credentials = credentials;
    }
}
