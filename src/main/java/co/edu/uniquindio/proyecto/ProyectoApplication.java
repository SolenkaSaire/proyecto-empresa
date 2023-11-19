package co.edu.uniquindio.proyecto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane; // Importa StackPane en lugar de BorderPane
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ProyectoApplication extends Application {
    private ConfigurableApplicationContext springContext;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void init() throws Exception {
        springContext = SpringApplication.run(ProyectoApplication.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Código para iniciar tu aplicación JavaFX
      //  FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/VistaLogin.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/VistaLogin.fxml"));
        loader.setControllerFactory(springContext::getBean);
        StackPane rootLayout = loader.load();
        Scene scene = new Scene(rootLayout);
      //  stage.setMinHeight(700);
      //  stage.setMinWidth(800);
        stage.setScene(scene);
        stage.setTitle("Tourism Management System");
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        springContext.close();
    }
}
