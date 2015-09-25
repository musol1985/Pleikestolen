/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.world.elements.terreno.elementos.estaticos;

import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.CompoundCollisionShape;
import com.jme3.math.Vector3f;
import com.preikestolen.persist.dao.EstaticoDAO;
import com.preikestolen.world.MundoClient;
import com.preikestolen.world.colisions.ICollisionListener;
import com.preikestolen.world.elements.terreno.Celda;
import com.preikestolen.world.elements.terreno.elementos.Estatico;

/**
 *
 * @author Edu
 */
public class Roca extends Estatico implements ICollisionListener{

    public Roca(EstaticoDAO dao, MundoClient mundo) {
        super(dao, mundo);
    }
    
    @Override
    public String getModel(int subtipo) {
        return "Models/rocas/roca"+subtipo+".j3o";
    }

    @Override
    protected CollisionShape getCollisionShape(int subtipo) {
        return new BoxCollisionShape(new Vector3f(2, 10,2));        
    }
 
    public void addCollision(CompoundCollisionShape colisiones){
        colisiones.addChildShape(collision, dao.pos.add(0, 2, 0));
    }

    @Override
    public void onColision() {
        System.out.println("Colision!!!!");
        onAttack(25);
    }
}
