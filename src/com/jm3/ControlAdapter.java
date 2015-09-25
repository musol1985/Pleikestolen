/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jm3;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import java.io.IOException;

/**
 *
 * @author Edu
 */
public abstract class ControlAdapter implements Control{

    public Control cloneForSpatial(Spatial spatial) {
       return null;
    }

    public void setSpatial(Spatial spatial) {

    }

    public void render(RenderManager rm, ViewPort vp) {

    }

    public void write(JmeExporter ex) throws IOException {

    }

    public void read(JmeImporter im) throws IOException {
        
    }
    
}
