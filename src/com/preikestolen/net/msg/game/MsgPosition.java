/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.net.msg.game;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import com.preikestolen.persist.dao.JugadorDAO;
import com.preikestolen.persist.dao.PosicionDAO;

/**
 *
 * @author Edu
 */
@Serializable
public class MsgPosition extends AbstractMessage {
    public String id;
    public PosicionDAO posicion;
    
    public MsgPosition() {

    }
    
    public MsgPosition(String id, PosicionDAO pos) {
        this.id=id;
        posicion=pos;
    }
}
