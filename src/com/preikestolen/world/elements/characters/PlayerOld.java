/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.world.elements.characters;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;
import com.jme3.animation.SkeletonControl;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.PhysicsControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.preikestolen.Game;
import com.preikestolen.net.msg.game.MsgAccion;
import com.preikestolen.world.elements.controllers.interfaces.INetPosSync;
import com.preikestolen.persist.dao.JugadorDAO;
import com.preikestolen.persist.dao.PosicionDAO;
import com.preikestolen.world.MundoClient;
import com.preikestolen.world.colisions.ICollisionListener;
import com.preikestolen.world.elements.characters.tools.Tool;
import com.preikestolen.world.elements.controllers.NetSyncController;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Edu
 */
public class PlayerOld extends DirectionalCharacter implements INetPosSync{ 
    private float v=20;
    private JugadorDAO jugador;
    
    private BetterCharacterControl body;
    private Geometry g;
    private Tool tool;
    private SkeletonControl skeleton;
    
    public Vector3f walk=new Vector3f();
    
    public boolean arriba;
    public boolean abajo;
    public boolean izq;
    public boolean der;
    
    public boolean accion=false;
    public boolean accionDone=false;
    
    public MundoClient mundo;
    
    
    public PlayerOld(JugadorDAO jugador, MundoClient mundo){
        super("NodePlayer_"+jugador.getNombre());
        this.jugador=jugador;
        this.mundo=mundo;
    }
    
    public void iniciar(Game game, MundoClient mundo){
        Node n=(Node)game.getAssetManager().loadModel("Models/humanos/chica.j3o");
        g=(Geometry)n.getChild("unidadGeo1");

        move(1,25,1);
        
        gNode.attachChild(n);

        mundo.escena.attachChild(this);
        
        body=new BetterCharacterControl(1f, 3, 50);
                
       // body.setGravity(new Vector3f(0,-100,0));
        addControl(body);
        addControl(new NetSyncController(mundo));
        mundo.getApp().getFisicas().add(body);
       // mundo.getApp().getNetSync().addSyncPos(this);
       // posicion.attachChild(g);
        
        
        skeleton = n.getChild(0).getControl(SkeletonControl.class);
       
        AnimControl anim=n.getChild(0).getControl(AnimControl.class);
        chanel=anim.createChannel(); 
        anim.addListener(this);
        
       // body.setPhysicsDamping(0);
        rotar(0);
       
        
       // setTool(new Tool(game), game);
    }
    
    
    public void setTool(Tool tool, Game g){
        this.tool=tool;
        skeleton.getAttachmentsNode("mano_L").attachChild(tool);
  //      tool.attach(g, this);
    }
    
    public void onArriba(boolean valor){
        arriba=valor;
    }
    
    public void onAbajo(boolean valor){
        abajo=valor;
    }
    
    public void onIzquierda(boolean valor){
        izq=valor;
    }
    
    public void onDerecha(boolean valor){
        der=valor;
    }
    
    public void  update(Camera cam, float tpf){
        if(!accion){
            walk.set(0, 0, 0);

            Vector3f modelLeftDir = cam.getRotation().mult(Vector3f.UNIT_X);
            Vector3f modelForwardDir = cam.getRotation().mult(new Vector3f(0,1,1));


            if (izq) {
                walk.addLocal(modelLeftDir.mult(v));  
            } else if (der) {
                walk.addLocal(modelLeftDir.negate().multLocal(v));
            }

            if (arriba) {
                walk.addLocal(modelForwardDir.mult(v));
            } else if (abajo) {
                walk.addLocal(modelForwardDir.negate().multLocal(v));
            }

            if(walk.x==0 && walk.z==0){       
                setAnim("idle1", true);
                actualizarDireccion(getDireccion(), tpf);
            }else{                
                actualizarDireccion(walk, tpf);
                setAnim("cammina", true);
                mundo.terreno.onMover(getWorldTranslation());                
            }

            body.setWalkDirection(walk);
        }
    }

    public String getID() {
        return jugador.getNombre();
    }

    public PosicionDAO getPosicion() {
        jugador.getPosicion().setPosition(getWorldTranslation());
        jugador.getPosicion().setVelocity(body.getVelocity());
        jugador.getPosicion().setDirection(getDireccion());

        return jugador.getPosicion();
    }

    @Override
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
        if("accion1".equals(animName)){                        
            for(ICollisionListener c:colisiones){
                c.onColision();
            }
            colisiones.clear();
            accion=false;
        }
    }

    @Override
    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
        
    }
    
    public void doAccion(MundoClient m){
        if(!accion){
            accionDone=false;
            accion=true;
            setAnim("accion1", false);          
            
            m.getApp().getNet().send(new MsgAccion(m.player.getID(), "accion1"));
        }
    }
    
    public boolean isAccion(){
        return accion;
    }
    
    private List<ICollisionListener> colisiones=new ArrayList<ICollisionListener>();
    public void addColision(ICollisionListener c){
        if(accion && !colisiones.contains(c))
            colisiones.add(c);
    }

    @Override
    public PhysicsControl getBody(Node n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initPlayer(Node n, Game g) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
