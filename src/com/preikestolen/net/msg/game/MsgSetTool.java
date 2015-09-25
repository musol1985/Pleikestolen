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
public class MsgSetTool extends AbstractMessage {
    public String jugador;
    public int tipoCosa;
    
    public MsgSetTool() {

    }
    
    public MsgSetTool(String jugador, int tipoCosa) {
        this.jugador=jugador;
        this.tipoCosa=tipoCosa;
    }
}
