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
public class MoveZMod extends Modifier3D{

    public MoveZMod(float from, float to, float t){
        super(from, to, t);
    }
    
    public MoveZMod(float from, float to, float t, Modifier3DListener listener){
        super(from, to, t, listener);
    }

    @Override
    public void onUpdate(float value, float delta) {
        e.setLocalTranslation(e.getLocalTranslation().set(e.getLocalTranslation().x,e.getLocalTranslation().y,delta));
    }
}
