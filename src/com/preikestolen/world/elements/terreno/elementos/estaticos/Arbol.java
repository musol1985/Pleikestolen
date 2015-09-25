/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.world.elements.terreno.elementos.estaticos;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.CompoundCollisionShape;
import com.jme3.bullet.collision.shapes.ConeCollisionShape;
import com.jme3.bullet.collision.shapes.CylinderCollisionShape;
import com.jme3.math.Vector3f;
import com.preikestolen.persist.dao.EstaticoDAO;
import com.preikestolen.world.MundoClient;
import com.preikestolen.world.colisions.ICollisionListener;
import com.preikestolen.world.elements.terreno.elementos.Estatico;

/**
 *
 * @author Edu
 */
public class Arbol extends Estatico implements ICollisionListener{

    public Arbol(EstaticoDAO dao, MundoClient mundo) {
        super(dao, mundo);
    }
    
    @Override
    public String getModel(int subtipo) {
        return "Models/arboles/pino"+subtipo+".j3o";
    }

    @Override
    protected CollisionShape getCollisionShape(int subtipo) {
        return new CylinderCollisionShape(new Vector3f(2,8,2), 1);        
    }
 
    public void addCollision(CompoundCollisionShape colisiones, Vector3f worldPos){
        colisiones.addChildShape(collision, dao.pos.add(0, 2, 0).add(worldPos));
    }

    @Override
    public void onColision() {
        System.out.println("ColisionArbol!!!!"+this);
        onAttack(20);
    }
}
