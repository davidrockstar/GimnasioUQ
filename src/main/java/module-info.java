module gimnasiouq.gimnasiouq {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.desktop;

    opens gimnasiouq.gimnasiouq.viewcontroller to javafx.fxml;
    opens gimnasiouq.gimnasiouq to javafx.fxml;

    // Exportar paquetes para que los tests puedan acceder
    exports gimnasiouq.gimnasiouq;
    exports gimnasiouq.gimnasiouq.viewcontroller;
    exports gimnasiouq.gimnasiouq.model;
    exports gimnasiouq.gimnasiouq.factory;
    exports gimnasiouq.gimnasiouq.util;
    exports gimnasiouq.gimnasiouq.controller; // <-- exportar controllers para permitir uso/reflexión desde otras capas

    // Abrir paquetes para reflexión (necesario para Mockito y tests)
    opens gimnasiouq.gimnasiouq.model to javafx.base;
}