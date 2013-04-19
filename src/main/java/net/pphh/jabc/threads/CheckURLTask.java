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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javafx.application.Platform;
import javafx.concurrent.Task;
import net.pphh.jabc.Bookmark;
import org.apache.log4j.Logger;
import org.perf4j.StopWatch;
import org.perf4j.log4j.Log4JStopWatch;


public class CheckURLTask extends Task<List<Bookmark>> {
    
    public CheckURLTask(List<Bookmark> list) {
        this.list = list;
    }
    
    @Override
    protected List<Bookmark> call() throws Exception {
        logger.debug("starting");
        StopWatch stopwatch = new Log4JStopWatch("CheckURL");
        updateProgress(-1, -1);
        List<Bookmark> resultlist = new ArrayList<>();
        List<Future<Bookmark>> futurelist = new ArrayList<>();
        executor = Executors.newFixedThreadPool(200);
        for (Bookmark b : list) {
            if (isCancelled()) {
                break;
            } 
            futurelist.add(executor.submit(new CheckURLThread(b)));
        }
        for (Future<Bookmark> f : futurelist) {
            if (isCancelled()) {
                break;
            }
            resultlist.add(f.get());
        }
        logger.debug("shutting down executor service");
        executor.shutdown();
        if (isCancelled()) {
            resultlist = list;
        }
        Platform.runLater(new ReturnURLCheckResults(resultlist));
        updateProgress(0, 1);
        stopwatch.stop();
        
        return resultlist;
    }
    
    @Override
    protected void cancelled() {
        super.cancelled();
        updateProgress(0, 1);
        executor.shutdownNow();
        Platform.runLater(new ReturnURLCheckResults(list));
        logger.debug("task cancelled.");
    }
    
    private final List<Bookmark> list;
    private ExecutorService executor;
    
    static Logger logger = Logger.getLogger(CheckURLTask.class);
}
