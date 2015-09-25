/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.persist.dao;

import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;
import com.preikestolen.common.Cosas;
import com.preikestolen.common.TipoCosa;
import com.preikestolen.persist.Persistable;
import com.preikestolen.server.Baldosa;
import com.preikestolen.server.Baldosa.TIPO;
import com.preikestolen.world.elements.Terreno;
import com.preikestolen.world.elements.entorno.Agua;
import com.preikestolen.world.elements.terreno.Celda;
import com.preikestolen.world.elements.terreno.Fractales;
import com.preikestolen.world.elements.terreno.TerrenoMaterial;
import com.utils.Vector2;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Edu
 */
@Serializable
public class CeldaDAO extends Persistable{
    public static String FILENAME="/#.celda";
    
    public long time;
    public HashMap<String, EstaticoDAO> estaticos;
    public HashMap<String, DinamicoDAO> dinamicos;
    
    public CeldaDAO(){
        super("");
    }
    
    public CeldaDAO(Vector2 pos){
        super(pos.toString());
    }
    
    public CeldaDAO(String file){
        super(file);
    }
    
    public Vector2 getPos(){
        String[] pos=id.split(",");
        
        try{
            return new Vector2(Integer.parseInt(pos[0]),Integer.parseInt(pos[1]));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }       
    
    public void setTime(){
        this.time=System.currentTimeMillis();
    }
    
    public void generar(Fractales f, Vector2 pos){
        change();
        time=System.currentTimeMillis();
        
        Vector3f posT=new Vector3f();
        Random rnd=new Random();
        
        Baldosa[][] baldosas=getBaldosas(f, pos);
        
        estaticos=new HashMap<String, EstaticoDAO>();
        dinamicos=new HashMap<String, DinamicoDAO>();
        
        int i=0;
        for(int x=0;x<Celda.SIZE*2;x+=5){
            for(int z=0;z<Celda.SIZE*2;z+=5){
                Baldosa b=baldosas[x][z];
                posT.set(x, b.altura, z);
                i++;
                
                if(!b.isAgua()){
                    if(!generarArboles(posT, i, rnd)){
                        if(!generarSeta(posT, i, rnd)){
                            if(!generarRoca(posT, i, rnd)){
                                if(!generarFlor(posT, i, rnd)){
                                    //generarHierba(posT, i, rnd);
                                }
                            }
                        }
                    }
                }                
            }
        }
        
        //System.out.println("Total Arboles generados "+estaticos.size());
    }
    
    public Baldosa[][] getBaldosas(Fractales f, Vector2 pos){
        Baldosa[][] res= new Baldosa[Celda.FULL_SIZE][Celda.FULL_SIZE];
        
        float[] alturas=Terreno.generarFractalAltura(f, pos, Celda.ALTURA_TERRENO);
        
        float[] t1=TerrenoMaterial.generarTxHierba(f,pos);
        float[] t2=TerrenoMaterial.generarTxArena(f,pos);
        float[] t3=TerrenoMaterial.generarTx3(f,pos);
        float[] t4=TerrenoMaterial.generarTx4(f,pos);
        float[] color=TerrenoMaterial.generarTxColor(f,pos);
        
        
        for(int z=0;z<Celda.FULL_SIZE;z++){
            for(int x=0;x<Celda.FULL_SIZE;x++){
                float altura=alturas[Celda.getBufferIndex(x, z)];
                TIPO t=TIPO.HIERBA;
                
                if(altura<Agua.ALTURA){
                    t=TIPO.AGUA;
                }
                
                res[x][z]=new Baldosa(altura, t);
            }
        }
        
        /*float r,g,b,a,r1,g1,b1,a1;
        for ( int i=0,e=0; i < Celda.INTERNAL_SIZE; i += 4, e++) {      
            r=255f;//Hierba
            g=t1[e]*255f;//Arena
            b=t2[e]*255f;//Tierra
            a=t3[e]*255f;//Bosque
            
            r1=t4[e]*255f*0;
            b1=color[e]*50f;

            
        }*/
        
        return res;
    }
    
    
    private boolean generarArboles(Vector3f pos, int indice, Random rnd){
        boolean generar=rnd.nextInt(10000)>8950;
        
        if(generar && pos.y>25){
            String id=pos.toString()+"_tree_"+indice;
            EstaticoDAO arbol=new EstaticoDAO(id, pos.clone(), Cosas.Tipo.ARBOL, rnd.nextFloat()* FastMath.PI, rnd.nextInt(2)+1, 100);
            estaticos.put(id, arbol);
        }
        return generar;
    }
    
    private boolean generarSeta(Vector3f pos, int indice, Random rnd){
        boolean generar=rnd.nextInt(10000)>9950;
        
        if(generar){
            String id=pos.toString()+"_mushroom_"+indice;
            EstaticoDAO arbol=new EstaticoDAO(id, pos.clone(), Cosas.Tipo.SETA, rnd.nextFloat()* FastMath.PI, rnd.nextInt(2)+1, 100);
            estaticos.put(id, arbol);
        }
        return generar;
    }
    
    private boolean generarRoca(Vector3f pos, int indice, Random rnd){
        boolean generar=rnd.nextInt(10000)>9950;
        
        if(generar){
            String id=pos.toString()+"_rock_"+indice;
            EstaticoDAO arbol=new EstaticoDAO(id, pos.clone(), Cosas.Tipo.ROCA, rnd.nextFloat()* FastMath.PI, rnd.nextInt(2)+1, 100);
            estaticos.put(id, arbol);
        }
        return generar;
    }
    
    private boolean generarFlor(Vector3f pos, int indice, Random rnd){
        boolean generar=rnd.nextInt(10000)>9950;
        
        if(generar){
            String id=pos.toString()+"_flower_"+indice;
            EstaticoDAO arbol=new EstaticoDAO(id, pos.clone(), Cosas.Tipo.FLOR, rnd.nextFloat()* FastMath.PI, rnd.nextInt(1)+1, 100);
            estaticos.put(id, arbol);
        }
        return generar;
    }
    
    private boolean generarHierba(Vector3f pos, int indice, Random rnd){
        boolean generar=rnd.nextInt(10000)>9950;
        
        if(generar){
            String id=pos.toString()+"_grass_"+indice;
            EstaticoDAO arbol=new EstaticoDAO(id, pos.clone(), Cosas.Tipo.HIERBA, rnd.nextFloat()* FastMath.PI, rnd.nextInt(1)+1, 100);
            estaticos.put(id, arbol);
        }
        return generar;
    }
    
    public boolean isCachearLocal(){
        return estaticos==null;
    }

    @Override
    public String getFileName() {
        return getFileName(id);
    }
    
    public static String getFileName(String id) {
        return FILENAME.replace("#", id);
    }
    
    public static CeldaDAO cargarById(String id){
        return Persistable.cargar(getFileName(id));
    }
    
    public List<DinamicoDAO> matarEstatico(String estatico){        
        return matarEstatico(estatico,System.currentTimeMillis(), new ArrayList<DinamicoDAO>());
    }
    
    public List<DinamicoDAO> matarEstatico(String estatico, long time, List<DinamicoDAO> added){        
        EstaticoDAO e=estaticos.remove(estatico);
        

        
        if(added.isEmpty()){
            added=new ArrayList<DinamicoDAO>();

            if(e.isArbol()){
                for(int i=0;i<3;i++){
                    DinamicoDAO din=addDinamico(e, "tronco", Cosas.Tipo.TRONCO, 1, 1);                    
                    added.add(din);
                }            
            }
        }else{
            for(EstaticoDAO s:added){
                estaticos.put(s.id, s);
            }
        }
        
        this.time=time;
        change();
        
        return added;
    }

    @Override
    public void save() {
        super.save(); //To change body of generated methods, choose Tools | Templates.
    }
    
    public DinamicoDAO addDinamico(EstaticoDAO estatico, String id, Cosas.Tipo tipo, int vida, int cantidad){
        return addDinamico(estatico.pos, id, tipo, vida, cantidad);
    }
    
    public DinamicoDAO addDinamico2(Vector3f posicion, String id, Cosas.Tipo tipo, int vida, int cantidad){
        float angle = (float)Math.random()*FastMath.PI*2;

        float x = (float)Math.cos(angle)*5;
        float z = (float)Math.sin(angle)*5;
        Vector3f pos=new Vector3f(posicion.x+x,posicion.y+1,posicion.z+z);
        PosicionRotacionDAO p=new PosicionRotacionDAO();
        p.setRotacion(new Vector3f());
        p.setPosition(pos);
        p.setVelocity(new Vector3f());
        DinamicoDAO din=new DinamicoDAO(id, p, tipo, 0, 1, vida, cantidad);
        dinamicos.put(din.id, din);
        return din;
    }
    
    public DinamicoDAO addDinamico(Vector3f posicion, String id, Cosas.Tipo tipo, int vida, int cantidad){
        float angle = (float)Math.random()*FastMath.PI*2;

        float x = (float)Math.cos(angle)*5;
        float z = (float)Math.sin(angle)*5;
        Vector3f pos=new Vector3f(posicion.x+x,posicion.y+1,posicion.z+z);
        PosicionRotacionDAO p=new PosicionRotacionDAO();
        p.setRotacion(new Vector3f());
        p.setPosition(pos);
        p.setVelocity(new Vector3f());
        DinamicoDAO din=new DinamicoDAO(pos.toString()+"_"+id+"_"+dinamicos.size(), p, tipo, 0, 1, vida, cantidad);
        dinamicos.put(din.id, din);
        return din;
    }
    
    public void addDinamico(DinamicoDAO din){
        dinamicos.put(din.id, din);
    }
    
    public DinamicoDAO cogerDinamico(String id){
        return dinamicos.remove(id);
    }
}
