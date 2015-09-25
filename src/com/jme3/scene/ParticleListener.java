/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3.scene;

import com.jme3.effect.ParticleEmitter;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.Control;
import java.io.IOException;

/**
 *
 * @author Edu
 */
public class ParticleListener implements Control {
    private ParticleEmitter emiter;
    @Override
    public Control cloneForSpatial(Spatial spatial) {
        return new ParticleListener();
    }

    @Override
    public void setSpatial(Spatial spatial) {
        this.emiter=(ParticleEmitter)spatial;
    }

    @Override
    public void update(float tpf) {        
        if(emiter.getNumVisibleParticles()==0){
            emiter.getParent().detachChild(emiter);
        }
    }

    @Override
    public void render(RenderManager rm, ViewPort vp) {
        
    }

    @Override
    public void write(JmeExporter ex) throws IOException {
        
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        
    }
    
}
