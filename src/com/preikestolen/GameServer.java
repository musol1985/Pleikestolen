/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen;

import com.jme3.app.SimpleApplication;
import com.jme3.network.ConnectionListener;
import com.jme3.network.HostedConnection;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeContext;
import com.preikestolen.common.Mensajes;
import com.preikestolen.persist.dao.MundoDAO;
import com.preikestolen.server.listeners.GameListener;
import com.preikestolen.server.listeners.LobbyListener;
import com.preikestolen.server.service.ServerService;
import com.preikestolen.world.MundoServer;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.logging.Logger;


public class GameServer extends Preikestolen implements ConnectionListener {

    public int puerto=42601;
    
    private Server net;
    private ServerService servicio;

    
    public static void main(String[] args) {
        GameServer app = new GameServer();
        
       // app.setShowSettings(false);
        app.start(JmeContext.Type.Headless);

        
        if(args.length>0)
            app.setPuerto(args[0]);
        
        app.start();
    }
    
    public void setPuerto(String puerto){
        this.puerto=Integer.parseInt(puerto);
    }
    
    public MundoServer getMundoServer(){
        return getStateManager().getState(MundoServer.class);
    }

    @Override
    public void simpleInitApp() {
        iniciarNetwork();
    }
    
    public void iniciarNetwork(){
        servicio=new ServerService(this);
        
        Mensajes.init();
        try {
            net = Network.createServer(GAME_NAME, GAME_VERSION, puerto, puerto);          
            net.addConnectionListener(this);
            
            goLobby();
            net.start();
        } catch (IOException ex) {
            Logger.getLogger(GameServer.class.getName()).severe(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
    @Override
    public void destroy() {
        net.close();
        super.destroy();
    }

    public void connectionAdded(Server server, HostedConnection conn) {
        synchronized(this){
             Logger.getLogger(GameServer.class.getName()).info("Conexion entrante "+conn.getAddress());
            if(jugando){
                Logger.getLogger(GameServer.class.getName()).info("No se deja conectar a "+conn.getAddress()+" porque ya se está jugando");
                conn.close("La partida ha empezado");
            }
        }
    }    

    public void connectionRemoved(Server server, HostedConnection conn) {
        if(!server.hasConnections() && jugando){
            goLobby();
        }
    }
    
    public Server getNet(){
        return net;
    }
    
    public ServerService getService(){
        return servicio;
    }

    @Override
    public void goJugando(MundoDAO mundo) {
        synchronized(this){
            setJugando(mundo);
            changeListener(new GameListener(this));
        }
    }

    @Override
    public void goLobby() {
        synchronized(this){
            setLobby();
            changeListener(new LobbyListener(this));
        }
    }

    @Override
    public void changeListener(MessageListener listener) {
        net.removeMessageListener(this.listener);
        this.listener=listener;
        net.addMessageListener(listener);
    }
}
