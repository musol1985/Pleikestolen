/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jm3.modifiers3d;

import com.jm3.ControlAdapter;
import com.jm3.Modifier3D;
import com.jme3.scene.Spatial;

/**
 *
 * @author Edu
 */
public class ParallelAnim extends ControlAdapter implements Modifier3D.Modifier3DListener{
    private Modifier3D[] mods;
    private float time;
    
    public ParallelAnim( float time, Modifier3D... mods){
        this.mods=mods;
        this.time=time;
        mods[mods.length-1].setListener(this);               
    }

    @Override
    public void setSpatial(Spatial spatial) {
        for(Modifier3D m:mods){
            m.setTime(time);
            m.setSpatial(spatial);
        }
    }

    public void update(float tpf) {
        for(Modifier3D m:mods){
            m.update(tpf);
        }
    }

    public void onFinish(Modifier3D m, Spatial e) {
        e.removeControl(ParallelAnim.class);
    }
}
