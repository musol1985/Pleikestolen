/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.world.elements.terreno;

import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.BatchNodeBackground;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.preikestolen.persist.dao.DinamicoDAO;
import com.preikestolen.persist.dao.EstaticoDAO;
import com.preikestolen.persist.dao.PosicionRotacionDAO;
import com.preikestolen.world.elements.Terreno;
import static com.preikestolen.world.elements.terreno.Celda.SIZE;
import com.preikestolen.world.elements.terreno.elementos.estaticos.Arbol;
import com.preikestolen.world.elements.terreno.elementos.Dinamico;
import com.preikestolen.world.elements.terreno.elementos.Estatico;
import com.preikestolen.world.elements.terreno.elementos.estaticos.Roca;
import com.preikestolen.world.elements.terreno.elementos.dinamicos.Tronco;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Edu
 */
public class Elementos {
    private BatchNodeBackground estaticos;
    private BatchNodeBackground dinamicos;
    private Celda c;
    
    public Elementos(Celda c){
        this.c=c;
        estaticos=iniciarBatch("estaticos");
        dinamicos=iniciarBatch("dinamicos");                        
    }
    
    private BatchNodeBackground iniciarBatch(String id){
        BatchNodeBackground batch=new BatchNodeBackground(id+c.getPos());
        batch.setShadowMode(RenderQueue.ShadowMode.Cast);
        c.attachChild(batch);         
        batch.move(-SIZE, 0,-SIZE);
        return batch;
    }
    
    public void dettach(){
        for(Spatial s:estaticos.getChildren()){
            if(s instanceof Estatico)
                ((Estatico)s).removeFisicas(c.getTerreno());
        }
        for(Spatial s:dinamicos.getChildren()){
            if(s instanceof Dinamico)
                ((Dinamico)s).removeFisicas(c.getTerreno());
        }
    }
    
    public void attach(){
        for(Spatial s:estaticos.getChildren()){
            if(s instanceof Estatico)
                ((Estatico)s).addFisicas(c.getTerreno());
        }
        for(Spatial s:dinamicos.getChildren()){
            if(s instanceof Dinamico)
                ((Dinamico)s).addFisicas(c.getTerreno());
        }
    }
    
    public void cargarElementos(){
        cargarEstaticos();
        cargarDinamicos();
    }
    
    private void cargarEstaticos(){  
        System.out.println(c+"    "+estaticos.getChildren().size()+"=?"+(c.getDao().estaticos.size()+estaticos.batches()));
        if(estaticos.getChildren().size()==c.getDao().estaticos.size()+estaticos.batches())
            return;
        
        for(Map.Entry<String, EstaticoDAO> estatico:c.getDao().estaticos.entrySet() ){
            addEstatico(estatico.getValue());
        }

        
        batch(estaticos);
    }
    
    private void cargarDinamicos(){  
        System.out.println(c+"    "+dinamicos.getChildren().size()+"=?"+(c.getDao().dinamicos.size()+dinamicos.batches()));
        if(dinamicos.getChildren().size()==c.getDao().dinamicos.size()+dinamicos.batches())
            return;    

        
        for(Map.Entry<String, DinamicoDAO> din:c.getDao().dinamicos.entrySet() ){
            addDinamico(din.getValue(), false);
        }
        
        batch(dinamicos);
    }
    
