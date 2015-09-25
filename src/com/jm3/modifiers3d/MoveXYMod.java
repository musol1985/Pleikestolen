/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jm3.modifiers3d;

import com.jm3.Modifier2Values3D;
import com.jme3.math.Vector3f;

/**
 *
 * @author Edu
 */
public class MoveXYMod extends Modifier2Values3D{
    
    public MoveXYMod(Vector3f from, Vector3f to, float t){
        super(from.x, to.x, from.y, to.y, t);
    }
    
    public MoveXYMod(Vector3f from, Vector3f to, float t, Modifier2Values3DListener listener){
        super(from.x, to.x, from.y, to.y, t, listener);
    }

    public MoveXYMod(float xfrom, float xto, float yfrom, float yto, float t){
        super(xfrom, xto, yfrom, yto, t);
    }
    
    public MoveXYMod(float xfrom, float xto, float yfrom, float yto,  float t, Modifier2Values3DListener listener){
        super(xfrom, xto, yfrom, yto, t, listener);
    }

    @Override
    public void onUpdate(float v, float d, float v2, float d2) {
        e.setLocalTranslation(e.getLocalTranslation().set(d,d2,e.getLocalTranslation().z));
    }
}
