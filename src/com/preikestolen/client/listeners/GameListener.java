/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.client.listeners;

import com.jme3.network.Client;
import com.jme3.network.Filters;
import com.jme3.network.HostedConnection;
import com.preikestolen.Game;
import com.preikestolen.common.MensajesListener;
import com.preikestolen.net.msg.game.MsgAccion;
import com.preikestolen.net.msg.game.MsgCogerDinamico;
import com.preikestolen.net.msg.game.MsgDinamicoPosition;
import com.preikestolen.net.msg.game.MsgDinamicoSleep;
import com.preikestolen.net.msg.game.MsgOnCelda;
import com.preikestolen.net.msg.game.MsgOnMatarEstatico;
import com.preikestolen.net.msg.game.MsgPosition;
import com.preikestolen.net.msg.game.MsgSetTool;
import com.preikestolen.net.msg.game.MsgSoltarDinamico;
import com.preikestolen.persist.dao.CeldaDAO;
import com.preikestolen.world.elements.terreno.Celda;
import com.preikestolen.world.elements.terreno.elementos.Dinamico;
import java.util.concurrent.Callable;

/**
 *
 * @author Edu
 */
public class GameListener extends MensajesListener<Client>{
    private Game game;
    

    public GameListener(Game game){
        super();
        this.game=game;
    }
    
    public void onCelda(Client client, final MsgOnCelda msg){
        game.getMundoClient().executor.execute(new Runnable() {

            public void run() {
                
        CeldaDAO celda=null;
        final Celda c=game.getMundoClient().terreno.getCelda(msg.celda);
        
        if(!msg.celda.isCachearLocal()){
           // System.out.println("OnCelda "+msg.celda.getID()+"       arboles: "+msg.celda.estaticos.size());            
            
            //guardar en cache local
            celda=msg.celda;            
            c.setDao(celda);
            celda.change();
            celda.save();            
        }else{            
         //   System.out.println("OnCelda "+msg.celda.getID()+" cachear desde local");
            
            celda=CeldaDAO.cargarById(msg.celda.getID());
            //cargar desde cache local
        }
        
        //Cargar celda al terreno
        final CeldaDAO fCelda=celda;
        game.enqueue(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                game.getMundoClient().terreno.onCelda(fCelda, c);
                return true;
            }
        });
         }
        });
    }
    
    
    
    public void onPosition(Client client, final MsgPosition m){   
        game.enqueue(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                game.getMundoClient().netSync(m);
                return true;
            }
        });
        
    }
    
    
    public void onMatarEstatico(Client client, final MsgOnMatarEstatico m){
        final Celda c=game.getMundoClient().terreno.getCelda(m.celdaId);
        if(c!=null){
            c.getDao().matarEstatico(m.objetoId, m.time, m.added);
            game.enqueue(new Callable<Boolean>() {
                public Boolean call() throws Exception {
                    c.matarEstatico(m.objetoId, m.added, m.controlasTu);                   
                    return true;
                }
            });
        }
    }
    
    public void onPositionDinamico(Client client, final MsgDinamicoPosition m){  
        System.out.println("OnPositionDin "+m.celda+" "+m.id);
        final Celda c=game.getMundoClient().terreno.getCelda(m.celda); 
        if(c!=null){
            game.enqueue(new Callable<Boolean>() {
                public Boolean call() throws Exception {
                    c.posicionarDinamico(m.id, m.posicion, m.time);
                    return true;
                }
            });
        }        
    }
    
    
    public void onSleepDinamico(Client client, final MsgDinamicoSleep m){  
        System.out.println("OnPositionDin "+m.celda+" "+m.id);
        final Celda c=game.getMundoClient().terreno.getCelda(m.celda); 
        if(c!=null){
            game.enqueue(new Callable<Boolean>() {
                public Boolean call() throws Exception {
                    c.sleepDinamico(m.id, m.posicion, m.time);
                    return true;
                }
            });
        }        
    }
    
    public void onCogerDinamico(Client client, final MsgCogerDinamico m){  
        final Celda c=game.getMundoClient().terreno.getCelda(m.celda); 
        if(c!=null){
            game.enqueue(new Callable<Boolean>() {
                public Boolean call() throws Exception {
                    Dinamico d=c.removeDinamico(m.id);                                        
                    return true;
                }
            });
        }       
    }
    
    
    public void onAccion(Client client, final MsgAccion m){  
        game.enqueue(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                game.getMundoClient().onNetAccion(m);
                return true;
            }
        });       
    }
    
    public void onSetTool(Client client, final MsgSetTool m){
        game.enqueue(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                game.getMundoClient().onNetSetTool(m);
                return true;
            }
        });
    }
    
    public void onSoltarDinamico(Client client, final MsgSoltarDinamico m){  
        /*game.enqueue(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                game.getMundoClient().onNetAccion(m);
                return true;
            }
        }); */      
    }
}
