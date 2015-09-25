/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.world.elements.characters;

import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.GhostControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.preikestolen.Game;
import com.preikestolen.net.msg.game.MsgCogerDinamico;
import com.preikestolen.net.msg.game.MsgSetTool;
import com.preikestolen.persist.dao.CosaDAO;
import com.preikestolen.world.elements.controllers.interfaces.INetPosSync;
import com.preikestolen.persist.dao.JugadorDAO;
import com.preikestolen.persist.dao.PilaCosasDAO;
import com.preikestolen.persist.dao.PosicionDAO;
import com.preikestolen.world.MundoClient;
import com.preikestolen.world.colisions.ICollisionListener;
import com.preikestolen.world.elements.characters.tools.Axe;
import com.preikestolen.world.elements.characters.tools.IAction;
import com.preikestolen.world.elements.characters.tools.Tool;
import com.preikestolen.world.elements.characters.tools.Torch;
import com.preikestolen.world.elements.controllers.NetSyncController;
import com.preikestolen.world.elements.terreno.elementos.Dinamico;
import com.preikestolen.world.elements.terreno.elementos.Estatico;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Edu
 */
public class Player extends PlayerCommon<BetterCharacterControl> implements INetPosSync{ 

    private List<ICollisionListener> colisiones=new ArrayList<ICollisionListener>();
    public Vector3f walk=new Vector3f();
    
    public boolean arriba;
    public boolean abajo;
    public boolean izq;
    public boolean der;

    private GhostControl ghost;
    
    private Estatico autoDestino;
    private boolean auto;
    
    public Player(JugadorDAO jugador, MundoClient mundo){
        super("NodePlayer_"+jugador.getNombre(), jugador, mundo);        
    }

    @Override
    public void initPlayer(Node n, Game g) {
        super.initPlayer(n, g); //To change body of generated methods, choose Tools | Templates.
        //setTool(new Torch(g, true), g);
        //setTool(new Axe(g, true), g);
        
        addControl(new NetSyncController(mundo));
        
        ghost=new GhostControl(new SphereCollisionShape(15));
        
        addControl(ghost);
    }
    
    public void onArriba(boolean valor){
        arriba=valor;
        autoDestino=null;
    }
    
    public void onAbajo(boolean valor){
        abajo=valor;
        autoDestino=null;
    }
    
    public void onIzquierda(boolean valor){
        izq=valor;
        autoDestino=null;
    }
    
    public void onDerecha(boolean valor){
        der=valor;
        autoDestino=null;
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
            
            if(autoDestino!=null){
                walk=getDireccionAutoDestino();
            }
            
            
            if(animAndar(walk)){
                actualizarDireccion(walk, tpf);
                mundo.terreno.onMover(getWorldTranslation());   
            }else{
                actualizarDireccion(getDireccion(), tpf);
            }

            body.setWalkDirection(walk);
        }
    }
    
    public void parar(){
        walk.set(0, 0, 0);
        body.setWalkDirection(walk);
    }

    @Override
    public String getID() {
        return jugador.getNombre();
    }

    @Override
    public PosicionDAO getPosicion() {
        jugador.getPosicion().setPosition(getWorldTranslation());
        jugador.getPosicion().setVelocity(body.getVelocity());
        jugador.getPosicion().setDirection(getDireccion());

        return jugador.getPosicion();
    }

    /*public void addColision(ICollisionListener c){
        if(accion && !colisiones.contains(c))
            colisiones.add(c);
    }*/

    @Override
    public void onAccionDone(String accion) {
        /*for(ICollisionListener c:colisiones){
            c.onColision();
        }
        colisiones.clear();*/
        if(tool!=null && tool instanceof IAction){
            for(PhysicsCollisionObject obj:((IAction)tool).getColisiones()){
                if(obj.getUserObject() instanceof ICollisionListener){
                    ((ICollisionListener)obj.getUserObject()).onColision();
                }
            }
        }
        

        if(isAutoDestino()){             
            if(!autoDestino.isMuerto()){
                animAndar(Vector3f.ZERO);
                doAccionNet(accion, mundo);
            }else if(autoDestino instanceof Dinamico){
                System.out.println("remove dinamico"+autoDestino);
                mundo.getApp().getNet().send(new MsgCogerDinamico(((Dinamico)autoDestino).getCelda().getDao().getID(), autoDestino.getDAO().id, mundo.player.getID()));
                autoDestino.getCelda().removeDinamico((Dinamico)autoDestino);
                
                jugador.addCosa(autoDestino.dao, mundo.getGUI());
                
                if(auto){
                   autoDestino=null;
                    doControl(true); 
                }                
            }
        }
    }

    @Override
    public BetterCharacterControl getBody(Node n) {
        return new BetterCharacterControl(1f, 3, 50);
    }
    
    public void doControl(boolean value){
        if(value){
            auto=true;
            Estatico cercano=getCercano();

            if(cercano!=autoDestino){
                System.out.println("On cambio autoDestino: "+cercano+" old->"+autoDestino);
                autoDestino=cercano;            
            }      
        }else{
            auto=false;
            autoDestino=null;
        }
    }
    
    public Estatico getCercano(){
        Estatico obj=null;

        for(PhysicsCollisionObject b:ghost.getOverlappingObjects()){
            if(b.getUserObject() instanceof Estatico){
                if(obj==null || (getObjectDistance((Estatico)b.getUserObject())<getObjectDistance(obj))){
                    obj=(Estatico)b.getUserObject();
                }
            }
        }
        
        return obj;
    }
    
    private float getObjectDistance(Estatico e){
        return e.getWorldTranslation().distance(getWorldTranslation());
    }
    
    private Vector3f getDireccionAutoDestino(){
        return autoDestino.getWorldTranslation().subtract(getWorldTranslation()).normalize().mult(v);
    }
    
    public boolean isAutoDestino(){
        return autoDestino!=null;
    }
    
    public void onRemoverEstatico(Estatico e, List<Dinamico> dinamicos){
        if(e==autoDestino){
            autoDestino=null;
        }
        
        if(auto){
            Dinamico res=null;
            for(Dinamico d:dinamicos){
                if(res==null || (d.getWorldTranslation().distance(getWorldTranslation())<res.getWorldTranslation().distance(getWorldTranslation()))){
                    res=d;
                }
            }
            
            if(res!=null){
                autoDestino=res;
            }else{
                doControl(true);
            }
        }
    }
    
    
    public void onKey(int key){
       /*switch(key){
            case 1:
                cambiarTool(new Torch(mundo.getApp(), true));
            break;
            case 2:
                cambiarTool(new Axe(mundo.getApp(), true));
            break;
        }*/
    }    
    
  
    
    public void debug(){
        removeControl(body);
        body=getBody(this);       

        addControl(body);
    }
    
    public void cambiarTool(CosaDAO tool){
        super.cambiarTool(tool);
        
        mundo.getApp().getNet().send(new MsgSetTool(getDAO().getNombre(), tool.tipo.ordinal()));
    }
        
}
