package com.payconiq.zekigu.githubrepos.core.imageloader.reqcommand;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by zekigu on 15.09.2017
 */
public class LoadImageInvoker {

    Queue<Load> loadImageCommands = new LinkedList<>();

    public void addLoadRequest(Load pLoad){
        loadImageCommands.add(pLoad);
    }

    public void executeCommands(){
        while(!loadImageCommands.isEmpty()){
            Load command = loadImageCommands.poll();
            command.load();
        }
    }
}
