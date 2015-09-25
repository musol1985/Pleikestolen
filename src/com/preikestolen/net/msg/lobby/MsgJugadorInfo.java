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
public class MsgJugadorInfo extends AbstractMessage {
    public String nick;
    public boolean owner;
    
    public MsgJugadorInfo() {

    }

    public MsgJugadorInfo(String nick, boolean owner) {
        this.nick = nick;
        this.owner = owner;
    }
    
    
}
