/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.net.msg.game;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import com.preikestolen.persist.dao.PosicionRotacionDAO;

/**
 *
 * @author Edu
 */
@Serializable
public class MsgDinamicoSleep extends MsgDinamicoPosition {

    public MsgDinamicoSleep() {

    }


    public MsgDinamicoSleep(String celda, String id, long time, PosicionRotacionDAO pos) {
        super(celda, id, time, pos);
    }
    

}
