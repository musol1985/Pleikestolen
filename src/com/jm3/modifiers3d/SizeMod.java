/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jm3.modifiers3d;

import com.jm3.Modifier3D;

/**
 *
 * @author Edu
 */
public class SizeMod extends Modifier3D{

    public SizeMod(float from, float to) {
        super(from, to);
    }

    public SizeMod(float from, float to, float t){
        super(from, to, t);
    }
    
    public SizeMod(float from, float to, float t, Modifier3DListener listener){
        super(from, to, t, listener);
    }

    @Override
    public void onUpdate(float value, float delta) {
        e.setLocalScale(e.getLocalScale().set(delta, delta, 0));        
    }
}
