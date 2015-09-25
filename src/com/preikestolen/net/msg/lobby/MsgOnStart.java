/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.net.msg.lobby;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import com.preikestolen.persist.dao.MundoDAO;

/**
 *
 * @author Edu
 */
@Serializable
public class MsgOnStart extends AbstractMessage {
    public MundoDAO mundo;
    
    public MsgOnStart() {

    }

    public MsgOnStart(MundoDAO mundo) {
        this.mundo=mundo;
    }
    
    
}
