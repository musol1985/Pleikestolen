/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;

import com.jme3.math.Vector3f;
import com.preikestolen.world.elements.terreno.Celda;
import java.io.Serializable;

/**
 *
 * @author Edu
 */
@com.jme3.network.serializing.Serializable
public class Vector2 implements Serializable{
    public int x;
    public int z;

    public Vector2(int x, int z) {
        this.x = x;
        this.z = z;
    }
    
    public Vector2() {
        this.x = 0;
        this.z = 0;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }
    
    public Vector2 add(int x, int z){
        this.x+=x;
        this.z+=z;
        return this;
    }
    
    public Vector2 addX(int x){
        this.x+=x;
        return this;
    }
    
    public Vector2 addZ(int z){
        this.z+=z;
        return this;
    }
    
    public boolean igual(Vector2 v){
        return v.x==x && v.z==z;
    }
    
    @Override
    public String toString(){
        return x+","+z;
    }
    
    @Override
    public Vector2 clone(){
        return new Vector2(x,z);
    }
    
    public static Vector2 fromReal(Vector3f world){
	return new Vector2((int)Math.ceil(world.x/Celda.FULL_SIZE)-1,(int)Math.ceil(world.z/Celda.FULL_SIZE)-1);
    }
    
    public static Vector2 fromVirtual(Vector3f world){
	return new Vector2((int)Math.ceil(world.x/Celda.SIZE)-1,(int)Math.ceil(world.z/Celda.SIZE)-1);
    }
    
    public static Vector2 getBaldosa(Vector3f world){
        Vector2 v=fromReal(world);//Obtenemos las coordenadas de las celdas
	return new Vector2((int)Math.ceil(world.x-v.x*Celda.FULL_SIZE),(int)Math.ceil(world.z-v.z*Celda.FULL_SIZE));
    }
}
