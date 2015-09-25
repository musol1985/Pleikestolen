/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.font.BitmapFont;
import com.jme3.math.Vector3f;
import com.jme3.network.Client;
import com.jme3.network.ClientStateListener;
import com.jme3.network.ClientStateListener.DisconnectInfo;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;
import com.preikestolen.client.listeners.GameListener;
import com.preikestolen.client.listeners.LobbyListener;
import com.preikestolen.common.Mensajes;
import com.preikestolen.net.msg.lobby.MsgJugadorInfo;
import com.preikestolen.persist.dao.JugadorDAO;
import com.preikestolen.persist.dao.MundoDAO;
import com.preikestolen.world.MundoClient;
import com.simsilica.lemur.GuiGlobals;
import de.lessvoid.nifty.Nifty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;




public class Game extends Preikestolen implements ClientStateListener{
    public int puerto=42601;
    public String host="localhost";
    public String nick="Player"+System.currentTimeMillis();
    
    private JugadorDAO jugador;
    private List<JugadorDAO> jugadores=new ArrayList<JugadorDAO>();
    
    private Client net;
    
    private BulletAppState fisicas;
    private Nifty nifty;
    public BitmapFont font48;
    public BitmapFont font34;
   // private NetSyncState netSync;

    public static void main(String[] args) {
        Game app = new Game();

        if(args.length>0){
            app.setHost(args[0]);
            app.setPuerto(args[1]);
            app.setNick(args[2]);
        }
        app.setNick("Edu");
        
        AppSettings set=new AppSettings(true);
        set.setHeight(720);
        set.setWidth(1280);
       // set.setFrameRate(60);
        app.setSettings(set);
        
        MundoDAO.PATH="cache/"+MundoDAO.PATH;
        app.setShowSettings(false);
       // app.setNick("Ed2u");
        app.setPauseOnLostFocus(false);
        app.start();
    }
    
    public void setPuerto(String puerto){
        this.puerto=Integer.parseInt(puerto);
    }
    
    public void setHost(String host){
        this.host=host;
    }
    
    public void setNick(String nick){
        this.nick=nick;
    }

    @Override
    public void simpleInitApp() {
        GuiGlobals.initialize(this);
        
        setDisplayStatView(false);
        
        font48=assetManager.loadFont("Interface/fnt/Texto48.fnt"); 
        font34=assetManager.loadFont("Interface/fnt/Texto34.fnt"); 
        
        fisicas = new BulletAppState();
       // netSync=new NetSyncState();
        
        fisicas.setThreadingType(BulletAppState.ThreadingType.PARALLEL);
        
        stateManager.attach(fisicas);
        //stateManager.attach(new GameGUI());
       // stateManager.attach(netSync);
        //fisicas.setDebugEnabled(true);
        fisicas.getPhysicsSpace().setGravity(new Vector3f(0,-100,0));
        Mensajes.init();
        try {
            net=Network.connectToServer(GAME_NAME, GAME_VERSION, host, puerto);
            
            net.addClientStateListener(this);
            goLobby();
            
            net.start();
        } catch (IOException ex) {
            Logger.getLogger(GameServer.class.getName()).severe(ex.getMessage());
            ex.printStackTrace();
        }
        
       /*   NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager,
                                                          inputManager,
                                                          audioRenderer,
                                                          guiViewPort);
      nifty = niftyDisplay.getNifty();
        guiViewPort.addProcessor(niftyDisplay);
        nifty.fromXml("Interface/Game.xml", "start");*/
    }


    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
    public void destroy() {
      getMundoClient().close();
      net.close();
      super.destroy();
    }

    public void clientConnected(Client c) {
        System.out.println("Conectado!");
        jugador=new JugadorDAO(nick, false);
        net.send(new MsgJugadorInfo(nick, false));
    }
    
    public void addJugadorExt(JugadorDAO j){
        jugadores.add(j);
    }
    
    public List<JugadorDAO> getJugadoresExt(){
        return jugadores;
    }

    public void clientDisconnected(Client c, DisconnectInfo info) {
        System.out.println("Te han echado");
    }

    public JugadorDAO getJugador() {
        return jugador;
    }
    
    public void setJugador(JugadorDAO j){
        this.jugador=j;
    }
    
    public MundoClient getMundoClient(){
        return getStateManager().getState(MundoClient.class);
    }
    
    public Client getNet(){
        return net;
    }


    @Override
    public void goJugando(MundoDAO m) {
        synchronized(this){
            m.crearClienteCache();
            setJugando(m);
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


    public PhysicsSpace getFisicas(){
        return fisicas.getPhysicsSpace();
    }
}
