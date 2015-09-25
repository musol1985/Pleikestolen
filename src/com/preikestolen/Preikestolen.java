/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen;

import com.jme3.app.SimpleApplication;
import com.jme3.network.MessageListener;
import com.preikestolen.persist.dao.CeldaDAO;
import com.preikestolen.persist.dao.JugadorDAO;
import com.preikestolen.persist.dao.MundoDAO;

/**
 *
 * @author Edu
 */
public abstract class Preikestolen extends SimpleApplication {
    public static final String GAME_NAME="Preikestolen";
    public static final int GAME_VERSION=1;
    
    protected boolean jugando;
    protected MessageListener listener;
    protected MundoDAO mundo;
    
    public abstract void goJugando(MundoDAO mundo);
    public abstract void goLobby();
    public abstract void changeListener(MessageListener listener);
    
    protected synchronized void setJugando(MundoDAO mundo){
        System.out.println("setJugando...");
        jugando=true;
        CeldaDAO.FILENAME=MundoDAO.PATH+mundo.getID()+"/#.celda";
    }
    
    protected synchronized void setLobby(){
        System.out.println("setLobby...");
        jugando=false;
    }

    public MundoDAO getMundo(){
        return mundo;
    }
    
    public void setMundo(MundoDAO m){
        this.mundo=m;
    }
    
    public JugadorDAO getJugador(String id){
        for(JugadorDAO j: mundo.getJugadores()){
            if(j.getNombre().equals(id))
                return j;
        }
        return null;
    }
}
