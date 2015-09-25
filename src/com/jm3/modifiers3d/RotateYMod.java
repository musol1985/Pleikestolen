/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jm3.modifiers3d;

import com.jm3.Modifier3D;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;

/**
 *
 * @author Edu
 */
public class RotateYMod extends Modifier3D{

    public RotateYMod(float from, float to, float t){
        super(from, to, t);
    }
    
    public RotateYMod(float from, float to, float t, Modifier3DListener listener){
        super(from, to, t, listener);
    }
    
    public RotateYMod(float from, float to, float t, boolean loop){
        super(from, to, t, null,loop, null);
    }

    @Override
    public void onUpdate(float value, float delta) {          
        //e.setLocalRotation(e.getLocalRotation().set(e.getLocalRotation().getX(), delta, e.getLocalRotation().getZ(), e.getLocalRotation().getW()));
       // e.setLocalRotation(e.getLocalRotation().mult(new Quaternion().fromAngles(delta, 0, 0)));
        float[] ang=new float[3];
        e.getLocalRotation().toAngles(ang);
        e.setLocalRotation(new Quaternion().fromAngles(0, delta, 0));
    }
}
