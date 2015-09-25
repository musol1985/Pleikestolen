/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jm3.modifiers3d;


import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jm3.Modifier3D;
import com.jm3.Modifier3D.Modifier3DListener;
import com.jm3.Modifier3D.Modifier3DListenerUpdate;
import com.jme3.scene.Spatial;

/**
 *
 * @author Edu
 */
public class AlphaMod extends Modifier3D{
    private ColorRGBA color;
    
    public AlphaMod(float from, float to) {
        super(from, to);
        color=new ColorRGBA(1,1,1,from);
    }

    public AlphaMod(float from, float to, float t){
        super(from, to, t);
        color=new ColorRGBA(1,1,1,from);
    }
    
    public AlphaMod(float from, float to, float t, Modifier3DListener listener){
        super(from, to, t, listener);
        color=new ColorRGBA(1,1,1,from);
    }
    
    public AlphaMod(float from, float to, float t, Modifier3DListener listener, Modifier3DListenerUpdate listenerUpdate){
        super(from, to, t, listener, false, listenerUpdate);
        color=new ColorRGBA(1,1,1,from);
    }

    @Override
    public void setSpatial(Spatial e) {        
        
        super.setSpatial(e);
    }
    
    

    @Override
    public void onUpdate(float value, float delta) {
        if(e instanceof Geometry){
            System.out.println(value);
            color.a=value;
            ((Geometry)e).getMaterial().setColor("Color", color);
            
        }
    }
}
