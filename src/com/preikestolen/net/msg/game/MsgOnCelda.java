/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.net.msg.game;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import com.preikestolen.persist.dao.CeldaDAO;

/**
 *
 * @author Edu
 */
@Serializable
public class MsgOnCelda extends AbstractMessage {
    public CeldaDAO celda;
    
    public MsgOnCelda() {

    }
    
    public MsgOnCelda(CeldaDAO celda) {
        this.celda=celda;
    }
}
