/*******************************************************************************
* This file is part of BookmarkChecker.
*
* BookmarkChecker is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* BookmarkChecker is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License along with
* BookmarkChecker. If not, see <http://www.gnu.org/licenses/gpl-3.0.html>.
******************************************************************************/

package net.pphh.jabc;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import net.pphh.jabc.gui.TablePane;
import net.pphh.jabc.gui.events.CheckURLEvent;
import net.pphh.jabc.gui.events.ExportHTMLEventHandler;
import net.pphh.jabc.gui.events.LoadActionEvent;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class BookmarkChecker extends Application {
    
    //TODO: add support for HTTPS bookmarks
    //TODO: implement browser specific exports (i.e. Firefox, Chrome, IE, etc.)
    //TODO: Internationalization: add more languages
    //TODO: testcases
    //TODO: documentation / help screens
    @Override
    public void start(Stage stage) {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("log4j.properties"));
            PropertyConfigurator.configure(prop);
        } catch (IOException ex) {
            System.err.printf(ex.getLocalizedMessage());
            //System.exit(1);
        }
        Controller controller = Controller.getInstance();
        logger.info("Starting Application ...");
        AnchorPane anchorpane = new TablePane();
        AnchorPane buttonanchorpane = new AnchorPane();
        HBox buttonbox = new HBox(5);
        HBox progressbox = new HBox(5);
        BorderPane border = new BorderPane();
        ProgressBar pb = controller.getProgressBar();
        Button btnLoad = controller.getButtonLoad();
        Button btnStart = controller.getButtonStart();
        Button btnStop = controller.getButtonStop();
        MenuBar mb = new MenuBar();
        Menu m_file = new Menu(controller.getString("menu.file"));
        Menu m_export = new Menu(controller.getString("menu.export"));
        MenuItem mi_open = controller.getMenuItemOpen();
        MenuItem mi_exit = new MenuItem(controller.getString("menuitem.exit"));
        MenuItem mi_export = new MenuItem(controller.getString("menuitem.export.html"));
        Tooltip ttopen = new Tooltip(controller.getString("tt.open"));
        Tooltip ttstop = new Tooltip(controller.getString("tt.stop"));
        Tooltip.install(btnLoad, ttopen);
        Tooltip.install(btnStop, ttstop);
        EventHandler<ActionEvent> loadactionevent = new LoadActionEvent();

        mi_exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                logger.info("Exiting application");
                Platform.exit();
            }
        });
        
        pb.setMinWidth(170.0);
        pb.setProgress(0);
        pb.setVisible(false);
        btnLoad.setOnAction(loadactionevent);
        btnStart.disableProperty().bind(Model.getInstance().emptyProperty());
        btnStart.setOnAction(new CheckURLEvent());
        mi_open.setOnAction(loadactionevent);
        mi_export.disableProperty().bind(Model.getInstance().emptyProperty());        
        mi_export.setOnAction(new ExportHTMLEventHandler());
        
        m_file.getItems().addAll(mi_open, new SeparatorMenuItem(), mi_exit);
        m_export.getItems().addAll(mi_export);
        mb.getMenus().addAll(m_file, m_export);
        
        progressbox.setAlignment(Pos.CENTER_RIGHT);
        progressbox.getChildren().add(pb);
        buttonbox.getChildren().addAll(btnLoad, btnStart, btnStop);
        buttonanchorpane.getChildren().addAll(buttonbox, progressbox);
        AnchorPane.setLeftAnchor(buttonbox, 0d);
        AnchorPane.setRightAnchor(progressbox, 0d);
        border.setTop(mb);
        border.setBottom(buttonanchorpane);
        border.setCenter(anchorpane);
        BorderPane.setMargin(anchorpane, new Insets(12));
        BorderPane.setMargin(buttonanchorpane, new Insets(12));
        
        Scene scene = new Scene(border, WIDTH, HEIGHT);
        
        stage.setTitle(APPNAME);
        stage.getIcons().add(new Image("images/logo.png"));
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    private final static int HEIGHT = 770;
    private final static int WIDTH = 1024;
    private final static String APPNAME = "JABC";
    
    static Logger logger = Logger.getLogger(BookmarkChecker.class);
}
