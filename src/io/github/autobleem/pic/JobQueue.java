/*
 * Copyright (C) 2019 artur.jakubowicz
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.autobleem.pic;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author artur.jakubowicz
 */
public class JobQueue extends ArrayList<Job>{
    private TimerTask updateTask;
    private Timer timer;
    public JobQueue() {
        timer = new Timer(true);
        final JobQueue thisQueue = this;
        updateTask = new TimerTask() {
            @Override
            public void run() {
                int running = getRunningTasks();
                int threads = Config.threads;
                int freeThreads = threads-running;
                if (freeThreads>0)
                {
                     for (Job job: thisQueue)
                     {
                         if (job.isNew())
                         {
                             if (freeThreads>0)
                             {
                             job.start();
                             freeThreads--;
                             }
                         }
                     }
                   
                }
               
            }
        };
        timer.schedule(updateTask, 0, 500);
        
    }
    
    public int getRunningTasks()
    {
        int val=0;
        for (Job job:this)
        {
           if (job.isRunning()) val++;
        }
        return val;
    }
    
    public Job schedule(String fileName)
    {
        for (Job job:App.queue)
        {
            if (job.getFilename().equals(fileName))
            {
                return null;
            }
        }
        Job job = new Job();
        if (job.schedule(fileName))
        {
            this.add(job);
            return job;
        }
        return null;
        
    }
}
