/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.world.elements.characters.tools;

import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.scene.Node;
import com.preikestolen.Game;
import com.preikestolen.persist.dao.CosaDAO;
import java.util.List;

/**
 *
 * @author Edu
 */
public class Axe extends Tool implements IAction{

    public Axe(Game g, boolean local, CosaDAO dao) {
        super(g, local, getNode(g), false, dao);
    }
    
    private static Node getNode(Game g){
        return (Node)((Node)g.getAssetManager().loadModel("Models/tools/axe.j3o")).getChild("Axe_Tomahawk");
    }

    @Override
    protected void onAttached(Game g) {
   
    }

    @Override
    protected void onDettached(Game g) {
        
    }

    @Override
    public List<PhysicsCollisionObject> getColisiones() {
        return ghost.getOverlappingObjects();
    }

}
