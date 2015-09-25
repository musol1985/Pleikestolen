/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.world.elements.characters;

import com.jme3.bullet.control.AbstractPhysicsControl;
import com.jme3.bullet.control.PhysicsControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.preikestolen.net.msg.game.MsgPosition;
import com.preikestolen.persist.dao.JugadorDAO;
import com.preikestolen.persist.dao.PosicionDAO;
import com.preikestolen.world.MundoClient;
import com.preikestolen.world.elements.controllers.VelocityController;
import com.preikestolen.world.elements.controllers.interfaces.IVelocity;

/**
 *
 * @author Edu
 */
public abstract class CharacterSync<T extends PhysicsControl> extends PlayerCommon<T> implements IVelocity{

    protected PosicionDAO posicion;
    
    public CharacterSync(String name, MundoClient m, JugadorDAO j){
        super(name, j, m);
        this.posicion=j.getPosicion();

        addControl(new VelocityController());
    }
    
    
    public void sync(MsgPosition m){
        this.posicion=m.posicion;      

        setLocalTranslation(m.posicion.getPosition()); 
    }
    

    @Override
    public void updateVelocity(float tpf) {
        move(posicion.getVelocity().mult(tpf));
        super.actualizarDireccion(posicion.getDirection(), tpf);
        
        onMover();
    }
   
    public abstract void onMover();
}
