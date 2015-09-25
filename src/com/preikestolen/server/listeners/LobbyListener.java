/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.server.listeners;


import com.jme3.math.Vector3f;
import com.jme3.network.Filters;
import com.jme3.network.HostedConnection;
import com.preikestolen.GameServer;
import com.preikestolen.common.MensajesListener;
import com.preikestolen.net.msg.lobby.MsgCrearMundo;
import com.preikestolen.persist.dao.JugadorDAO;
import com.preikestolen.net.msg.lobby.MsgError;
import com.preikestolen.net.msg.lobby.MsgJugadorInfo;
import com.preikestolen.net.msg.lobby.MsgJugadorJoined;
import com.preikestolen.net.msg.lobby.MsgJugadoresLista;
import com.preikestolen.net.msg.lobby.MsgMundoCreado;
import com.preikestolen.persist.dao.MundoDAO;
import com.preikestolen.net.msg.lobby.MsgMundosLista;
import com.preikestolen.net.msg.lobby.MsgOnStart;
import com.preikestolen.net.msg.lobby.MsgStart;
import com.preikestolen.world.MundoServer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Edu
 */
public class LobbyListener extends MensajesListener<HostedConnection>{
    private GameServer server;
    

    public LobbyListener(GameServer server){
        super();
        this.server=server;
    }

    
    public void onJugadorInfo(HostedConnection source, MsgJugadorInfo m){     
        List<JugadorDAO> jugadores=new ArrayList<JugadorDAO>();
        
        for(HostedConnection c:server.getNet().getConnections()){            
            if(c!=source){
                JugadorDAO j=c.getAttribute(JugadorDAO.ID);
                jugadores.add(j);
                if(j!=null){
                    if(m.nick.equals(j.getNombre())){
                        source.send(new MsgError(MsgError.NICK_EXISTENTE));
                        return;
                    }
                }
            }
         }  
        
        JugadorDAO j=new JugadorDAO(m.nick,server.getNet().getConnections().size()==1);
 
        source.setAttribute(JugadorDAO.ID, j);
        m.owner=j.isOwner();
        source.send(m);
        
        if(j.isOwner()){
            source.send(new MsgMundosLista(server.getService().getMundos()));
        }else{
            source.send(new MsgJugadoresLista(jugadores));
        }
        
        server.getNet().broadcast( Filters.notEqualTo(source), new MsgJugadorJoined(m.nick, m.owner) );
    }

    public void onCrearMundo(HostedConnection source, MsgCrearMundo m){      
        try{
            MundoDAO mundo=server.getService().crearMundo(m.nombre);  
            
            System.out.println("Mundo creado "+mundo.getID());
            
            source.send(new MsgMundoCreado(mundo));
        }catch(Exception e){
            e.printStackTrace();
            source.send(new MsgError(MsgError.MUNDO_EXISTENTE));
        }
    }
    
    public void onStart(HostedConnection source, MsgStart m){              
        
        try{                                                
            MundoDAO mundo=(MundoDAO)MundoDAO.cargarById(m.mundoID);
            server.getService().iniciarMundo( mundo, server);
            
            for(JugadorDAO j:mundo.getJugadores()){
                for(HostedConnection c:server.getNet().getConnections()){            
                    JugadorDAO jCnn=c.getAttribute(JugadorDAO.ID);
                    if(jCnn.getNombre().equals(j.getNombre())){
                        c.setAttribute(JugadorDAO.ID, j);
                        break;
                    }                
                }
            }
            
            server.goJugando(mundo);
            
            server.getStateManager().attach(new MundoServer(mundo));
            
            server.getNet().broadcast(new MsgOnStart(mundo));
        }catch(Exception e){
            e.printStackTrace();
            source.send(new MsgError(MsgError.MUNDO_FALTAN_JUGADORES));
        }
    }
}

