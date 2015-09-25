/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.world.elements.entorno;

import com.jme3.app.SimpleApplication;
import com.jme3.light.Ambiente;
import com.jme3.light.Sol;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.ssao.SSAOFilter;
import com.jme3.renderer.ViewPort;
import com.jme3.shadow.DirectionalLightShadowRenderer;
import com.jme3.shadow.PointLightShadowRenderer;
import com.preikestolen.Game;
import com.preikestolen.world.elements.terreno.elementos.ILight;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Edu
 */
public class Luz {
    private Vector3f direccion;
    public Sol sol;
    public Ambiente ambiente;
    public ColorRGBA color;
    public DirectionalLightShadowRenderer sombrasDia;
    private List<PointLightShadowRenderer> sombrasNoche=new ArrayList<PointLightShadowRenderer>();
    
    private FilterPostProcessor SSAO;
    private boolean sombrasNocturnas=false;
    
    private ViewPort vp;
    
    public Luz(SimpleApplication c){
        direccion = new Vector3f(0.5f,-0.5f,0).normalizeLocal();
        //direccion = new Vector3f(0, -0.1f, 0);
          
        color=new ColorRGBA(1f, 1f, 1f, 1.0f);
        
        sol = new Sol();
        sol.setDirection(direccion);
        sol.setColor(color);
        c.getRootNode().addLight(sol);

        
        sombrasDia = new DirectionalLightShadowRenderer(c.getAssetManager(), 2048, 3);
        sombrasDia.setLight(sol);
        sombrasDia.setShadowIntensity(0.3f);
        c.getViewPort().addProcessor(sombrasDia);
        
        
//        c.getViewPort().addProcessor(sombras);
        
        SSAO = new FilterPostProcessor(c.getAssetManager());
        SSAOFilter ssaoFilter = new SSAOFilter(12.94f, 43.92f, 0.33f, 0.61f);
        SSAO.addFilter(ssaoFilter);
       // c.getViewPort().addProcessor(bloom);
        
        
        ambiente = new Ambiente();
        ambiente.setColor(color);
        c.getRootNode().addLight(ambiente);
        
        this.vp=c.getViewPort();
    }

    public Vector3f getDireccion() {
        return direccion;
    }
    
    public void setDireccion(Vector3f direccion){
        this.direccion.set(direccion);
         sol.setDirection(direccion);
    }
    
    public void addLight(ILight l, Game g){
        PointLightShadowRenderer sombra = new PointLightShadowRenderer(g.getAssetManager(), 1024);
        sombra.setLight(l.getLuz());
        sombra.setShadowIntensity(0.2f);
       // sombras.addFilter(sombra);
        sombrasNoche.add(sombra);
    }
    
    public void onAmanacer(){
        if(sombrasNocturnas){
            for(PointLightShadowRenderer render:sombrasNoche){
                vp.removeProcessor(render);
            }
            sombrasNocturnas=false;
        }
    }
    
    public void onAnochecer(){
        if(!sombrasNocturnas){
            for(PointLightShadowRenderer render:sombrasNoche){
                vp.addProcessor(render);
            }
            sombrasNocturnas=true;
            
        } 
    }
}
