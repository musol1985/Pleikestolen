/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.net.msg.lobby;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import com.preikestolen.persist.dao.JugadorDAO;
import com.preikestolen.persist.dao.MundoDAO;
import java.util.List;

/**
 *
 * @author Edu
 */
@Serializable
public class MsgJugadoresLista extends AbstractMessage {
    public List<JugadorDAO> jugadores;
  
    public MsgJugadoresLista() {

    }

    public MsgJugadoresLista(List<JugadorDAO> jugadores) {
        this.jugadores=jugadores;
    }
        
    
}
