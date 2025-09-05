module com.mycompany.amplify {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.amplify to javafx.fxml;
    exports com.mycompany.amplify;
}
