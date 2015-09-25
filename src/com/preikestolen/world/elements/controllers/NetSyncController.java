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
import com.preikestolen.net.msg.game.MsgPosition;
import com.preikestolen.world.elements.controllers.interfaces.INetPosSync;
import com.preikestolen.world.MundoClient;
import java.io.IOException;

/**
 *
 * @author Edu
 */
public class NetSyncController implements Control{
    public static final int TIMEOUT=10;
    
    private INetPosSync spatial;
    private MundoClient mundo;
    private long time;
    
    public NetSyncController(MundoClient mundo){
        this.mundo=mundo;
    }
    
    
    public Control cloneForSpatial(Spatial spatial) {
        return new NetSyncController(mundo);
    }

    public void setSpatial(Spatial spatial) {
        this.spatial=(INetPosSync)spatial;
    }

    public void update(float tpf) {
        if(System.currentTimeMillis()-time>TIMEOUT){
            
            mundo.app.getNet().send(new MsgPosition(spatial.getID(), spatial.getPosicion()));
            
            time=System.currentTimeMillis();
        }
    }

    public void render(RenderManager rm, ViewPort vp) {
        
    }

    public void write(JmeExporter ex) throws IOException {
        
    }

    public void read(JmeImporter im) throws IOException {
        
    }
    
}
