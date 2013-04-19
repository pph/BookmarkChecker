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

package net.pphh.jabc.threads;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;
import net.pphh.jabc.Bookmark;
import net.pphh.jabc.Status;
import org.apache.log4j.Logger;


public class CheckURLThread implements Callable<Bookmark> {

    public CheckURLThread(Bookmark bookmark) {
        this.bookmark = bookmark;
    }

    @Override
    public Bookmark call() throws Exception {
        try {
            URL url = getURL();
            if (url.getProtocol().contains("https")) {
                logger.warn("Checking for HTTPS URL not implemented! " + url);
                //bookmark.setStatus(Status.NEW);
            } else {
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                int code = conn.getResponseCode();
                bookmark.setCode(code);
                bookmark.setStatus(parseResponseCode(code));
                conn.disconnect();
            }
        } catch (IOException ex) {
            logger.error(ex.getMessage());
            bookmark.setStatus(Status.ERROR);
        }
        return bookmark;
    }
    
    private Status parseResponseCode(int code) {
        Status status = Status.OK;
        if (code >= 100 && code < 200) {
            status = Status.WARN;
        } else if (code >= 300 && code < 400) {
            status = Status.WARN;
        } else if (code >= 400 && code < 500) {
            status = Status.ERROR;
        } else if (code >= 500 && code <= 505) {
            status = Status.ERROR;
        } else {
            status = Status.OK;
        }
        
        return status;
    }
    
    private URL getURL() {
        return (URL) bookmark.getUrl();
    }
    
    private Bookmark bookmark;
    static Logger logger = Logger.getLogger(CheckURLThread.class);
}
