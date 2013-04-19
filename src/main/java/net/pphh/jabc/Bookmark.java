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

import java.net.URL;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Bookmark {
    
    public Bookmark(URL url, String desc) {
        this.url = new SimpleObjectProperty(url);
        this.description = new SimpleStringProperty(desc);
        this.status = new SimpleObjectProperty(Status.NEW);
    }
    
    public Bookmark(URL url) {
        this.url = new SimpleObjectProperty(url);
        this.description = new SimpleStringProperty(Controller.getInstance().getString("bookmark.na"));
        this.status = new SimpleObjectProperty(Status.NEW);
    }
    
    public void setUrl(URL u) {
        url.set(u);
    }
    
    public Object getUrl() {
        return url.get();
    }
    
    public ObjectProperty urlProperty() {
        return url;
    }
    
    public void setCode(int c) {
        code.set(c);
    }
    
    public Integer getCode() {
        return code.get();
    }
    
    public IntegerProperty codeProperty() {
        return code;
    }
    
    public void setDescription(String desc) {
        description.set(desc);
    }
    
    public String getDescription() {
        return description.get();
    }
    
    public StringProperty descriptionProperty() {
        return description;
    }
    
    public void setStatus(Status stat) {
        status.set(stat);
    }
    
    public Object getStatus() {
        return status.get();
    }
    
    public ObjectProperty statusProperty() {
        return status;
    }
            
    private ObjectProperty status = new SimpleObjectProperty();
    private ObjectProperty url = new SimpleObjectProperty();
    private IntegerProperty code = new SimpleIntegerProperty();
    private StringProperty description = new SimpleStringProperty();
}
