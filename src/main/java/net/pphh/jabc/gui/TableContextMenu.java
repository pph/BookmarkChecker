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

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import net.pphh.jabc.Controller;
import net.pphh.jabc.gui.events.RemoveBookmarkEvent;


public class TableContextMenu extends ContextMenu {
    public TableContextMenu() {
        super();
        mi_remove = new MenuItem(Controller.getInstance().getString("menu.remove"));
        mi_remove.setDisable(true);
        mi_remove.setOnAction(new RemoveBookmarkEvent());
        getItems().addAll(mi_remove);
    }
    
    MenuItem mi_remove;
}
