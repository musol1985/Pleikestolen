/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.gui;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.post.Filter;
import com.preikestolen.Game;
import com.preikestolen.world.elements.Terreno;
import com.preikestolen.world.elements.entorno.DiaNoche;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Checkbox;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.RollupPanel;
import com.simsilica.lemur.TabbedPanel;
import com.simsilica.lemur.props.PropertyPanel;
import com.simsilica.lemur.style.BaseStyles;
import com.simsilica.lemur.style.ElementId;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Edu
 */
public class DebugGUI  extends AbstractAppState{
    public Game app;
    
    private Container window;

  
    @Override
    public void initialize(AppStateManager stateManager, Application a) {
        this.app=(Game)a;
        BaseStyles.loadGlassStyle();
        window = new Container("glass");
        window.addChild(new Label("Debug", new ElementId("title"), "glass"));
        
        crearAgua();
        crearBloom(app.getMundoClient().entorno.bloom);
        crearDiaNoche(app.getMundoClient().entorno.diaNoche);
        crearTerreno(app.getMundoClient().terreno);
        
        window.setLocalTranslation(5, 600, 0);
        app.getGuiNode().attachChild(window);
    }
    
    
    private void crearAgua(){
        TabbedPanel waterTabs = new TabbedPanel("glass");
        RollupPanel rollup = window.addChild(new RollupPanel("Agua", waterTabs, "glass"));
        PropertyPanel properties = waterTabs.addTab("Visualization", new PropertyPanel("glass"));
        properties.setEnabledProperty(app.getMundoClient().entorno.agua.getFilter(), "enabled");        
        rollup.getTitleContainer().addChild(new Checkbox("", properties.getEnabledModel(), "glass"));
        rollup.setOpen(false);
        properties.addFloatProperty("Height", app.getMundoClient().entorno.agua.getFilter(), "waterHeight", -20, 20, 0.1f);
        properties.addFloatProperty("speed", app.getMundoClient().entorno.agua.getFilter(), "speed", 0.0f, 5, 0.1f);
        properties.addFloatProperty("Sun Scale", app.getMundoClient().entorno.agua.getFilter(), "sunScale", 0.0f, 10, 0.01f);
        properties.addFloatProperty("Foam Intensity", app.getMundoClient().entorno.agua.getFilter(), "foamIntensity", 0.0f, 5, 0.01f);
        properties.addFloatProperty("Foam Hardness", app.getMundoClient().entorno.agua.getFilter(), "foamHardness", 0.0f, 5, 0.01f);
        properties.addFloatProperty("RefractionStrength", app.getMundoClient().entorno.agua.getFilter(), "refractionStrength", 0.0f, 1, 0.01f);
        properties.addFloatProperty("ShoreHardness", app.getMundoClient().entorno.agua.getFilter(), "shoreHardness", 0.0f, 1, 0.01f);        
        
        
        properties = waterTabs.addTab("Waves", new PropertyPanel("glass"));
        properties.addBooleanProperty("Use Ripples", app.getMundoClient().entorno.agua.getFilter(), "useRipples");
        properties.addBooleanProperty("Use Specular", app.getMundoClient().entorno.agua.getFilter(), "useSpecular");
        properties.addFloatProperty("Shininess", app.getMundoClient().entorno.agua.getFilter(), "shininess", 0.0f, 1, 0.01f);
        properties.addFloatProperty("Wave Scale", app.getMundoClient().entorno.agua.getFilter(), "waveScale", 0.001f, 1, 0.001f);
        properties.addFloatProperty("Max Amplitude", app.getMundoClient().entorno.agua.getFilter(), "maxAmplitude", 0.0f, 2, 0.01f);
        properties.addFloatProperty("Normal Scale", app.getMundoClient().entorno.agua.getFilter(), "normalScale", 0.0f, 10, 0.01f);  
        
        
        properties = waterTabs.addTab("Color", new PropertyPanel("glass"));
        properties.addFloatProperty("Rojo", app.getMundoClient().entorno.agua, "rojo",0f,1f,0.01f);
        properties.addFloatProperty("Verde", app.getMundoClient().entorno.agua, "verde",0f,1f,0.01f);
        properties.addFloatProperty("Azul", app.getMundoClient().entorno.agua, "azul",0f,1f,0.01f);
                properties.addFloatProperty("X", app.getMundoClient().entorno.agua, "x",-5f,5f,0.1f);
        properties.addFloatProperty("Y", app.getMundoClient().entorno.agua, "y",-5f,5f,0.1f);
        properties.addFloatProperty("Z", app.getMundoClient().entorno.agua, "z",-5f,5f,0.1f);
    }

