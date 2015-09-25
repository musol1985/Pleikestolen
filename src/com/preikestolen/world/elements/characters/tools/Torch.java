/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.world.elements.characters.tools;

import com.jme3.effect.ParticleEmitter;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.preikestolen.Game;
import com.preikestolen.persist.dao.CosaDAO;
import com.preikestolen.world.elements.terreno.elementos.ILight;
import java.util.Random;

/**
 *
 * @author Edu
 */
public class Torch extends Tool implements ILight{
    private PointLight l=new PointLight();
    private Spatial posicionLuz;
    private Random rnd=new Random();
    private ParticleEmitter pe;

    public Torch(Game g, boolean local, CosaDAO dao) {
        super(g, local, getNode(g), true, dao);
        
        pe=(ParticleEmitter)((Node)g.getAssetManager().loadModel("Models/fx/Fuego.j3o")).getChild(0);
        
        l=new PointLight();
        l.setRadius(50);
        l.setColor(ColorRGBA.Orange);
        
        posicionLuz=getChild("colision");
        
        ((Node)posicionLuz).attachChild(pe);        
        
    }
    
    private static Node getNode(Game g){
        return (Node)((Node)g.getAssetManager().loadModel("Models/tools/torch.j3o")).getChild("torch");
    }
    
    @Override
    protected void onAttached(Game g) {
        g.getMundoClient().escena.addLight(l);
        pe.emitAllParticles();
        //g.getMundoClient().addLuz(this);
    }

    @Override
    protected void onDettached(Game g) {
        g.getMundoClient().escena.removeLight(l);        
    }

    @Override
    public void update(float tpf) {
        l.setPosition(posicionLuz.getWorldTranslation().add(0, 2, 0));        
        l.setRadius(50+rnd.nextInt(50));
    }

    @Override
    public PointLight getLuz() {
        return l;
    }

    @Override
    public boolean isLighting() {
        return true;
    }
}
