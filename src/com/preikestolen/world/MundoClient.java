/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.world;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.preikestolen.Game;
import com.preikestolen.common.Cosas;
import com.preikestolen.common.TipoCosa;
import com.preikestolen.gui.GameGUI1;
import com.preikestolen.net.msg.game.MsgAccion;
import com.preikestolen.net.msg.game.MsgPosition;
import com.preikestolen.net.msg.game.MsgSetTool;
import com.preikestolen.persist.dao.CosaDAO;
import com.preikestolen.persist.dao.EstaticoDAO;
import com.preikestolen.persist.dao.JugadorDAO;
import com.preikestolen.persist.dao.MundoDAO;
import com.preikestolen.world.colisions.CollisionManager;
import com.preikestolen.world.elements.Camara;
import com.preikestolen.world.elements.Controles;
import com.preikestolen.world.elements.Entorno;
import com.preikestolen.world.elements.Terreno;
import com.preikestolen.world.elements.characters.Player;
import com.preikestolen.world.elements.characters.PlayerExt;
import com.preikestolen.world.elements.terreno.elementos.ILight;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 *
 * @author Edu
 */
public class MundoClient extends AbstractAppState{
    public Game app;
    
    public MundoDAO dao;
    
    public Entorno entorno;
        
    public Camara cam;
    public Controles controles;
    public Node escena;
    
    public Terreno terreno;
    
    public Player player;
    public HashMap<String, PlayerExt> players=new HashMap<String, PlayerExt>();
    
    public ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(4);
    
    private long timeStart;
    
    public MundoClient(MundoDAO dao, JugadorDAO jugador, List<JugadorDAO> jugadores){
        this.timeStart=System.currentTimeMillis();
        this.dao=dao;
        this.player=new Player(jugador, this);
        for(JugadorDAO j:jugadores){
            players.put(j.getNombre(), new PlayerExt(j,this));
        }
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application a) {
        super.initialize(stateManager, a); //To change body of generated methods, choose Tools | Templates.
        this.app=(Game)a;
        
        escena=new Node("MainEscena");        
        
        cam=new Camara(app, this);
        controles=new Controles(app, this);        

        terreno=new Terreno(this);
        
        entorno=new Entorno(app, this);
        
        player.iniciar(app, this);
        player.attach(this);
        
        for(Entry<String, PlayerExt> p:players.entrySet()){
            p.getValue().iniciar(app, this);
            p.getValue().attach(this);
        }
        //cam.setDebug(app);                       
        app.getRootNode().attachChild(escena);       

        terreno.iniciar(player.getWorldTranslation());

        app.getFisicas().addCollisionListener(new CollisionManager(this));
        
        //app.getStateManager().attach(new DebugGUI());
        //app.getStateManager().attach(new GameGUI());
        app.getStateManager().attach(new GameGUI1());
        
      /*  for(int i=0;i<30;i++)
            player.getDAO().addCosa( new EstaticoDAO("", Vector3f.NAN, Cosas.Tipo.TRONCO, 0, 1, 100), getGUI());*/
        
        player.getDAO().addCosa(new EstaticoDAO("",Vector3f.NAN, Cosas.Tipo.HACHA,0,1,100), getGUI());
        /*for(int i=0;i<30;i++)
            player.getDAO().addCosa( new EstaticoDAO("", Vector3f.NAN, Cosas.Tipo.TRONCO, 0, 1, 100), getGUI());*/
        player.getDAO().addCosa(new EstaticoDAO("",Vector3f.NAN, Cosas.Tipo.ANTORCHA,0,1,100), getGUI());
    }
    
    public GameGUI1 getGUI(){
        return app.getStateManager().getState(GameGUI1.class);
    }

    @Override
    public void cleanup() {
        app.getRootNode().detachChild(escena);
        executor.shutdown();
        super.cleanup(); //To change body of generated methods, choose Tools | Templates.        
    }
    
    

    @Override
    public void update(float tpf) {
        player.update(app.getCamera(),tpf);
        entorno.diaNoche.update(tpf);
    }

    public Game getApp() {
        return app;
    }

    public void espacio(){
       /* Geometry bulletg = new Geometry("bullet", new Sphere(30, 30, 1));
        Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Yellow);
        bulletg.setMaterial(mat);
        bulletg.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        bulletg.setLocalTranslation(app.getCamera().getLocation());

        SphereCollisionShape bulletCollisionShape = new SphereCollisionShape(1f);
        RigidBodyControl bulletNode = new RigidBodyControl(bulletCollisionShape, 1);

        bulletg.addControl(bulletNode);
        bulletNode.setLinearVelocity(app.getCamera().getDirection().mult(25));
        app.getRootNode().attachChild(bulletg);
        app.getFisicas().add(bulletNode);*/
        player.doAccionNet("accion1", this);
    }
    
    public void control(boolean value){
        player.doControl(value);
    }
    
    public void netSync(MsgPosition m){
        players.get(m.id).sync(m);
    }
    
    public void onNetAccion(MsgAccion m){
        players.get(m.id).doAccion(m.accion, this, false);
    }
    
    public void onNetSetTool(MsgSetTool m){
        players.get(m.jugador).cambiarTool(new CosaDAO(Cosas.getTipo(m.tipoCosa), 10));
    }

    
    public void close(){
        terreno.close();
    }
    
    public long getTime(){
        return System.currentTimeMillis()-timeStart;
    }
    
    public void addLuz(ILight luz){
        entorno.luz.addLight(luz, app);
    }
}
