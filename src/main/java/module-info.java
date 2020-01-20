module at.htl {
    requires javafx.controls;
    requires javafx.fxml;

    opens imageplayer to javafx.fxml;
    exports imageplayer;
}