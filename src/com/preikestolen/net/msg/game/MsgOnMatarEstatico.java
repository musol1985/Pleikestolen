/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.net.msg.game;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import com.preikestolen.persist.dao.DinamicoDAO;
import com.preikestolen.persist.dao.EstaticoDAO;
import java.util.List;

/**
 *
 * @author Edu
 */
@Serializable
public class MsgOnMatarEstatico extends AbstractMessage {
    public String celdaId;
    public String objetoId;
    public Long time;
    public List<DinamicoDAO> added;
    public boolean controlasTu;
    
    public MsgOnMatarEstatico() {

    }
    
    public MsgOnMatarEstatico(String celdaId, String objetoId, Long time, List<DinamicoDAO> added) {
        this.celdaId=celdaId;
        this.objetoId=objetoId;
        this.time=time;
        this.added=added;
    }
}
