/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.autobleem.pic.mt;

import io.github.autobleem.pic.Util;

/**
 *
 * @author screemer
 */
public class WorkerFactory {
    public static ProcessingWorker getWorker(String filename)
    {
        if (Util.isCISO(filename))
        {
            CisoExpandWorker worker = new CisoExpandWorker();
            worker.init(filename);
            return worker;
        }
        if (Util.isISO(filename))
        {
            CisoCompressWorker worker = new CisoCompressWorker();
            worker.init(filename);
            return worker;
        }
        
        return null;
    }
}
