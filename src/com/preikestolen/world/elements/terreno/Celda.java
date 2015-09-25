/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.world.elements.terreno;

import com.jme3.bounding.BoundingBox;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.BatchNodeBackground;
import com.jme3.scene.Node;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.preikestolen.persist.dao.CeldaDAO;
import com.preikestolen.persist.dao.CosaDAO;
import com.preikestolen.persist.dao.DinamicoDAO;
import com.preikestolen.persist.dao.PosicionRotacionDAO;
import com.preikestolen.world.elements.Terreno;
import com.preikestolen.world.elements.terreno.elementos.Dinamico;
import com.preikestolen.world.elements.terreno.elementos.Estatico;
import com.utils.Vector2;
import java.util.List;
import java.util.concurrent.Callable;

/**
 *
 * @author Edu
 */
public class Celda extends Node{
    public static final float ALTURA_TERRENO=30f;
    public static final String CELDA="celda";
    
    public static final int SIZE=128;
    public static final int FULL_SIZE=SIZE*2;
    public static final int INTERNAL_SIZE=SIZE*SIZE*4;
    
    private CeldaDAO dao;
    private TerrainQuad t;
    private Vector2 pos;
    
    private BatchNodeBackground debug;
    private boolean cargada=false;
    
    private float[] height;
    private RigidBodyControl body;
    private Terreno terreno;
    
    private Elementos elementos;
    
    public Celda(Vector2 posicion, final Terreno terreno, final Material mat){
        super("Celda"+posicion.toString());
        this.pos=posicion;
        this.terreno=terreno;
        
        terreno.getM().executor.execute(new Runnable() {
            public void run() {
                try{
                    generarTerreno( mat, terreno);
                    terreno.getM().app.enqueue(new Callable() {
                        public Object call() throws Exception {
                            if(!terreno.enVisor(pos)){
                                dettach(terreno);                                
                            }else{
                                attach(terreno);
                            }

                            return null;
                        }
                    });  
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public CeldaDAO getDao() {
        return dao;
    }

    public void setDao(CeldaDAO dao) {
        this.dao = dao;
    }
    
    public static int getXZtoBufferPos(int x, int z){
        return z*SIZE+x;
    }
    /*
    public static int getXZFulltoBufferPos(int x, int z){
        return z*SIZE*2+x;
    }
    
    public static int getXZFulltoBufferPos(float x, float z){
        return (int)(z*SIZE*2+x);
    }*/
    
    public static int getBufferIndex(float x, float z){
        return (int)(z*(FULL_SIZE+1)+x);
    }
    
    /**
     * Método que se encarga de generar el terrain Quad a partir de las fractales
     * @param mat
     * @param terreno
     * @return 
     */
    private TerrainQuad generarTerreno(Material mat, final Terreno terreno){
        cargarPersistencia();
        
        elementos=new Elementos(this);
        
        debug=new BatchNodeBackground("debug "+pos);
        this.height=Terreno.generarFractalAltura(terreno.getFractales(), pos, ALTURA_TERRENO);
        t = new TerrainQuad("terreno "+pos, SIZE/2+1, FULL_SIZE+1,height);//, new LodPerspectiveCalculatorFactory(getCamera(), 4)); // add this in to see it use entropy for LOD calculations
        t.setUserData(CELDA, this);
        
        TerrainLodControl control = new TerrainLodControl(t, terreno.getM().app.getCamera());
        t.addControl(control);
        
        terreno.getM().getApp().enqueue(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                terreno.onCeldaCargada(Celda.this);                
                return true;
            }
        });        
       
        terreno.getMaterial().generarTexturas(pos, terreno, mat, "AlphaMap", "AlphaMap_1");        

        t.setMaterial(mat);
        t.setModelBound(new BoundingBox());
        t.updateModelBound();        

        
        t.setShadowMode(RenderQueue.ShadowMode.Receive);  
        attachChild(t);
        
        

        
        attachChild(debug);
         
        debug.move(-SIZE, 0,-SIZE);
        
        setLocalTranslation(pos.x*FULL_SIZE+SIZE, 0, pos.z*FULL_SIZE+SIZE);  
        
        dao.save();
 
        CollisionShape col=CollisionShapeFactory.createMeshShape(t);
   
        body=new RigidBodyControl(col, 0);
        t.addControl(body);
        
        cargada=true;
        
        return t;
    }
    
    
    private void cargarPersistencia(){
        dao=CeldaDAO.cargarById(pos.toString());
        
        if(dao==null){
            //System.out.println("Sin persistencia para la posicion "+pos.toString());
            dao=new CeldaDAO(pos);
        }
    }
    
    public void dettach(Terreno terreno){
        if(getParent()!=null){
            terreno.getFisicas().remove(t);            
            getParent().detachChild(this);            
            terreno.getMaterial().putMaterial(t.getMaterial());
            
            elementos.dettach();        
        }
        
                
    }
    
    public void attach(Terreno terreno){
        if(getParent()==null){
            terreno.getM().escena.attachChild(this);   
            terreno.getFisicas().add(t);

            elementos.attach();
        }
    }
    
    public void generarObjetos(final Terreno terreno){  

        long t=System.currentTimeMillis();
       elementos.cargarElementos();
       System.out.println("*********************************************Estaticos: "+(System.currentTimeMillis()-t)+"ms");

    }
    
     
     public boolean isCargada(){
         return cargada;
     }
     
     public float getAltura(Vector3f pos){
         return getAltura(new Vector2((int)pos.x,(int)pos.z));
     }
     
     public float getAltura(Vector2 pos){
         if(height!=null && pos.x>=0 && pos.z>=0){
            return height[getBufferIndex(pos.x, pos.z)];         
         }
         return 0;
     }

     
     public void debug(Terreno t){
         for(int x=0;x<FULL_SIZE;x++){
             for(int z=0;z<FULL_SIZE;z++){
        
      /*           Line g=new Line(t.getM().getApp().getAssetManager(), );
        
        g.move(x,10,z);
        debug.attachChild(g);*/
             }
         }
        debug.batch();
     }
     
     public Terreno getTerreno(){
         return terreno;
     }
     
     public TerrainQuad getT(){
         return t;
     }
     
     /**
      * Este viene del listener
      * @param estatico
      * @param added
      * @param controlasTU
      * @return 
      */
     public Estatico matarEstatico(String estatico, List<DinamicoDAO> added, boolean controlasTU){
         return elementos.matarEstatico(estatico, added, controlasTU);
     }
     
     public void removeDinamico(Dinamico d){
         elementos.onRemoveDinamico(d);
     }
     
     public Dinamico removeDinamico(String id){
         Dinamico d=elementos.getDinamico(id);
         elementos.onRemoveDinamico(d);
         return d;
     }
     
     public Vector2 getPos(){
         return pos;
     }
     
     public boolean posicionarDinamico(String id, PosicionRotacionDAO pos, long time){
        return elementos.posicionarDinamico(id, pos, time);
     }
     
     public boolean sleepDinamico(String id, PosicionRotacionDAO pos, long time){
        return elementos.sleepDinamico(id, pos, time);
     }
     
     public Elementos getElementos(){
         return elementos;
     }
     
     public Dinamico addDinamico(CosaDAO cosa, int cantidad){

         DinamicoDAO dao=this.dao.addDinamico(getTerreno().getM().player.getWorldTranslation().add(0,1,0), cosa.tipo.name(), cosa.tipo, cosa.vida, cantidad);
         return elementos.addDinamico(dao, true);         
     }
}
