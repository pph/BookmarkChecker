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

package net.pphh.jabc.gui.events;

import java.util.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import net.pphh.jabc.Bookmark;
import net.pphh.jabc.Controller;
import net.pphh.jabc.Model;
import org.apache.log4j.Logger;


public class RemoveBookmarkEvent implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent t) {
        TableView<Bookmark> tableview = Controller.getInstance().getTableView();
        logger.debug("size: " + tableview.getSelectionModel().getSelectedItems().size());
        if (tableview.getSelectionModel().getSelectedItems().size() < 1) {
            tableview.getContextMenu().getItems().get(0).setDisable(true);
        } else {
            ObservableList<Bookmark> l = FXCollections.observableArrayList();
            l.addAll(tableview.getSelectionModel().getSelectedItems());
            Iterator<Bookmark> i = l.iterator();
            while (i.hasNext()) {
                Bookmark bookmark = i.next();
                Model.getInstance().remove(bookmark);
                logger.debug("removed: " + bookmark.getUrl());
            }
        }
    }
    static Logger logger = Logger.getLogger(RemoveBookmarkEvent.class);
}