    private void batch(BatchNodeBackground b){
        try{
          //  GeometryBatchFactory.alignBuffers(b, GeometryBatchFactory.AlignOption.CreateMissingBuffers);
            b.batch();  

            b.postBatch(); 
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * Este viene del listener
     * Es decir es otro usuario remoto quien lo ha cogido(el estatico)
     * Por lo que viene la lista de dinamicos añadidos y el estatico que se debe eliminar
     * este metodo elimina el estatico y añade los dinamicos(Dinamico tal cual ya que el dao ya lo tenemos)
     * @param e
     * @param added
     * @param controlasTU
     * @return 
     */
    public List<Dinamico> onRemoveEstatico(Estatico e, List<DinamicoDAO> added, boolean controlasTU){
        e.remove(c.getTerreno(), c);                        
        System.out.println("Remover estatico--->"+estaticos.detachChild(e));
              
        List<Dinamico> dins=new ArrayList<Dinamico>(added.size());
        for(DinamicoDAO dinamico:added){
            dins.add(addDinamico(dinamico, controlasTU));
        }
        
        batch(estaticos);
       
        return dins;
    }
    
    public void onRemoveDinamico(Dinamico d){
        d.remove(c.getTerreno(), c);                        
        System.out.println("Remover dinamico--->"+dinamicos.detachChild(d));
              
        
        batch(dinamicos);
    }
    
    public Dinamico getDinamico(String id){
        return (Dinamico) dinamicos.getChild(id);
    }
    
    /**
     * Este viene del listener
     * @param estatico
     * @param added
     * @param controlasTU
     * @return 
     */
    public Estatico matarEstatico(String estatico, List<DinamicoDAO> added, boolean controlasTU){
        Estatico e=(Estatico)estaticos.getChild(estatico);   
        List<Dinamico> dins=onRemoveEstatico(e, added, controlasTU);
        
        c.getTerreno().getM().player.onRemoverEstatico(e, dins);
        
        //c.getTerreno().getM().player.onRemoverEstatico(e);
        return e;
    }
    
    
    private void addEstatico(EstaticoDAO estatico){
        Estatico e=null;
        if(estatico.isArbol()){
            e=addArbol(c.getTerreno(), estatico, c.getT());
        }else if(estatico.isSeta()){
            addSeta(c.getTerreno(), estatico, c.getT());
        }else if(estatico.isRoca()){
            e=addRoca(c.getTerreno(), estatico, c.getT());
        }else if(estatico.isFlor()){
            addFlor(c.getTerreno(), estatico, c.getT());
        }else if(estatico.isHierba()){
            addHierba(c.getTerreno(), estatico, c.getT());
        }/*else if(estatico.isTronco()){
            e=addTronco(terreno, estatico, t);
        }*/

        if(e!=null && c.getParent()!=null){            
            e.addFisicas(c.getTerreno());
        }
    }
    
    /**
     * S eencarga de generar un Dinamico a partir del DAO
     */
    public Dinamico addDinamico(DinamicoDAO dinamico, boolean controlasTU){
        Dinamico e=null;
        
        if(dinamico.isTronco()){
            e=addTronco(c.getTerreno(), dinamico, c.getT());
        }
        
        if(controlasTU){
            e.controlar(c);
        }

        if(e!=null && c.getParent()!=null){
            e.addFisicas(c.getTerreno());
        }
        
        return e;
    }
    
    public boolean posicionarDinamico(String id, PosicionRotacionDAO pos, long time){
        Dinamico d=(Dinamico)dinamicos.getChild(id);
        if(d!=null && time>d.getTimeout()){
            d.updatePosicion(c, pos);            
            d.setTimeout(time);
            return true;
        }
        return false;
    }
    
    public boolean sleepDinamico(String id, PosicionRotacionDAO pos, long time){
        Dinamico d=(Dinamico)dinamicos.getChild(id);
        if(d!=null && time>d.getTimeout()){         
            System.out.println("onsleeeep"+id);
            pos.getVelocity().set(0, 0, 0);
            d.updatePosicion(c, pos);
            d.setTimeout(time);
            return true;
        }
        return false;
    }        
    
    
     public Arbol addArbol(Terreno terreno, EstaticoDAO item, TerrainQuad t){           
         Arbol a=new Arbol(item, terreno.getM());

         estaticos.attachChild(a);
        return a;
    }
     
     public Tronco addTronco(Terreno terreno, DinamicoDAO item, TerrainQuad t){           
         Tronco tr=new Tronco(item, terreno.getM());
         dinamicos.attachChild(tr);
        return tr;
    }
     
     public void addSeta(Terreno terreno, EstaticoDAO item, TerrainQuad t){
            Node arbol=(Node)terreno.getM().getApp().getAssetManager().loadModel("Models/setas/seta"+item.subtipo+".j3o");
          //  arbol.rotate(0, a.getAng(), 0);
            estaticos.attachChild(arbol);
            arbol.setName("subSeta");

            arbol.move(item.pos.x, item.pos.y, item.pos.z);      
            arbol.rotate(0, item.ang, 0);
           //arbol.move(0, getAltura(new Vector2f(arbol.getWorldTranslation().x, arbol.getWorldTranslation().z)), 0);
    }
     
     public Roca addRoca(Terreno terreno, EstaticoDAO item, TerrainQuad t){
        Roca r=new Roca(item, terreno.getM());
        estaticos.attachChild(r);  
        return r;
    }
     
     public void addFlor(Terreno terreno, EstaticoDAO item, TerrainQuad t){
            Node arbol=(Node)terreno.getM().getApp().getAssetManager().loadModel("Models/flores/flor"+item.subtipo+".j3o");
          //  arbol.rotate(0, a.getAng(), 0);
            estaticos.attachChild(arbol);
            arbol.setName("subFlor");

            arbol.move(item.pos.x, item.pos.y, item.pos.z);      
            arbol.rotate(0, item.ang, 0);
           //arbol.move(0, getAltura(new Vector2f(arbol.getWorldTranslation().x, arbol.getWorldTranslation().z)), 0);
    }
     
     public void addHierba(Terreno terreno, EstaticoDAO item, TerrainQuad t){
            Node arbol=(Node)terreno.getM().getApp().getAssetManager().loadModel("Models/hierbas/hierba"+item.subtipo+".j3o");
          //  arbol.rotate(0, a.getAng(), 0);
            estaticos.attachChild(arbol);
            arbol.setName("subHierba");

            arbol.move(item.pos.x, item.pos.y, item.pos.z);      
            arbol.rotate(0, item.ang, 0);
           //arbol.move(0, getAltura(new Vector2f(arbol.getWorldTranslation().x, arbol.getWorldTranslation().z)), 0);
    }

          
}
