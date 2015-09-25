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
public class MsgMatarEstatico extends AbstractMessage {
    public String celdaId;
    public String objetoId;
    
    public MsgMatarEstatico() {

    }
    
    public MsgMatarEstatico(String celdaId, String objetoId) {
        this.celdaId=celdaId;
        this.objetoId=objetoId;
    }
}
