/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.world.elements.terreno.elementos.dinamicos;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.CompoundCollisionShape;
import com.jme3.bullet.collision.shapes.CylinderCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.preikestolen.persist.dao.DinamicoDAO;
import com.preikestolen.world.MundoClient;
import com.preikestolen.world.colisions.ICollisionListener;
import com.preikestolen.world.elements.terreno.elementos.Dinamico;

/**
 *
 * @author Edu
 */
public class Tronco extends Dinamico implements ICollisionListener{

    public Tronco(DinamicoDAO dao, MundoClient mundo) {
        super(dao, mundo);
    }
    
    @Override
    public String getModel(int subtipo) {
        return "Models/materiales/tronco.j3o";
    }

    @Override
    protected CollisionShape getCollisionShape(int subtipo) {
        return new CylinderCollisionShape(new Vector3f(0.4f,0.8f,0.4f), 1);
    }
 
    public void addCollision(CompoundCollisionShape colisiones){
        colisiones.addChildShape(collision, dao.pos.add(0, 2, 0));
    }

    @Override
    public void onColision() {
        System.out.println("Colision tronco!!!!");
        //onAttack(25);
    }
    
    public float getScale(){
        return 1f;
    }

    @Override
    public float getMasa() {
        return 20f; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onBodyCreated(RigidBodyControl body) {
        body.setSleepingThresholds(10, 10);
    }
    
    
    
}
