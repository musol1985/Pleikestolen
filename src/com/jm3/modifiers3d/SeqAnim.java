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
public class SeqAnim extends ControlAdapter implements Modifier3D.Modifier3DListener{
    private Modifier3D[] mods;
    private boolean loop;
    private int current=0;
    
    public SeqAnim( boolean loop, Modifier3D... mods){
        this.mods=mods;
        this.loop=loop;             
    }

    @Override
    public void setSpatial(Spatial spatial) {
        for(Modifier3D m:mods){
            m.setListener(this);
            m.setSpatial(spatial);
        }
    }

    public void update(float tpf) {
        mods[current].update(tpf);
    }

    public void onFinish(Modifier3D m, Spatial e) {
        if(current<mods.length-1){
            current++;
        }else if(loop){
            for(Modifier3D mod:mods){
                mod.reset();
            }
            current=0;
        }else{
            e.removeControl(SeqAnim.class);
        }
    }
}
