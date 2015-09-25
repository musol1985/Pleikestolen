/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.world.elements;


import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.BatchNode;
import com.preikestolen.world.MundoClient;

/**
 *
 * @author Edu
 */
public class Controles{
    public static final int TERRENO_PRELOAD=100;    
    
    public static final String ARRIBA="arriba";
    public static final String ABAJO="abajo";
    public static final String IZQ="izquierda";
    public static final String DER="derecha";
    
    public static final String MOUSE_RUEDA="MouseWheel";
    public static final String MOUSE_RUEDA_MIN="MouseWheel-";
    public static final String CLICK_RUEDA="clickRueda";
    public static final String CLICK_IZQ="clickIzq";
    public static final String MOUSE_MOVER="MouseMover";
    
    
    public static final String ESPACIO="espacio";
    public static final String CONTROL="control";
    public static final String K1="key1";
    public static final String K2="key2";
   
    private boolean clickRueda=false;   
    private float lastMouseX=0;
    private MundoClient mundo;    
   // private BlockChunkControl currentChunk;
    
    public Controles(SimpleApplication c, MundoClient mundo){
        this.mundo=mundo;
        
        c.getInputManager().addMapping(ARRIBA, new KeyTrigger(KeyInput.KEY_W));
        c.getInputManager().addListener(actionListener, ARRIBA);
        c.getInputManager().addMapping(ABAJO, new KeyTrigger(KeyInput.KEY_S));
        c.getInputManager().addListener(actionListener, ABAJO);
        
        c.getInputManager().addMapping(IZQ, new KeyTrigger(KeyInput.KEY_A));
        c.getInputManager().addListener(actionListener, IZQ);
        c.getInputManager().addMapping(DER, new KeyTrigger(KeyInput.KEY_D));
        c.getInputManager().addListener(actionListener, DER);
  
        c.getInputManager().addMapping(CLICK_RUEDA, new MouseButtonTrigger(MouseInput.BUTTON_MIDDLE));
        c.getInputManager().addListener(actionListener, CLICK_RUEDA);
        
        c.getInputManager().addMapping(MOUSE_RUEDA_MIN, new MouseAxisTrigger(MouseInput.AXIS_WHEEL, false));
        c.getInputManager().addMapping(MOUSE_RUEDA, new MouseAxisTrigger(MouseInput.AXIS_WHEEL, true));
        c.getInputManager().addListener(analogListener, MOUSE_RUEDA);
        c.getInputManager().addListener(analogListener, MOUSE_RUEDA_MIN);
        
        c.getInputManager().addMapping(CLICK_IZQ, new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        c.getInputManager().addListener(actionListener, CLICK_IZQ);
        
        c.getInputManager().addMapping(MOUSE_MOVER, new MouseAxisTrigger(MouseInput.AXIS_X, true));
        c.getInputManager().addMapping(MOUSE_MOVER, new MouseAxisTrigger(MouseInput.AXIS_X, false));
        c.getInputManager().addMapping(MOUSE_MOVER, new MouseAxisTrigger(MouseInput.AXIS_Y, true));
        c.getInputManager().addMapping(MOUSE_MOVER, new MouseAxisTrigger(MouseInput.AXIS_Y, false));        
        c.getInputManager().addListener(analogListener, MOUSE_MOVER);
        
        c.getInputManager().addMapping(ESPACIO, new KeyTrigger(KeyInput.KEY_SPACE));
        c.getInputManager().addListener(actionListener, ESPACIO);
        
        c.getInputManager().addMapping(CONTROL, new KeyTrigger(KeyInput.KEY_LCONTROL));
        c.getInputManager().addListener(actionListener, CONTROL);
        
        c.getInputManager().addMapping(K1, new KeyTrigger(KeyInput.KEY_1));
        c.getInputManager().addListener(actionListener, K1);
        c.getInputManager().addMapping(K2, new KeyTrigger(KeyInput.KEY_2));
        c.getInputManager().addListener(actionListener, K2);
    }

    private ActionListener actionListener=new ActionListener() {
        public void onAction(String action, boolean value, float tpf) {
            if(action.equals(CLICK_RUEDA)){
                clickRueda=value;
            }else if(action.equals(CLICK_IZQ) && value){
                ray();
            }else if(action.equals(ESPACIO)&&value){
                mundo.espacio();
            }else if(action.equals(CONTROL)){
                mundo.control(value);
            }else if(action.equals(ARRIBA)){                
                mundo.player.onArriba(value);
            }else if(action.equals(ABAJO)){
                mundo.player.onAbajo(value);
            }else if(action.equals(IZQ)){
                mundo.player.onIzquierda(value);
            }else if(action.equals(DER)){
                mundo.player.onDerecha(value);
            }else if(action.equals(K1) && value){
                mundo.player.onKey(1);
            }else if(action.equals(K2) && value){
                mundo.player.onKey(2);
            }
        }
    };
    
    
    private void ray(){
        CollisionResults results = new CollisionResults();
        Vector2f mouseCoords=mundo.app.getInputManager().getCursorPosition();
        Vector3f pos = mundo.app.getCamera().getWorldCoordinates(mouseCoords, 0).clone();
        Vector3f dir = mundo.app.getCamera().getWorldCoordinates(mouseCoords, 0.3f).clone();
        dir.subtractLocal(pos).normalizeLocal();
        Ray ray = new Ray(pos, dir);
        //ray.setLimit(10f);

        mundo.escena.collideWith(ray, results);
        
        for(CollisionResult r:results){
            if(r.getGeometry().getName().startsWith("Untitled.0071")){

             /*   System.out.println("arbol!!"+r.getGeometry().getName());
                System.out.println("arbol pos: "+r.getGeometry().getWorldTranslation());*/
            }else{
              // System.out.println(r.getGeometry().getName());
            }
        }
    }

    private AnalogListener analogListener=new AnalogListener() {
        public void onAnalog(String action, float value, float tpf) {
            if(action.equals(MOUSE_RUEDA)){
                onMouseRueda(value, tpf);
            }else if(action.equals(MOUSE_RUEDA_MIN)){
                onMouseRueda(-value, tpf);
            }/*else if(action.equals(ARRIBA) && value!=0){
                moverCamY(tpf);
                mundo.player.onArriba(value!=0);
            }else if(action.equals(ABAJO) && value!=0){
                moverCamY(-tpf);
                mundo.player.onAbajo(value!=0);
            }else if(action.equals(IZQ) && value!=0){
                moverCamX(tpf);
                mundo.player.onIzquierda(value!=0);
            }else if(action.equals(DER) && value!=0){
                moverCamX(-tpf);
                mundo.player.onDerecha(value!=0);
            }*/if(action.equals(MOUSE_MOVER)){
                onMouseMover(mundo.getApp().getInputManager().getCursorPosition(), tpf);
            }
            
            if(action.equals(ARRIBA) || action.equals(ABAJO)){

            }
        }
    };
    
    private void onMouseMover(Vector2f cursor, float tpf){
        if(clickRueda){
            float x=cursor.x-lastMouseX;
            if(x!=0){
                mundo.cam.posicion.rotate(0, x*tpf*2, 0);
            }
        }
        lastMouseX=cursor.x;
    }
    
    private void onMouseRueda(float v, float tpf){
       mundo.cam.zoom(v);
    }    
    
    private void moverCamX(float v){
        Vector3f vel = new Vector3f();
        Vector3f pos = mundo.cam.posicion.getLocalTranslation().clone();
        mundo.cam.posicion.getLocalRotation().getRotationColumn(0, vel);
        vel.multLocal(v);
       // mundo.player.onMover(vel, mundo.terreno.getAltura(mundo.player.getWorldTranslation()));
        //pos.addLocal(vel);
       // mundo.cam.posicion.setLocalTranslation(pos);   
    }
    
    private void moverCamY(float v){
        Vector3f vel = new Vector3f();
        Vector3f pos = mundo.cam.posicion.getLocalTranslation().clone();
        mundo.cam.posicion.getLocalRotation().getRotationColumn(2, vel);
        vel.multLocal(v);
       // mundo.player.onMover(vel, mundo.terreno.getAltura(mundo.player.getWorldTranslation()));
    }
}
