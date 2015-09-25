/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jm3.modifiers3d;

import com.jm3.Modifier3D;
import com.jme3.scene.Spatial;

/**
 *
 * @author Edu
 */
public class SizeLoopMod{
    private SizeMod in;
    private SizeMod out;
    private SizeMod current;
    
    public SizeLoopMod(Spatial s, float from, float to, float t){
        in=new SizeMod(from, to, t/2, new Modifier3D.Modifier3DListener() {
            public void onFinish(Modifier3D m, Spatial e) {
                e.removeControl(in);
                out.reset();                
                e.addControl(out);
                current=out;
            }
        });
        out=new SizeMod(to, from, t/2, new Modifier3D.Modifier3DListener() {
            public void onFinish(Modifier3D m, Spatial e) {
                e.removeControl(out);
                in.reset();                
                e.addControl(in);
                current=in;
            }
        });
        current=in;
        
        s.addControl(current);
    }

    public SizeMod getCurrent(){
        return current;
    }
    
}
