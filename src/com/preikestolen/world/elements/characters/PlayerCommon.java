/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.world.elements.characters;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.SkeletonControl;
import com.jme3.bullet.control.PhysicsControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.preikestolen.Game;
import com.preikestolen.net.msg.game.MsgAccion;
import com.preikestolen.persist.dao.CosaDAO;
import com.preikestolen.persist.dao.JugadorDAO;
import com.preikestolen.world.MundoClient;
import com.preikestolen.world.elements.characters.tools.Tool;

/**
 *
 * @author Edu
 */
public abstract class PlayerCommon<T extends PhysicsControl> extends DirectionalCharacter<T>{ 
    protected JugadorDAO jugador;
    
    protected Tool tool;
    protected SkeletonControl skeleton;
    
    protected MundoClient mundo;
    
    protected boolean accion=false;
    
    
    
    public PlayerCommon(String name, JugadorDAO jugador, MundoClient mundo){
        super(name+jugador.getNombre());
        this.jugador=jugador;
        this.mundo=mundo;
    }
    

    public void iniciar(Game game, MundoClient mundo){
        super.iniciar(game, mundo, "Models/humanos/chica.j3o", jugador.getPosicion().getPosition());
    }
    
    public JugadorDAO getDAO(){
        return jugador;
    }

    @Override
    public void initPlayer(Node n, Game g){
        skeleton = n.getChild(0).getControl(SkeletonControl.class);
    }
    
    
    public void setTool(Tool tool, Game g){
        this.tool=tool;
        if(tool!=null){
            skeleton.getAttachmentsNode("mano_L").attachChild(tool);
            tool.attach(g, this);
        }
    }
    
    protected boolean animAndar(Vector3f posicion){
        if(!accion){
            if(isZero(posicion)){       
                setAnim("idle1", true);            
            }else{                            
                setAnim("cammina", true);            
                return true;
            }
        }  
        return false;
    }
    
        
    protected boolean isZero(float v){
        return v<0.001f && v>-0.001;
    }
    
    protected boolean isZero(Vector3f v){
        return isZero(v.x)&&isZero(v.z);
    }

    @Override
    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
        
    }
    
    @Override
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
        if(animName.startsWith("accion")){  
            accion=false;
            onAccionDone(animName);            
        }
    }
    
    public void doAccionNet(String name, MundoClient m){
        doAccion(name, m, true);
    }
    
    public void doAccion(String name, MundoClient m, boolean net){
        if(!accion){
            accion=true;
            setAnim(name, false);          
            
            if(net)
                m.getApp().getNet().send(new MsgAccion(m.player.getID(), name));
        }
    }
    
    public boolean isAccion(){
        return accion;
    }
    
    public abstract void onAccionDone(String accion);
    
    public void cambiarTool(CosaDAO tool){
        cambiarTool(Tool.getTool(mundo.getApp(), tool));
    }
    
    private void cambiarTool(Tool t){
        if(tool!=null){
            tool.dettach(mundo.getApp(), this);
        }
        
        setTool(t, mundo.getApp());
    }
}
