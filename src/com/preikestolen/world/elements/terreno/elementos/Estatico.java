/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.world.elements.terreno.elementos;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.effect.ParticleEmitter;
import com.jme3.scene.Node;
import com.jme3.scene.ParticleListener;
import com.jme3.scene.Spatial;
import com.preikestolen.net.msg.game.MsgMatarEstatico;
import com.preikestolen.persist.dao.EstaticoDAO;
import com.preikestolen.world.MundoClient;
import com.preikestolen.world.elements.Terreno;
import com.preikestolen.world.elements.terreno.Celda;

/**
 *
 * @author Edu
 */
public abstract class Estatico<T extends EstaticoDAO> extends Node{
    public static final float SCALE=0.05f;
    public T dao;    
    public CollisionShape collision;
    public RigidBodyControl body;
    public boolean added;
    
    protected int vida=100;
    
    public Estatico(T dao, MundoClient mundo){
        super(dao.id);
        this.dao=dao;
        
        Node node=(Node)mundo.getApp().getAssetManager().loadModel(getModel(dao.subtipo));
        Spatial g=node;
        do{
            g=((Node)g).getChild(0);
        }while(g instanceof Node);
        
        g.setLocalScale(getScale());
        
        attachChild(g);
        
        collision=getCollisionShape(dao.subtipo);
        

        move(dao.pos.x, dao.pos.y, dao.pos.z);      
        rotate(0, dao.ang, 0);
        
       
      //  body.setPhysicsLocation(node.getWorldTranslation());        
    }
    
    public abstract String getModel(int subtipo);
    protected abstract CollisionShape getCollisionShape(int subtipo);
    public float getScale(){
        return SCALE;
    }
    
    public float getMasa(){
        return 0;
    }
    
    public void onBodyCreated(RigidBodyControl body){
        
    }
    
    public void addFisicas(Terreno t){
        if(getControl(RigidBodyControl.class)==null){
             body=new RigidBodyControl(collision, getMasa());             
             onBodyCreated(body);
             addControl(body);
        }
        if(!added){            
            t.getFisicas().add(body);
            added=true;
        }
    }
    
    public void removeFisicas(Terreno t){
        if(added){
            t.getFisicas().remove(body);
            added=false;
        }
    }
    
    public synchronized boolean onAttack(int v){
        if(vida>0){
            vida-=v;
            if(vida<=0){
                onMuerte();
                return true;
            }
        }
        
        return false;
    }
    
    public Celda getCelda(){
        return (Celda)getParent().getParent();
    }
    
    public void onMuerte(){
        //System.out.println("OnMuerte!!! "+getName()+" "+System.currentTimeMillis()+" "+Thread.currentThread());
        getCelda().getTerreno().getM().getApp().getNet().send(new MsgMatarEstatico(getCelda().getDao().getID(), dao.id));
        //getCelda().onRemoveEstatico(this);
    }
    
    public void remove(Terreno t, Celda c){
        removeFisicas(t);
        
        ParticleEmitter humo=(ParticleEmitter)((Node)t.getM().app.getAssetManager().loadModel("Models/fx/Humo.j3o")).getChild(0);
        
        t.getM().app.getRootNode().attachChild(humo);
        humo.setLocalTranslation(getWorldTranslation().add(0, 2, 0));        
        t.getM().escena.attachChild(humo);
        
        humo.emitAllParticles();
        humo.setParticlesPerSec(0);
        humo.addControl(new ParticleListener());
    }
    
    public T getDAO(){
        return dao;
    }
    
    public boolean isMuerto(){
        return vida<=0;
    }
}
