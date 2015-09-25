/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.world.elements.terreno;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.terrain.noise.fractal.FractalSum;
import com.jme3.texture.Image;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture2D;
import com.jme3.util.BufferUtils;
import com.preikestolen.world.elements.Terreno;
import static com.preikestolen.world.elements.Terreno.O_ARENA;
import static com.preikestolen.world.elements.Terreno.O_HIERBA;
import static com.preikestolen.world.elements.Terreno.O_TIERRA;
import com.utils.Vector2;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author Edu
 */
public class TerrenoMaterial {
    private Material matTerrain;
    private Vector<Material> materiales=new Vector<Material>();
    
    
    private Material nuevoMaterial(AssetManager asset){
        matTerrain=asset.loadMaterial("Materials/Terreno.j3m").clone();

        matTerrain.setTexture("AlphaMap", getImagen());
        matTerrain.setTexture("AlphaMap_1", getImagen());

        return matTerrain;
    }
     
     private Texture2D getImagen(){
        byte[] data = new byte[Celda.INTERNAL_SIZE];       

        Image img=new Image(Image.Format.RGBA8, Celda.SIZE, Celda.SIZE, BufferUtils.createByteBuffer(data));

        Texture2D texture = new Texture2D(img);

        texture.setMagFilter(Texture.MagFilter.Nearest);
        
        return texture;
     }
     
     public static float[] generarTxHierba(Fractales f, Vector2 pos){
         return generarTx(pos, f.t1, O_HIERBA);
     }
     public static float[] generarTxArena(Fractales f, Vector2 pos){
         return generarTx(pos, f.t2, O_ARENA);
     }
     public static float[] generarTx3(Fractales f, Vector2 pos){
         return generarTx(pos, f.t3, O_TIERRA);
     }
     public static float[] generarTx4(Fractales f, Vector2 pos){
         return generarTx(pos, f.t4,0);
     }
    public static float[] generarTxColor(Fractales f, Vector2 pos){
         return generarTx(pos, f.color,0);
     }
     
     public static float[] generarTx(Vector2 pos, FractalSum f, int origen){
         //System.out.println("generando fractal de tx "+pos);
        FloatBuffer buffer = f.getBuffer(pos.x*(Celda.SIZE), pos.z*(Celda.SIZE), origen, Celda.SIZE);
        float[] tmp=buffer.array();
        float[] res=new float[tmp.length];
        
        int SIZEZ=Celda.SIZE-1;
        for(int x=0;x<Celda.SIZE;x++){
            for(int z=0;z<Celda.SIZE;z++){
                int e=Celda.getXZtoBufferPos(x, z);
                int i=Celda.getXZtoBufferPos(x, SIZEZ-z);
                
                res[e]=tmp[i];
            }
        }
        return res;
    }

     
     public synchronized Material getMaterial(AssetManager asset){
        Material m=null;
        
        if(materiales.size()>0){            
            m=materiales.remove(0);
        }else{
            m=nuevoMaterial(asset);
        }
        return m;
    }
    
    public synchronized void putMaterial(Material m){     
        if(!materiales.contains(m)){
            materiales.add(m);      
        }
    }
    
    
    
    public void generarTexturas(Vector2 pos, Terreno t, Material m, String map1, String map2){
        Random rnd=new Random();
        
        Texture texture=m.getTextureParam(map1).getTextureValue();
        Texture texture2=m.getTextureParam(map2).getTextureValue();
 
        ByteBuffer data=texture.getImage().getData(0);
        ByteBuffer data2=texture2.getImage().getData(0);

     
        float[] t1=generarTxHierba(t.getFractales(),pos);
        float[] t2=generarTxArena(t.getFractales(),pos);
        float[] t3=generarTx3(t.getFractales(),pos);
        float[] t4=generarTx4(t.getFractales(),pos);
        float[] color=generarTxColor(t.getFractales(),pos);

        data.rewind();
        data2.rewind();
        
        float r,g,b,a,r1,g1,b1,a1;
        for ( int i=0,e=0; i < Celda.INTERNAL_SIZE; i += 4, e++) {      
            r=255f;//Hierba
            g=t1[e]*255f;//Arena
            b=t2[e]*255f;//Tierra
            a=t3[e]*255f;//Bosque
            
            r1=t4[e]*255f*0;
            b1=color[e]*50f;
            
             data.put((byte) (r)); // r
             data.put((byte) (g)); // g
             data.put((byte) (b)); // b
             data.put((byte) (a)); // a
             
             data2.put((byte) (r1)); // Map4
             data2.put((byte) (0f)); // Map5
             data2.put((byte) (b1)); // Map6
             data2.put((byte) (0f)); // Map6
        }
        
        texture.getImage().setUpdateNeeded();
        texture2.getImage().setUpdateNeeded();
    }
}
