/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jm3.modifiers3d;

import com.jm3.Modifier3D;
import com.jme3.font.BitmapText;

/**
 *
 * @author Edu
 */
public class TextMod extends Modifier3D{
    public TextModUpdate r;
    private String oldText="";
    
    public TextMod(float from, float to, float t){
        super(from, to, t);
    }
    
    public TextMod(float from, float to, float t, Modifier3DListener listener){
        super(from, to, t, listener);
    }

    @Override
    public void onUpdate(float value, float delta) {
        ((BitmapText)e).setText(String.valueOf((int)value));
        if(r!=null)
            r.onUpdate(oldText);
        oldText=((BitmapText)e).getText();
    }
    
    public interface TextModUpdate{
        public void onUpdate(String oldText);
    }
}
