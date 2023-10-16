package com.vmish.taskmanager;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;


public class JavaFxApplication extends Application {
    private ConfigurableApplicationContext context;


    @Override
    public void init() throws Exception {
        this.context = new SpringApplicationBuilder()
                .sources(TaskmanagerApplication.class).run(getParameters().getRaw().toArray(new String[0]));

        //TaskData.getInstance().createList();

    }

    @Override
    public void start(Stage stage) throws Exception {

        context.publishEvent(new StageReadyEvent(stage));
    }

    @Override
    public void stop() throws Exception {
        this.context.close();
        Platform.exit();
    }
}
