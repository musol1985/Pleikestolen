/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.net.msg.game;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import com.preikestolen.persist.dao.DinamicoDAO;

/**
 *
 * @author Edu
 */
@Serializable
public class MsgOnSoltarDinamico extends AbstractMessage {
    public String celda;
    public DinamicoDAO dinamico;

    public MsgOnSoltarDinamico(String celda, DinamicoDAO din) {
        this.celda = celda;
        this.dinamico=din;
    }
    
    public MsgOnSoltarDinamico() {
        
    }
}
