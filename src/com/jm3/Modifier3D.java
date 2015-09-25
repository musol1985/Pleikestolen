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
public abstract class Modifier3D  implements Control{
    private float time;
    private float v;    
    private float value;
    private float dist;
    private float from;
    private Modifier3DListener listener;
    private Modifier3DListenerUpdate listenerUpdate;
    protected Spatial e;
    protected boolean finInicial;
    protected boolean loop;
    
    public Modifier3D(float from, float to){
        this(from, to, 1);
    }
    
    public Modifier3D(float from, float to, float time){
        this(from, to, time, null);
    }
    
    public Modifier3D(float from, float to, float time, Modifier3DListener listener){
        this(from, to, time, listener, false, null);
    }
    
    public Modifier3D(float from, float to, float time, Modifier3DListener listener, boolean loop, Modifier3DListenerUpdate listenerUpdate){
        this.loop=loop;
        this.from=from;
        dist=to-from;
        v=dist/(time/1000);
        this.listener=listener;
        if(from==to)
            finInicial=true;
        this.listenerUpdate=listenerUpdate;
    }
    
    public void setTime(float time){
         v=dist/(time/1000);
         this.time=0;
    }
    
    public void setListener(Modifier3DListener listener){
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
            e.removeControl(this);
        }else{            
            time+=tpf;
            value=v*time;
            if(Math.abs(value)>Math.abs(dist)){
                value=dist;
                onUpdate(value, from+value);
                if(listenerUpdate!=null)
                    listenerUpdate.onUpdate(this, e, value, from+value);
                if(!loop){
                    Spatial te=e;
                    e.removeControl(this);
                    if(listener!=null)
                        listener.onFinish(this, te);
                }else{
                    time=0;
                }
            }else{
                onUpdate(value, from+value);
                if(listenerUpdate!=null)
                    listenerUpdate.onUpdate(this, e, value, from+value);
            }
        }
    }
    
    public void reset(){
        time=0;
    }
    
    public abstract void onUpdate(float value, float delta);
    
    public interface Modifier3DListener{
        public void onFinish(Modifier3D m, Spatial e);
    }
    
    public interface Modifier3DListenerUpdate{
        public void onUpdate(Modifier3D m, Spatial e, float value, float delta);
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

    public Modifier3DListenerUpdate getListenerUpdate() {
        return listenerUpdate;
    }

    public void setListenerUpdate(Modifier3DListenerUpdate listenerUpdate) {
        this.listenerUpdate = listenerUpdate;
    }
    
    
}
