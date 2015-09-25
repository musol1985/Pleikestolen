/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.gui;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector3f;
import com.jme3.texture.Texture;
import com.preikestolen.Game;
import com.preikestolen.gui.elementos.CuadrosCosas;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.Panel;
import com.simsilica.lemur.component.BorderLayout;
import com.simsilica.lemur.component.QuadBackgroundComponent;
import com.simsilica.lemur.style.BaseStyles;



/**
 *
 * @author Edu
 */
public class GameGUI  extends AbstractAppState{
    public Game app;
    
    private Container window;
    private CuadrosCosas cuadros;
  
    @Override
    public void initialize(AppStateManager stateManager, Application a) {
        this.app=(Game)a;
        BaseStyles.loadGlassStyle();
        window = new Container();
        
        QuadBackgroundComponent img=new QuadBackgroundComponent(GuiGlobals.getInstance().loadTexture("Interface/barraCosas.png", false, false));
        Panel p=window.addChild(new Panel(img.getTexture().getImage().getWidth(), img.getTexture().getImage().getHeight()));
        
        p.setBackground(img);
        
        
        //window.setBorder(null);
       // window.setPreferredSize(new Vector3f(200,100,0));
        //window.setSize(new Vector3f(200,100,0));        
        window.setLocalTranslation(300, img.getTexture().getImage().getHeight(), 0);
        app.getGuiNode().attachChild(window);
    }



}
