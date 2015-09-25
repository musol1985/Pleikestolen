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
public class MsgCogerDinamico extends AbstractMessage {
    public String celda;
    public String id;
    public String jugador;

    public MsgCogerDinamico(String celda, String id, String jugador) {
        this.celda = celda;
        this.id = id;
        this.jugador = jugador;
    }
    
    public MsgCogerDinamico() {
    }
}
