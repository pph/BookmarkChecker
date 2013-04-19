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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import net.pphh.jabc.Controller;
import net.pphh.jabc.impl.HTMLParser;
import org.apache.log4j.Logger;


public class LoadActionEvent implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent t) {
        logger.debug("handle()");
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter filter = 
            new FileChooser.ExtensionFilter("HTML Bookmark files", "*.htm", "*.html");
        chooser.getExtensionFilters().add(filter);
        chooser.setTitle("Open HTML Bookmark file");
        logger.debug("showing FileChooser");
        File file = chooser.showOpenDialog(null);
        if (file != null) {
            Controller.getInstance().process(new HTMLParser(), file);
        }
    }
    
    static Logger logger = Logger.getLogger(LoadActionEvent.class);
}
