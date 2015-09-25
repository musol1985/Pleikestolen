/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.world.elements.terreno.elementos;

import com.jme3.effect.ParticleEmitter;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.ParticleListener;
import com.preikestolen.persist.dao.DinamicoDAO;
import com.preikestolen.persist.dao.PosicionRotacionDAO;
import com.preikestolen.world.MundoClient;
import com.preikestolen.world.colisions.ICollisionListener;
import com.preikestolen.world.elements.Terreno;
import com.preikestolen.world.elements.controllers.DinamicoController;
import com.preikestolen.world.elements.terreno.Celda;

/**
 *
 * @author Edu
 */
public abstract class Dinamico extends Estatico<DinamicoDAO> implements ICollisionListener{
    private long timeout;
    private boolean controloYo;    
    
    public Dinamico(DinamicoDAO dao, MundoClient mundo){
        super(dao, mundo);    
        vida=0;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public boolean isControloYo() {
        return controloYo;
    }

    public void setControloYo(boolean controloYo) {
        this.controloYo = controloYo;
    }
    
    public Vector3f getPosition(){        
        return super.body.getPhysicsLocation();
    }
    
    public void updatePosicion(Celda c, PosicionRotacionDAO pos){        
        dao.posicion=pos;
        c.getDao().change();
        
        //setLocalTranslation(pos);
        body.setPhysicsLocation(pos.getPosition());
        body.setLinearVelocity(pos.getVelocity());              
        
        body.setPhysicsRotation(new Quaternion().fromAngles(pos.getRotacion().x, pos.getRotacion().y, pos.getRotacion().z));
        
        if(isControloYo())
            descontrolar();
    }
    
    public void controlar(Celda c){
        controlar(c.getTerreno().getM());
        
    }
    
    public void controlar(MundoClient m){
        controlar(m, m.getTime());
    }
    
    public void controlar(MundoClient m, long time){
        if(!isControloYo()){
            setControloYo(true);
            setTimeout(time);

            addControl(new DinamicoController(m));
        }
    }

    
    public void descontrolar(){
        setControloYo(false);
        removeControl(DinamicoController.class);
    }
    
    
    @Override
    public void remove(Terreno t, Celda c){
        descontrolar();
        removeFisicas(t);
    }
}
