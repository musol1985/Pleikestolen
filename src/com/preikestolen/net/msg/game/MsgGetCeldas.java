/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.net.msg.game;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import com.preikestolen.world.elements.terreno.Celda;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

/**
 *
 * @author Edu
 */
@Serializable
public class MsgGetCeldas extends AbstractMessage {
    public List<String> posiciones;
    public List<Long> timestamps;
    
    public MsgGetCeldas() {

    }
    
    public MsgGetCeldas(List<Celda> celdas) {
        posiciones=new ArrayList<String>();
        timestamps=new ArrayList<Long>();
        for(Celda c:celdas){
            posiciones.add(c.getDao().getID());
            timestamps.add(c.getDao().getTime());
        }
    }
}
