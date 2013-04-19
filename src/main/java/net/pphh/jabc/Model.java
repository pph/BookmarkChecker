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

import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.log4j.Logger;


public class Model {
    private Model() {
        observableList = FXCollections.observableArrayList();
    }
    
    public ObservableList<Bookmark> getObservableList() {
        return observableList;
    }
    
    /**
     * Clears the backed internal List
     */
    public void emptyList() {
        getObservableList().clear();
        empty.set(true);
    }
    
    public void addAll(List<Bookmark> bookmarks) {
        getObservableList().addAll(bookmarks);
        empty.set(false);
        logger.debug("added bookmarks: " + observableList.size());
    }
    
    public void remove(Bookmark bookmark) {
        getObservableList().remove(bookmark);
        if (getObservableList().isEmpty()) {
            empty.set(true);
        }
    }
        
    private static class SingletonHolder {
        public static final Model INSTANCE = new Model();
    }
    
    public static Model getInstance() {
        return SingletonHolder.INSTANCE;
    }
    
    public BooleanProperty emptyProperty() {
        return empty;
    }
    
    private ObservableList<Bookmark> observableList;
    private BooleanProperty empty = new SimpleBooleanProperty(true);
    static Logger logger = Logger.getLogger(Model.class);
}