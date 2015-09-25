/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.world.colisions.resolvers;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.preikestolen.world.colisions.CollisionResolver;
import com.preikestolen.world.elements.characters.tools.Tool;

/**
 *
 * @author Edu
 */
public class ToolResolver extends CollisionResolver<Tool>{

    public ToolResolver() {
        super(Tool.class);
    }

    @Override
    public Node getParent(Spatial n) {
        if(n!=null && n.getParent()!=null)
            return n.getParent().getParent();
        return (Node)n;
    }
}
