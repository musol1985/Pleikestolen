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
public class MsgDinamicoPosition extends AbstractMessage {
    public String celda;
    public String id;
    public PosicionRotacionDAO posicion;
    public Long time;
    
    public MsgDinamicoPosition() {

    }
    
    public MsgDinamicoPosition(String celda, String id, long time, PosicionRotacionDAO pos) {
        this.id=id;
        this.celda=celda;
        posicion=pos;
        this.time=time;
    }
}
