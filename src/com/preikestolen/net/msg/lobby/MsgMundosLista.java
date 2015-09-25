/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.net.msg.lobby;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import com.preikestolen.persist.dao.MundoDAO;
import java.util.List;

/**
 *
 * @author Edu
 */
@Serializable
public class MsgMundosLista extends AbstractMessage {
    public List<MundoDAO> mundos;
  
    public MsgMundosLista() {

    }

    public MsgMundosLista(List<MundoDAO> mundos) {
        this.mundos=mundos;
    }
        
    
}
