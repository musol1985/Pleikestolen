/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.world;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.preikestolen.common.CacheCeldas;
import com.preikestolen.persist.dao.MundoDAO;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 *
 * @author Edu
 */
public class MundoServer extends AbstractAppState{
    private SimpleApplication app;
    private MundoDAO dao;
    
    private CacheCeldas celdas;
    
    public ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(6);
    
    public MundoServer(MundoDAO dao){
        this.dao=dao;
    }
    
    
    
    @Override
    public void initialize(AppStateManager stateManager, Application a) {
        super.initialize(stateManager, a); //To change body of generated methods, choose Tools | Templates.
        this.app=(SimpleApplication)a;
        
        celdas=new CacheCeldas(dao);
        
        app.getRootNode().addControl(celdas);
    }

    @Override
    public void cleanup() {
        celdas.guardar();
        app.getRootNode().removeControl(celdas);
        super.cleanup(); //To change body of generated methods, choose Tools | Templates.        
    }
    
    

    @Override
    public void update(float tpf) {
        
    }
    
    public CacheCeldas getCache(){
        return celdas;
    }
    
}
