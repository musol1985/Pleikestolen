/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.net.sync;

import com.preikestolen.world.elements.controllers.interfaces.INetPosSync;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.preikestolen.Game;
import com.preikestolen.net.msg.game.MsgPosition;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Edu
 */
public class NetSyncState extends AbstractAppState implements Runnable{
    public static final int TIMEOUT=10;//ms
    
    private long time;
    private List<INetPosSync> elements=new ArrayList<INetPosSync>();
    private Game game;
    
    private Thread thd;
    private boolean run;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app); 
        game=(Game)app;
        
        thd=new Thread(this);
        thd.setDaemon(true);
        run=true;
        thd.start();
    }

    public void addSyncPos(INetPosSync elemento){
        elements.add(elemento);
    }
    
    public void removeSyncPos(INetPosSync elemento){
        elements.remove(elemento);
    }
    
    
     @Override
    public void cleanup() {
         run=false;
        elements.clear();
        super.cleanup(); //To change body of generated methods, choose Tools | Templates.        
    }
    
    

    @Override
    public void update(float tpf) {
        
    }

    public void run() {
        while(run){
            try{
                if(System.currentTimeMillis()-time>TIMEOUT){
                    System.out.println("Sincronizando posiciones!!"+elements.size());
                    for(int i=0;i<elements.size();i++){
                        INetPosSync elemento=elements.get(i);
                        game.getNet().send(new MsgPosition(elemento.getID(), elemento.getPosicion()));
                    }
                    Thread.sleep(100);
                    time=System.currentTimeMillis();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
