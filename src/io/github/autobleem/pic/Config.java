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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author artur.jakubowicz
 */
public class Config {
    public static int compressionLevel = 6;
    public static int threads = 4;
    public static boolean removeInput = false;
    
    public static final String VERSION = "0.1-master"; 
    
    public static void load()
    {
        try {
            Properties prop = new Properties();
            FileInputStream fis = new FileInputStream("config.properties");
            prop.load(fis);
            fis.close();
            if (prop.getProperty("compressionLevel")!=null)
            {
                compressionLevel = Integer.parseInt(prop.getProperty("compressionLevel"));
            }
            if (prop.getProperty("threads")!=null)
            {
                threads = Integer.parseInt(prop.getProperty("threads"));
            }
              if (prop.getProperty("removeInput")!=null)
            {
                removeInput = Boolean.parseBoolean(prop.getProperty("removeInput"));
            }
            
        } catch (Exception ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
      public static void save()
    {
        try {
            Properties prop = new Properties();
            prop.setProperty("compressionLevel", Integer.toString(compressionLevel));
            prop.setProperty("threads", Integer.toString(threads));
            prop.setProperty("removeInput", Boolean.toString(removeInput));
                  FileOutputStream fis = new FileOutputStream("config.properties");
            prop.store(fis,"PSPCisoCruncher Configuration");
            fis.flush();
            fis.close();
           
            
        } catch (Exception ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
