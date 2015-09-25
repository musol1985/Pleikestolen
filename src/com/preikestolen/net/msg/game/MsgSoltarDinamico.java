/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.net.msg.game;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Edu
 */
@Serializable
public class MsgSoltarDinamico extends AbstractMessage {
    public String celda;
    public String id;
    public int pilaId;

    public MsgSoltarDinamico(String celda, String id, int pilaId) {
        this.celda = celda;
        this.id = id;
        this.pilaId=pilaId;
    }
    
    public MsgSoltarDinamico() {
    }
}
