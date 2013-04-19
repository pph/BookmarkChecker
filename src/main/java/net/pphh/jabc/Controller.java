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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import net.pphh.jabc.gui.events.StopTaskEventHandler;
import net.pphh.jabc.intf.BookmarkParser;
import net.pphh.jabc.intf.BookmarkWriter;
import net.pphh.jabc.threads.CheckURLTask;
import org.apache.log4j.Logger;


public class Controller {

    private Controller() {
        messages = ResourceBundle.getBundle("Messages");
        buttonLoad.setText(getString("open"));
        buttonStart.setText(getString("start"));
        buttonStop.setText(getString("stop"));
        menuitemopen.setText(getString("menu.open"));
        
        buttonStart.setDisable(false);
        buttonStop.setDisable(true);
        
        buttonStop.setOnAction(stoptaskevent);
    }

    public void checkURLs(List<Bookmark> bookmarklist) {
        List<Bookmark> input = new ArrayList<>();
        input.addAll(bookmarklist);
        task = new CheckURLTask(input);
        progressbar.visibleProperty().bind(task.runningProperty());
        progressbar.progressProperty().bind(task.progressProperty());
        buttonStop.disableProperty().bind(task.runningProperty().not());
        buttonStart.disableProperty().bind(task.runningProperty());
        buttonLoad.disableProperty().bind(task.runningProperty());
        menuitemopen.disableProperty().bind(task.runningProperty());
        Thread thread = new Thread(task);
        logger.info("starting to check URLS");
        logger.debug("submitted threads for execution: " + bookmarklist.size());
        Model.getInstance().emptyList();
        thread.setDaemon(true);
        thread.start();
        getTableView().getColumns().get(2).setVisible(true);
        logger.info("checking done.");
    }

    public void process(BookmarkParser parser, File file) {
        logger.debug("process bookmarks");
        parser.open(file);
        parser.parse();
        Model.getInstance().addAll(parser.getBookmarks());
        parser.close();
        getTableView().getContextMenu().getItems().get(0).setDisable(false);
    }
    
    public void export(BookmarkWriter writer, File file) {
        logger.debug("writing bookmarks");
        writer.open(file);
        writer.write(Model.getInstance().getObservableList());
        writer.close();
    }

    private static class SingletonHolder {

        public static final Controller INSTANCE = new Controller();
    }

    public static Controller getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public TableView<Bookmark> getTableView() {
        return tableview;
    }

    public String getString(String key) {
        return messages.getString(key);
    }

    public ProgressBar getProgressBar() {
        return progressbar;
    }

    public Button getButtonLoad() {
        return buttonLoad;
    }

    public Button getButtonStart() {
        return buttonStart;
    }

    public Button getButtonStop() {
        return buttonStop;
    }
    
    public MenuItem getMenuItemOpen() {
        return menuitemopen;
    }
    
    public Task<List<Bookmark>> getTask() {
        return task;
    }
    
    private TableView<Bookmark> tableview = new TableView<>();
    private ProgressBar progressbar = new ProgressBar();
    private Button buttonLoad = new Button();
    private Button buttonStart = new Button();
    private Button buttonStop = new Button();
    private MenuItem menuitemopen = new MenuItem();
    private EventHandler<ActionEvent> stoptaskevent = new StopTaskEventHandler();
    private Task<List<Bookmark>> task;
    private ResourceBundle messages;
    static Logger logger = Logger.getLogger(Controller.class);
}
