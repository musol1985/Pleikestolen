/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.world.elements;

import com.jme3.bullet.PhysicsSpace;
import com.preikestolen.world.elements.terreno.Fractales;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.terrain.geomipmap.LRUCache;
import com.jme3.terrain.noise.ShaderUtils;
import com.jme3.terrain.noise.modulator.NoiseModulator;
import com.preikestolen.net.msg.game.MsgGetCeldas;
import com.preikestolen.persist.dao.CeldaDAO;
import com.preikestolen.world.MundoClient;
import com.preikestolen.world.elements.terreno.Celda;
import com.preikestolen.world.elements.terreno.TerrenoMaterial;
import com.utils.Vector2;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;


/**
 *
 * @author Edu
 */
public class Terreno implements NoiseModulator{
    public static final int MITAD_SIZE=Celda.SIZE;
    
    public static final int O_HIERBA=5000;
    public static final int O_ARENA=10000;
    public static final int O_TIERRA=0;
    
    private MundoClient m;
    
    //Esta cache es solo para las TX y para el terreno!!
    private LRUCache<String, Celda> cache=new LRUCache<String, Celda>(10);

    private Vector2 posicion;
    private Vector2 lastVirtualPos;
    
    private List<String> visor=new ArrayList<String>();
    
    private TerrenoMaterial material;
    
    private Fractales fractales;
    
    private List<Celda> celdasCreadas=new ArrayList<Celda>(4);

    
    public Terreno(MundoClient m){
        this.m=m;      
        this.material=new TerrenoMaterial();
        fractales=new Fractales(m.dao.getSeed());       
    }
    
    public void iniciar(Vector3f world){
        onMover(world);
    }

    public MundoClient getM() {
        return m;
    }
    
    public Fractales getFractales(){
        return fractales;
    }
    
    public TerrenoMaterial getMaterial(){
        return material;
    }
         
     public static float[] generarFractalAltura(Fractales f, Vector2 pos, float escalar){
         //System.out.println("generando fractal de altura "+pos);
        FloatBuffer buffer = f.altura.getFractal().getBuffer(pos.x*(Celda.FULL_SIZE), pos.z*(Celda.FULL_SIZE), 0, Celda.FULL_SIZE+1);
        float[] tmp=buffer.array();
        float[] res=new float[tmp.length];
        for(int i=0;i<tmp.length;i++){
            float t=tmp[i]*escalar;
            res[i]=t;            
        }
        return res;
    }
     
     public void onPreCambiarPosicion(Vector2 nuevaPos){
         
     }
     
     public Celda getCeldaByPos(Vector3f pos){
         Vector2 vp=worldToCelda(pos);
         
         return cache.get(vp.toString());
     }
     
     public Vector2 worldToCelda(Vector3f pos){
         Vector2 p=new Vector2();
         p.x= (int)FastMath.ceil(pos.x/Celda.FULL_SIZE)-1;
         p.z= (int)FastMath.ceil(pos.z/Celda.FULL_SIZE)-1;
         return p;
     }
     
     public Vector2 isCambioChunk(Vector3f pos, int X, int Z){
         Vector2 np=null;
         if(X>0){
            np=worldToCelda(pos.add(Celda.FULL_SIZE, 0, 0));
         }else if(X<0){
            np=worldToCelda(pos.add(-Celda.FULL_SIZE, 0, 0));
         }
         if(Z>0){
            np=worldToCelda(pos.add(0,0,Celda.FULL_SIZE));
         }else if(Z<0){
            np=worldToCelda(pos.add(0,0,-Celda.FULL_SIZE));
         }
         return np;
     }
     
    public void onMover(Vector3f worldPos){
        Vector2 nv=Vector2.fromVirtual(worldPos);
        if(lastVirtualPos!=null && lastVirtualPos.igual(nv)){
                //System.out.println("Iguales! "+np+"   actual: "+lastPos);
        }else{	
            Vector2 np=Vector2.fromReal(worldPos);
           /* System.out.println("Posicion: "+worldPos);
            System.out.println("diferentes, se tiene que cargar el quad"+np);*/
            onCambiarCuadricula(np, worldPos);
            lastVirtualPos=nv;
        }
        
    }
    
    public float getAltura(Vector3f worldPos){
        Celda c=getCeldaByPos(worldPos);
        //System.out.println("Baldosa: "+Vector2.getBaldosa(worldPos));
        return c.getAltura(Vector2.getBaldosa(worldPos));
    }
    