    private void crearBloom(Filter f){
        TabbedPanel waterTabs = new TabbedPanel("glass");
        RollupPanel rollup = window.addChild(new RollupPanel("Bloom", waterTabs, "glass"));
        rollup.setOpen(false);
        PropertyPanel properties = waterTabs.addTab("Visualization", new PropertyPanel("glass"));
        properties.setEnabledProperty(f, "enabled");        
        rollup.getTitleContainer().addChild(new Checkbox("", properties.getEnabledModel(), "glass"));
        properties.addFloatProperty("DownSamplingFactor", f, "downSamplingFactor", 0, 2, 0.001f);
        properties.addFloatProperty("blurScale", f, "blurScale", 0.0f, 10, 0.01f);
        properties.addFloatProperty("exposurePower", f, "exposurePower", 0.0f, 10, 0.01f);
        properties.addFloatProperty("ExposureCutOff", f, "exposureCutOff", 0.0f, 10, 0.01f);
        properties.addFloatProperty("BloomIntensity", f, "bloomIntensity", 0.0f, 10, 0.01f);    
    }
    
    private void crearDiaNoche(DiaNoche dn){
        TabbedPanel waterTabs = new TabbedPanel("glass");
        RollupPanel rollup = window.addChild(new RollupPanel("Atmosferico", waterTabs, "glass"));
        rollup.setOpen(false);
        PropertyPanel properties = waterTabs.addTab("Ciclo Dia Noche", new PropertyPanel("glass"));
        properties.setEnabledProperty(dn, "enabled");        
        rollup.getTitleContainer().addChild(new Checkbox("", properties.getEnabledModel(), "glass"));
        properties.addFloatProperty("hora", dn, "hora", 0, FastMath.TWO_PI, 0.01f);

    }

    private void crearTerreno(final Terreno t){
        TabbedPanel waterTabs = new TabbedPanel("glass");
        RollupPanel rollup = window.addChild(new RollupPanel("Terreno", waterTabs, "glass"));
        rollup.setOpen(false);
        PropertyPanel properties = waterTabs.addTab("Altura", new PropertyPanel("glass"));      
        properties.addFloatProperty("Amplitude", t.getFractales().altura, "amplitude", 0, 30, 1f);
        properties.addFloatProperty("Roughness", t.getFractales().altura, "roughness", 0, 3, 0.001f);
        properties.addFloatProperty("Frequency", t.getFractales().altura, "frequency", 0, 3, 0.001f);
        properties.addFloatProperty("Lacunarity", t.getFractales().altura, "lacunarity", 0, 3, 0.01f);
        properties.addFloatProperty("Octaves", t.getFractales().altura, "octaves", 0, 15, 1f);
        
        properties.addIntProperty("ThermRadius", t.getFractales().altura, "thermRadius", 0, 20, 1);
        properties.addFloatProperty("ThermTalus", t.getFractales().altura, "thermTalus", 0, 5, 0.01f);
        properties.addIntProperty("SmoothRadius", t.getFractales().altura, "smoothRadius", 0, 20, 1);
        properties.addFloatProperty("SmoothEffect", t.getFractales().altura, "smoothEffect", 0, 5, 0.01f);
        properties.addFloatProperty("PerturbMagnitude", t.getFractales().altura, "perturbMagnitude", 0, 5, 0.01f);

        Button btnGenerar=new Button("Generar", "glass");
        btnGenerar.addClickCommands(new Command<Button>() {
            public void execute(Button source) {
                t.reGenerarAltura();
                t.getM().player.debug();
                
            }
        });
        rollup.getTitleContainer().addChild(btnGenerar);
    }
}
