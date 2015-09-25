package com.jm3;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import java.io.IOException;

/**
 *
 * @author Edu
 */
public abstract class Modifier2Values3D  implements Control{
    private float time;
    private float v;    
    private float value;
    private float dist;
    private float from;
    
    private float dist2;
    private float from2;
    private float v2;
    private float value2;
    
    protected Modifier2Values3DListener listener;

    protected Spatial e;
    protected boolean finInicial;
    protected boolean loop;
    
    public Modifier2Values3D(float from, float to, float from2, float to2, float time){
        this(from, to, from2, to2, time, null);
    }
    
    public Modifier2Values3D(float from, float to, float from2, float to2, float time, Modifier2Values3DListener listener){
        this(from, to, from2, to2, time, listener, false);
    }
    
    public Modifier2Values3D(float from, float to, float from2, float to2, float time, Modifier2Values3DListener listener, boolean loop){
        this.loop=loop;
        this.from=from;
        
        this.from2=from2;
        dist2=to2-from2;
        v2=dist2/(time/1000);
        
        dist=to-from;
        v=dist/(time/1000);
        this.listener=listener;
        if(from==from2 && to==to2)
            finInicial=true;
    }
    
    public void setListener(Modifier2Values3DListener listener){
        this.listener=listener;
    }
            
    public void setSpatial(Spatial e){
        this.e=e;
    }
    
    public void setLoop(){
        loop=true;
    }        
    
    public void update(float tpf){
        if(finInicial){
            Spatial te=e;
            e.removeControl(this);
            if(listener!=null)
                listener.onFinish(this, te);
        }else{            
            time+=tpf;
            value=v*time;
            value2=v2*time;
            
            if(Math.abs(value)>Math.abs(dist)){
                value=dist;
                value2=dist2;
                onUpdate(value, from+value, value2, from2+value2);

                if(!loop){
                    Spatial te=e;
                    e.removeControl(this);
                    if(listener!=null)
                        listener.onFinish(this, te);
                }else{
                    time=0;
                }
            }else{
                onUpdate(value, from+value, value2, from2+value2);

            }
        }
    }
    
    public void reset(){
        time=0;
    }
    
    public abstract void onUpdate(float v, float d, float v2, float d2);
    
    public interface Modifier2Values3DListener{
        public void onFinish(Modifier2Values3D m, Spatial e);
    }
    
    public Control cloneForSpatial(Spatial spatial) {
        return null;
    }

    public void render(RenderManager rm, ViewPort vp) {
      
    }

    public void write(JmeExporter ex) throws IOException {
       
    }

    public void read(JmeImporter im) throws IOException {
 
    }

}
