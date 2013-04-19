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

package net.pphh.jabc.impl;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import net.pphh.jabc.Bookmark;
import net.pphh.jabc.intf.BookmarkParser;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class HTMLParser implements BookmarkParser {

    @Override
    public void open(File file) {
        logger.debug("open()");
        this.file = file;
    }

    @Override
    public void parse() {
        logger.debug("parse()");
        try {
            doc = Jsoup.parse(file, null);
            Elements elements = doc.select("a[href^=http]");
            logger.debug("size of elements: " + elements.size());
            for (Element e : elements) {
                Bookmark bookmark;
                URL url = new URL(e.attr("href"));
                String desc = e.text();
                logger.debug("URL: " + url.toString() + " Description: " + desc);
                bookmark = new Bookmark(url, desc);
                list.add(bookmark);
            }
        } catch (IOException ex) {
            logger.error(ex.getLocalizedMessage());
        }
    }

    @Override
    public List<Bookmark> getBookmarks() {
        return list;
    }

    @Override
    public void close() {
        // not needed
    }
    
    private Document doc;
    private File file;
    private List<Bookmark> list = new ArrayList<>();
    static Logger logger = Logger.getLogger(HTMLParser.class);
}
