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
public class MoveYMod extends Modifier3D{

    public MoveYMod(float from, float to, float t){
        super(from, to, t);
    }
    
    public MoveYMod(float from, float to, float t, Modifier3DListener listener){
        super(from, to, t, listener);
    }
    
   public MoveYMod(float from, float to, float t, Modifier3DListener listener, Modifier3DListenerUpdate listenerUpdate){
        super(from, to, t, listener, false, listenerUpdate);
    }

    @Override
    public void onUpdate(float value, float delta) {
        e.setLocalTranslation(e.getLocalTranslation().set(e.getLocalTranslation().x,delta, e.getLocalTranslation().z));
    }
}
