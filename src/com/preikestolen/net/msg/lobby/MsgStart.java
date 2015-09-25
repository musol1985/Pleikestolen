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
public class MsgStart extends AbstractMessage {
    public String mundoID;
    
    public MsgStart() {

    }
    
    public MsgStart(String mundoID) {
        this.mundoID=mundoID;
    }
}
