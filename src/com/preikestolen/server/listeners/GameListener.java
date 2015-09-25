/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.server.listeners;


import com.jme3.network.Filters;
import com.jme3.network.HostedConnection;
import com.preikestolen.GameServer;
import com.preikestolen.common.MensajesListener;
import com.preikestolen.net.msg.game.MsgAccion;
import com.preikestolen.net.msg.game.MsgCogerDinamico;
import com.preikestolen.net.msg.game.MsgDinamicoPosition;
import com.preikestolen.net.msg.game.MsgDinamicoSleep;
import com.preikestolen.net.msg.game.MsgGetCeldas;
import com.preikestolen.net.msg.game.MsgMatarEstatico;
import com.preikestolen.net.msg.game.MsgOnCelda;
import com.preikestolen.net.msg.game.MsgOnMatarEstatico;
import com.preikestolen.net.msg.game.MsgPosition;
import com.preikestolen.net.msg.game.MsgSetTool;
import com.preikestolen.net.msg.game.MsgSoltarDinamico;
import com.preikestolen.persist.dao.CeldaDAO;
import com.preikestolen.persist.dao.JugadorDAO;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Edu
 */
public class GameListener extends MensajesListener<HostedConnection>{
    private static Logger log=Logger.getLogger(GameServer.class.getName());
    private GameServer server;
    

    public GameListener(GameServer server){
        super();
        this.server=server;
    }

    
    public synchronized  void onGetCeldas(HostedConnection source, MsgGetCeldas m){                
        for(int i=0;i<m.posiciones.size();i++){
            //log.log(Level.INFO, "    {0}---{1}", new Object[]{m.posiciones.get(i), m.timestamps.get(i)});
            CeldaDAO c=server.getService().getCelda(m.posiciones.get(i), true);
            if(c.time<0){
                //Este metodo la genera y la envia x net
                server.getService().generarCelda(c, source);
            }else if(c.time!=m.timestamps.get(i)){
                log.log(Level.INFO, "Timestamp diferente para la celda {0}. Se reenvia al cliente.", c.getID());
                source.send(new MsgOnCelda(c));
            }else{
                log.info("Timestamp igual, se envia que debe cargar el cacheado");
                source.send(new MsgOnCelda(new CeldaDAO(c.getID())));                
            }
        }
    }
    
    
    public void onPosition(HostedConnection source, MsgPosition m){           
        server.getNet().broadcast(Filters.notIn(source), m);
    }
    
    public void onPositionDinamico(HostedConnection source, MsgDinamicoPosition m){  
        log.log(Level.INFO, "onPositionDin {0} {1}", new Object[]{m.celda, m.id});
        if(server.getService().posicionarDinamico(m.celda, m.id, m.posicion)){
            server.getNet().broadcast(Filters.notIn(source), m);
        }   
    }
    
    public void onMatarEstatico(HostedConnection source, MsgMatarEstatico m){
        MsgOnMatarEstatico msg=server.getService().matarEstatico(m.celdaId, m.objetoId);
        if(msg!=null){
            server.getNet().broadcast(Filters.notIn(source), msg);
            msg.controlasTu=true;
            source.send(msg);
        }
    }
    
    
    public void onSleepDinamico(HostedConnection source, MsgDinamicoSleep m){
        server.getNet().broadcast(Filters.notIn(source), m);
    }
    
    public void onAccion(HostedConnection source, MsgAccion m){
        server.getNet().broadcast(Filters.notIn(source), m);
    }
    
    public void onCogerDinamico(HostedConnection source, MsgCogerDinamico m){
        try{
            server.getService().cogerDinamico(m.celda, m.id, m.jugador);
            server.getNet().broadcast(Filters.notIn(source), m);
        }catch(Exception e){
            log.log(Level.SEVERE, "Error al coger dinamico: {0}", e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void onAccion(HostedConnection source, MsgSetTool m){
        server.getNet().broadcast(Filters.notIn(source), m);
    }
    
    public void onSoltarDinamico(HostedConnection source, MsgSoltarDinamico m){
        try{
            JugadorDAO jugador=source.getAttribute(JugadorDAO.ID);
            server.getService().soltarDinamico(m.celda, m.id, jugador, m.pilaId);
            server.getNet().broadcast(Filters.notIn(source), m);
        }catch(Exception e){
            log.log(Level.SEVERE, "Error al coger dinamico: {0}", e.getMessage());
            e.printStackTrace();
        }
    }
}

