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
import java.net.URL;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import net.pphh.jabc.Bookmark;
import net.pphh.jabc.intf.BookmarkWriter;
import org.apache.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class HTMLWriter implements BookmarkWriter {

    @Override
    public void open(File file) {
        result = new StreamResult(file);
    }

    @Override
    public void write(List<Bookmark> list) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = docFactory.newDocumentBuilder();
            Document doc = builder.newDocument();
            DOMSource source = new DOMSource(doc);
            Element root = doc.createElement("html");
            doc.appendChild(root);
            Element title = doc.createElement("title");
            title.appendChild(doc.createTextNode("Bookmarks"));
            root.appendChild(title);
            Element h1 = doc.createElement("h1");
            h1.appendChild(doc.createTextNode("Bookmarks Menu"));
            root.appendChild(h1);
            Element dl = doc.createElement("dl");
            root.appendChild(dl);
            logger.info("Exporting Bookmark to HTML");
            logger.debug("Exporting entries: " + list.size());
            for (Bookmark b : list) {
                Element dt = doc.createElement("dt");
                Element a = doc.createElement("a");
                Attr href = doc.createAttribute("href");
                href.setValue(((URL) b.getUrl()).toString());
                a.setAttributeNode(href);
                if (b.getDescription() != null) {
                    a.appendChild(doc.createTextNode(b.getDescription()));
                }
                dt.appendChild(a);
                dl.appendChild(dt);
            }
            TransformerFactory transformFactory = TransformerFactory.newInstance();
            Transformer transformer = transformFactory.newTransformer();
            transformer.transform(source, result);
            logger.debug("HTML bookmark export completed");
        } catch (ParserConfigurationException | TransformerException ex) {
            logger.error(ex.getLocalizedMessage(), ex);
        }
    }

    @Override
    public void close() {
    }
    private StreamResult result;
    static Logger logger = Logger.getLogger(HTMLWriter.class);
}
