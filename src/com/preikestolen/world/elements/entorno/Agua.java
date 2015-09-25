/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.world.elements.entorno;

import com.jme3.app.SimpleApplication;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.scene.Node;
import com.jme3.water.WaterFilter;
import com.preikestolen.world.MundoClient;
import com.preikestolen.world.elements.Entorno;

/**
 *
 * @author Edu
 */
public class Agua {
    public static final float ALTURA=15f;
    private WaterFilter water;
    
    public Agua(SimpleApplication c, Entorno entorno, MundoClient mundo){    
        water = new WaterFilter(mundo.escena, entorno.luz.getDireccion());

        FilterPostProcessor fpp = new FilterPostProcessor(c.getAssetManager());

        fpp.addFilter(water);

        water.setWaveScale(0.001f);
        water.setMaxAmplitude(0.5f);
        water.setRefractionStrength(0.2f);
        water.setFoamIntensity(0.3f);
        water.setFoamHardness(0.5f);
        water.setShoreHardness(1f);
        water.setFoamExistence(new Vector3f(0.5f,1f,1.5f));
        water.setUseSpecular(true);
        water.setWaterHeight(ALTURA);
        water.setShininess(0.3f);
        water.setSpeed(1f);
        water.setLightDirection(entorno.luz.getDireccion());

        
        c.getViewPort().addProcessor(fpp);
    }
    
    public WaterFilter getFilter(){
        return water;
    }

    public void setRojo(float v){
        water.getLightColor().r=v;
    }
    
    public float getRojo(){
        return water.getLightColor().r;
    }
    
    public void setVerde(float v){
        water.getLightColor().g=v;
    }
    
    public float getVerde(){
        return water.getLightColor().g;
    }
    
     public void setAzul(float v){
        water.getLightColor().b=v;
    }
    
    public float getAzul(){
        return water.getLightColor().b;
    }
    
    public void setX(float v){
        water.getFoamExistence().x=v;
    }
    
    public float getX(){
        return water.getFoamExistence().x;
    }
        
    public void setY(float v){
        water.getFoamExistence().y=v;
    }
    
    public float getY(){
        return water.getFoamExistence().y;
    }
    
        
    public void setZ(float v){
        water.getFoamExistence().z=v;
    }
    
    public float getZ(){
        return water.getFoamExistence().z;
    }
}
