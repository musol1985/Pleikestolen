/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jm3.modifiers3d;

import com.jm3.Modifier3D;
import com.jme3.math.FastMath;

/**
 *
 * @author Edu
 */
public class RotateXMod extends Modifier3D{

    public RotateXMod(float from, float to, float t){
        super(from, to, t);
    }
    
    public RotateXMod(float from, float to, float t, Modifier3DListener listener){
        super(from, to, t, listener);
    }

    @Override
    public void onUpdate(float value, float delta) {    
        e.setLocalRotation(e.getLocalRotation().set(e.getLocalRotation().getX(), e.getLocalRotation().getY(), delta, e.getLocalRotation().getW()));
       // e.setLocalRotation(e.getLocalRotation().mult(new Quaternion().fromAngles(delta, 0, 0)));
    }
}
