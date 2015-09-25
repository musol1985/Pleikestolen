/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jm3.modifiers3d;

import com.jm3.Modifier3D;
import com.jm3.Modifier3D;
import com.jm3.Modifier3D.Modifier3DListener;
import com.jm3.Modifier3D.Modifier3DListenerUpdate;

/**
 *
 * @author Edu
 */
public class MoveXMod extends Modifier3D{

    public MoveXMod(float from, float to, float t){
        super(from, to, t);
    }
    
    public MoveXMod(float from, float to, float t, Modifier3DListener listener){
        super(from, to, t, listener);
    }
    
    public MoveXMod(float from, float to, float t, Modifier3DListener listener, Modifier3DListenerUpdate listenerUpdate){
        super(from, to, t, listener, false, listenerUpdate);
    }

    @Override
    public void onUpdate(float value, float delta) {
        e.setLocalTranslation(e.getLocalTranslation().set(delta,e.getLocalTranslation().y,e.getLocalTranslation().z));
    }
}
