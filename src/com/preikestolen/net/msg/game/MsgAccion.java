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
public class MsgAccion extends AbstractMessage {
    public String id;
    public String accion;
    
    public MsgAccion() {

    }
    
    public MsgAccion(String id, String accion) {
        this.id=id;
        this.accion=accion;
    }
}
