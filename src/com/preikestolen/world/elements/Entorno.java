/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.world.elements;

import com.preikestolen.world.elements.entorno.Agua;
import com.preikestolen.world.elements.entorno.Luz;
import com.jme3.app.SimpleApplication;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.scene.Node;
import com.jme3.util.SkyFactory;
import com.preikestolen.world.MundoClient;
import com.preikestolen.world.elements.entorno.DiaNoche;


/**
 *
 * @author Edu
 */
public class Entorno {
    public Node cielo;    
    public BloomFilter bloom;

    public Agua agua;
    public Luz luz;
    public DiaNoche diaNoche;
    
    public Entorno(SimpleApplication c, MundoClient mundo){     
        cielo=new Node();
        cielo.attachChild(SkyFactory.createSky(c.getAssetManager(), "Textures/sky.jpg", true));
        mundo.escena.attachChild(cielo);
        
        luz=new Luz(c);        
        
        FilterPostProcessor fpp = new FilterPostProcessor(c.getAssetManager());

        bloom = new BloomFilter();
        bloom.setDownSamplingFactor(1);
        bloom.setBlurScale(1.37f);
        bloom.setExposurePower(4.30f);
        bloom.setExposureCutOff(0.2f);
        bloom.setBloomIntensity(1.45f);
        fpp.addFilter(bloom);


        c.getViewPort().addProcessor(fpp);
        
        agua=new Agua(c, this, mundo);
        
        diaNoche=new DiaNoche(luz, c.getAssetManager());
    }

    
}
