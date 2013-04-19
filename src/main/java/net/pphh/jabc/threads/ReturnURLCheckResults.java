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

import java.util.List;
import net.pphh.jabc.Bookmark;
import net.pphh.jabc.Model;


public class ReturnURLCheckResults implements Runnable {

    public ReturnURLCheckResults(List<Bookmark> list) {
        this.list = list;
    }
    
    @Override
    public void run() {
        Model.getInstance().addAll(list);
    }
    
    private List<Bookmark> list;
}
