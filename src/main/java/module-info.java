module gimnasiouq.gimnasiouq {
    requires javafx.controls;
    requires javafx.fxml;


    opens gimnasiouq.gimnasiouq to javafx.fxml;
    exports gimnasiouq.gimnasiouq;
}