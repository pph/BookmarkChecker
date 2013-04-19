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

import java.io.File;
import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;


public class DragOverEventHandler implements EventHandler<DragEvent> {

    @Override
    public void handle(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        if (dragboard.hasFiles()) {
            boolean hasHTML = false;
            for (File f : dragboard.getFiles()) {
                if (f.getName().endsWith(".html") || f.getName().endsWith(".htm")) {
                    hasHTML = true;
                }
            }
            if (hasHTML) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
        }      
        event.consume();
    }
}
