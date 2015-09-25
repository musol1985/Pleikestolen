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
import com.preikestolen.net.msg.game.MsgDinamicoPosition;
import com.preikestolen.net.msg.game.MsgDinamicoSleep;
import com.preikestolen.persist.dao.PosicionDAO;
import com.preikestolen.world.MundoClient;
import com.preikestolen.world.elements.terreno.elementos.Dinamico;
import java.io.IOException;

/**
 *
 * @author Edu
 */
public class DinamicoController implements Control{
    public static final int TIMEOUT=20;
    
    private Dinamico spatial;
    private MundoClient mundo;
    private long time;
    
    public DinamicoController(MundoClient mundo){
        this.mundo=mundo;
    }
    
    
    public Control cloneForSpatial(Spatial spatial) {
        return new DinamicoController(mundo);
    }

    public void setSpatial(Spatial spatial) {
        this.spatial=(Dinamico)spatial;
    }
    
    private float[] angs=new float[3];

    public void update(float tpf) {
        if(System.currentTimeMillis()-time>TIMEOUT){
            if(spatial.body.isActive()){
                spatial.dao.posicion.setPosition(spatial.getPosition());
                spatial.dao.posicion.setVelocity(spatial.body.getLinearVelocity());

                spatial.body.getPhysicsRotation().toAngles(angs);
                spatial.dao.posicion.setRotacion(angs[0], angs[1], angs[2]);

                mundo.app.getNet().send(new MsgDinamicoPosition(spatial.getCelda().getDao().getID(), spatial.getDAO().id, time, spatial.dao.posicion));

                time=System.currentTimeMillis();
            }else{
                System.out.println("On remove control dinamico "+spatial.getName());
                mundo.app.getNet().send(new MsgDinamicoSleep(spatial.getCelda().getDao().getID(), spatial.getDAO().id, time, spatial.dao.posicion));
                spatial.descontrolar();                
            }
        }
    }

    public void render(RenderManager rm, ViewPort vp) {
        
    }

    public void write(JmeExporter ex) throws IOException {
        
    }

    public void read(JmeImporter im) throws IOException {
        
    }
    
}
