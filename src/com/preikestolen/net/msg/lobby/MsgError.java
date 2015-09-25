/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.net.msg.lobby;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Edu
 */
@Serializable
public class MsgError extends AbstractMessage {
    public static final int NICK_EXISTENTE=1;
    public static final int MUNDO_EXISTENTE=2;
    public static final int MUNDO_FALTAN_JUGADORES=3;
    
    public int id;
    
    public MsgError() {

    }

    public MsgError(int id) {
        this.id=id;
    }
    
    
}
