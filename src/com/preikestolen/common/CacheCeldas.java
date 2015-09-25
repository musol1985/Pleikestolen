/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.common;

import com.preikestolen.persist.dao.CeldaDAO;
import com.preikestolen.persist.dao.MundoDAO;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Edu
 */
public class CacheCeldas extends ControlAdapter{
    public static final int SIZE=10;
    public static final int SAVE_TIME=10*1000;
    private static final float hashTableLoadFactor = 0.75f;
    
    private long lastSave;
    
    private LinkedHashMap<String, CeldaDAO> celdas;
    private MundoDAO mundo;

    public CacheCeldas(MundoDAO mundo){
        this.lastSave=System.currentTimeMillis();
        
        this.mundo=mundo;
        
        int hashTableCapacity = (int) Math.ceil(SIZE / hashTableLoadFactor) + 1;
        celdas=new LinkedHashMap<String, CeldaDAO>(hashTableCapacity, hashTableLoadFactor, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, CeldaDAO> eldest) {
                boolean borrar=this.size() > SIZE;
                if(borrar){
                    eldest.getValue().save();
                }
                return borrar;
            }
        };
    }

    public void update(float tpf) {
        if(System.currentTimeMillis()-lastSave>SAVE_TIME)
            guardar();
    }

    public void guardar(){
        lastSave=System.currentTimeMillis();        
        //TODO guardar en FS
        System.out.println("Guardar en FS....");
        final List<CeldaDAO> celdasTmp=new ArrayList<CeldaDAO>(celdas.size());
        for(Entry<String, CeldaDAO> e:celdas.entrySet()){
            if(e.getValue().estaticos!=null){
                celdasTmp.add(e.getValue());
            }else{
                System.out.println("Celda "+e.getValue().getID()+" con estaticos a null!!!!!!!!!!!!!!!!!");
            }
        }
        
        new Thread(new Runnable() {
            public void run() {                
                for(CeldaDAO celda:celdasTmp){                    
                    celda.save();
                }
                mundo.save();
                System.out.println("Guardado en FS completo en "+((System.currentTimeMillis()-lastSave)/1000)+" segundos");
            }
        },"SaveThread").start();
    }
    
    public void put(String pos, CeldaDAO celda){
        System.out.println("put "+pos);
        celdas.put(pos, celda);
        
    }
    
    public CeldaDAO get(String pos){
        return celdas.get(pos);
    }
}
