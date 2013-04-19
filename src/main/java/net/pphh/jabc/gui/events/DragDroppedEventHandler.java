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
import net.pphh.jabc.Controller;
import net.pphh.jabc.Model;
import net.pphh.jabc.impl.HTMLParser;
import net.pphh.jabc.intf.BookmarkParser;
import org.apache.log4j.Logger;


public class DragDroppedEventHandler implements EventHandler<DragEvent> {
    @Override
    public void handle(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        Model model = Model.getInstance();
        model.emptyList();
        for (File file : dragboard.getFiles()) {
            logger.debug("Parsing " + file.getName());
            BookmarkParser parser = new HTMLParser();
            Controller.getInstance().process(parser, file);
        }
        event.setDropCompleted(true);
        event.consume();
    }
    
    static Logger logger = Logger.getLogger(DragDroppedEventHandler.class);
}