    public void onCambiarCuadricula(Vector2 p, Vector3f worldPos){
        long t=System.currentTimeMillis();
            int x=0;
            int z=0;


            x=(int)(Math.abs(worldPos.x)-Math.abs(Celda.FULL_SIZE*p.x))/Celda.SIZE;
            if(x<0)x*=-1;
            z=(int)(Math.abs(worldPos.z)-Math.abs(Celda.FULL_SIZE*p.z))/Celda.SIZE;
            if(z<0)z*=-1;		

           // System.out.println("virtual "+"("+x+","+z+")");
            
            crearCelda(p);
            if(x==0 && z==0){
                    crearCelda(p.clone().addX(-1));
                    crearCelda(p.clone().add(-1,-1));
                    crearCelda(p.clone().addZ(-1));
            }else if(x==1 && z==0){
                    crearCelda(p.clone().addX(1));
                    crearCelda(p.clone().add(1,-1));
                    crearCelda(p.clone().addZ(-1));
            }else if(x==0 && z==1){
                    crearCelda(p.clone().addX(-1));
                    crearCelda(p.clone().add(-1,1));
                    crearCelda(p.clone().addZ(1));
            }else if(x==1 && z==1){
                    crearCelda(p.clone().addX(1));
                    crearCelda(p.clone().add(1,1));
                    crearCelda(p.clone().addZ(1));
            }	
            
            //System.out.println("PreVisrooooooooooooooooo"+visor.size());
            for(int i=visor.size()-1;i>3;i--){
               Celda c=cache.get(visor.remove(i));
               if(c!=null)
                    c.dettach(this);
              // System.out.println("INooooo"+visor.size());
            }
            //System.out.println("Visrooooooooooooooooo"+visor.size());
            
           // System.out.println("---------------->Tiempo: "+(System.currentTimeMillis()-t)+"ms");
    }
    
    
    private void crearCelda(Vector2 pos){
        long t=System.currentTimeMillis();
        Celda c=cache.get(pos.toString());
        if(c==null){
             c=new Celda(pos, this, getMaterial().getMaterial(m.app.getAssetManager()));
             cache.put(pos.toString(), c);             
        }else{
            if(c.isCargada())
                c.attach(this);
            onCeldaCargada(c);
        }
        addVisor(pos);
        
        //System.out.println("Celda creada time: "+(System.currentTimeMillis()-t)+"ms");
    }
    
    public void onCeldaCargada(Celda c){
       // System.out.println("add celda creada "+c.getDao().getID());
        celdasCreadas.add(c);            
        //Se ha cargado el visor entero, asi que enviamos el msg
        if(celdasCreadas.size()==4){
            MsgGetCeldas msg=new MsgGetCeldas(celdasCreadas);
            m.getApp().getNet().send(msg);
            celdasCreadas.clear();
        }        
    }
    
    public Celda getCelda(CeldaDAO c){
        return cache.get(c.getID());
    }

    public Celda getCelda(String c){
        return cache.get(c);
    }
    
    public void onCelda(CeldaDAO dao, Celda celda){               
        celda.generarObjetos(this);                
    }
    
    public boolean enVisor(Vector2 pos){
        return visor.contains(pos.toString());
    }
    
    private void addVisor(Vector2 pos){
        visor.remove(pos.toString());
        visor.add(0,pos.toString());    
    }


    public Vector2 getPosicion() {
        return posicion;
    }

    public float value(float... in) {
         return ShaderUtils.clamp(in[0] * 0.5f + 0.5f, 0, 1);
    }
    
    public boolean test;

    public boolean isTest() {
        return test;
    }

    public void setTest(boolean test) {
        this.test = test;
        System.out.println("test!"+test);
    }
    
    public PhysicsSpace getFisicas(){
        return m.app.getFisicas();
    }
    
    public void close(){
        Iterator<Entry<String,Celda>> it=cache.getAll().iterator();
        while(it.hasNext()){
            it.next().getValue().getDao().save();
        }        
    }

    
    public void reGenerarAltura(){
        Iterator<Entry<String,Celda>> it=cache.getAll().iterator();
        while(it.hasNext()){
            it.next().getValue().dettach(this);
        } 
        cache.clear();
        visor.clear();
        lastVirtualPos=null;
        onMover(getM().player.getWorldTranslation());
    }
    
    public Celda getCurrentCelda(){
        return getCeldaByPos(getM().player.getWorldTranslation());
    }
}
