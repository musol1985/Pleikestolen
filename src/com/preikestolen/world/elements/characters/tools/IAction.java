/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.world.elements.characters.tools;

import com.jme3.bullet.collision.PhysicsCollisionObject;
import java.util.List;

/**
 *
 * @author Edu
 */
public interface IAction {
    public List<PhysicsCollisionObject> getColisiones();
}
