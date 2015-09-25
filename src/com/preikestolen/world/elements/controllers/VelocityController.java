/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.world.elements.controllers;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import com.preikestolen.world.elements.controllers.interfaces.IVelocity;
import java.io.IOException;

/**
 *
 * @author Edu
 */
public class VelocityController implements Control{
    private IVelocity v;

    public Control cloneForSpatial(Spatial spatial) {
        return new VelocityController();
    }

    public void setSpatial(Spatial spatial) {
        this.v=(IVelocity)spatial;
    }

    public void update(float tpf) {
        v.updateVelocity(tpf);
    }

    public void render(RenderManager rm, ViewPort vp) {
        
    }

    public void write(JmeExporter ex) throws IOException {
        
    }

    public void read(JmeImporter im) throws IOException {
        
    }
    
}
