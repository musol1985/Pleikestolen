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
public class TimeMod extends Modifier3D{
    
    public TimeMod(float t, Modifier3DListener listener){
        super(0, 100, t, listener);
    }

    @Override
    public void onUpdate(float value, float delta) {
            
    }
}
