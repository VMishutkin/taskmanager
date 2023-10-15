package com.vmish.taskmanager;

import com.vmish.taskmanager.controller.MainController;
import com.vmish.taskmanager.model.TaskData;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;


public class JavaFxApplication extends Application {
    private ConfigurableApplicationContext context;


    @Override
    public void init() throws Exception {
        this.context = new SpringApplicationBuilder()
                .sources(TaskmanagerApplication.class)
                .run(getParameters().getRaw().toArray(new String[0]));

        //TaskData.getInstance().createList();

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        context.publishEvent(new StageReadyEvent(primaryStage));
    }

    @Override
    public void stop() throws Exception {

        Platform.exit();
    }
}
