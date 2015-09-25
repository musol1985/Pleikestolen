/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.world.elements.characters.tools;

import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.math.FastMath;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import com.preikestolen.Game;
import com.preikestolen.persist.dao.CosaDAO;
import com.preikestolen.world.elements.characters.PlayerCommon;

/**
 *
 * @author Edu
 */
public abstract class Tool extends Node implements Control{
    protected PlayerCommon player;
    protected boolean local;
    protected float vida=100;
    protected boolean control;
    protected GhostControl ghost;
    protected CosaDAO dao;
    
    public Tool(Game g, boolean local, Node tool, boolean esControl, CosaDAO dao){                
        tool.rotate(0, FastMath.PI, 0);
        attachChild(tool);
        
        this.dao=dao;
        this.local=local;
        this.control=esControl;
        
        //PhysicsControl fTool=tool.getControl(PhysicsControl.class);
        if(local){
            Node colision=(Node)tool.getChild("colision");
            Geometry geo=(Geometry)colision.getChild(0);

            ghost=new GhostControl(CollisionShapeFactory.createBoxShape(geo));
            colision.addControl(ghost);                
        }                
    }
    
    public void attach(Game g, PlayerCommon player){
        if(local)
            g.getFisicas().add(getChild("colision"));
        this.player=player;
        onAttached(g);
        
        if(control)
            addControl(this);
    }
    
    public void dettach(Game g, PlayerCommon player){        
        if(local)
            g.getFisicas().remove(getChild("colision"));
        
        onDettached(g);
        
        if(control)
            removeControl(this);
        
        if(getParent()!=null)
            getParent().detachChild(this);
    }

    public PlayerCommon getPlayer() {
        return player;
    }
    
    protected abstract void onAttached(Game g);
    protected abstract void onDettached(Game g);

    @Override
    public void render(RenderManager rm, ViewPort vp){
        
    }

    @Override
    public void update(float tpf){
        
    }

    @Override
    public void setSpatial(Spatial spatial){
        
    }

    @Override
    public Control cloneForSpatial(Spatial spatial){
        return null;
    }       
    
    public static Tool getTool(Game game, CosaDAO tool){
        if(tool!=null){
            if(tool.isHacha()){
                return new Axe(game, true, tool);
            }else if(tool.isAntorcha()){
                return new Torch(game, true, tool);
            }
        }
        return null;
    }
}
