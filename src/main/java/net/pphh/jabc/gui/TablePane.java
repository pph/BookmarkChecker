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

package net.pphh.jabc.gui;

import java.net.URL;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import net.pphh.jabc.Bookmark;
import net.pphh.jabc.Controller;
import net.pphh.jabc.Model;
import net.pphh.jabc.Status;
import net.pphh.jabc.gui.events.DragDroppedEventHandler;
import net.pphh.jabc.gui.events.DragOverEventHandler;


public class TablePane extends AnchorPane {
    public TablePane() {
        Controller controller = Controller.getInstance();
        tableview = controller.getTableView();
        tablecontextmenu = new TableContextMenu();
        TableColumn<Bookmark, Status> statusCol = new TableColumn<>(controller.getString("tablecolumn.status"));
        TableColumn<Bookmark, URL> urlCol = new TableColumn<>(controller.getString("tablecolumn.url"));
        TableColumn<Bookmark, Integer> codeCol = new TableColumn<>(controller.getString("tablecolumn.code"));
        TableColumn<Bookmark, String> descCol = new TableColumn<>(controller.getString("tablecolumn.desc"));
        
        tableview.getColumns().addAll(statusCol, urlCol, codeCol, descCol);
        tableview.setTableMenuButtonVisible(true);
        tableview.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableview.setContextMenu(tablecontextmenu);
        tableview.setPlaceholder(new Label(controller.getString("table.placeholder")));
        tableview.setItems(Model.getInstance().getObservableList());
        
        tableview.setOnDragOver(new DragOverEventHandler());
        //TODO: implement a D&D DragEntered Handler for visual feedback
        tableview.setOnDragDropped(new DragDroppedEventHandler());
        
        statusCol.setCellValueFactory(new PropertyValueFactory<Bookmark, Status>("status"));
        urlCol.setCellValueFactory(new PropertyValueFactory<Bookmark, URL>("url"));
        codeCol.setCellValueFactory(new PropertyValueFactory<Bookmark, Integer>("code"));
        descCol.setCellValueFactory(new PropertyValueFactory<Bookmark, String>("description"));
        
        urlCol.setMinWidth(200.0);
        descCol.setMinWidth(90.0);
        descCol.setSortable(false);
        
        urlCol.setCellFactory(new Callback<TableColumn<Bookmark, URL>, TableCell<Bookmark, URL>>() {
            @Override
            public TableCell<Bookmark, URL> call(TableColumn<Bookmark, URL> p) {
                TableCell<Bookmark,URL> cell = new TableCell<Bookmark,URL>(){
                    @Override
                    public void updateItem(URL item, boolean empty) {
                        if (item != null) {
                            setText(item.toString());
                        }
                    }
                };
                return cell;
            }
        });
        
        statusCol.setCellFactory(new Callback<TableColumn<Bookmark, Status>, TableCell<Bookmark, Status>>() {
            @Override
            public TableCell<Bookmark, Status> call(TableColumn<Bookmark, Status> p) {
                TableCell<Bookmark,Status> cell = new TableCell<Bookmark,Status>() {
                    @Override
                    public void updateItem(Status item, boolean empty) {
                        if (item != null) {
                            HBox hbox = new HBox();
                            ImageView imageview = new ImageView();
                            imageview.setFitHeight(16.0);
                            imageview.setFitWidth(16.0);
                            switch (item) {
                                case NEW:
                                    imageview.setImage(new Image("images/Grey.png"));
                                    break;
                                case WARN:
                                    imageview.setImage(new Image("images/Yellow.png"));
                                    break;
                                case ERROR:
                                    imageview.setImage(new Image("images/Red.png"));
                                    break;
                                case OK:
                                    imageview.setImage(new Image("images/Green.png"));
                                    break;
                            }
                            hbox.getChildren().add(imageview);
                            setGraphic(hbox);
                        }
                    }
                };
                return cell;
            }
        });
        
        codeCol.setVisible(false);
        
        AnchorPane.setTopAnchor(tableview, 0.0);
        AnchorPane.setLeftAnchor(tableview, 0.0);
        AnchorPane.setRightAnchor(tableview, 0.0);
        AnchorPane.setBottomAnchor(tableview, 0.0);
        
        getChildren().add(tableview);
    }
        
    private TableView<Bookmark> tableview;
    private TableContextMenu tablecontextmenu;
}
