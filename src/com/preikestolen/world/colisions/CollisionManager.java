/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.world.colisions;

import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.control.GhostControl;
import com.preikestolen.world.MundoClient;
import com.preikestolen.world.elements.characters.Player;
import com.preikestolen.world.elements.terreno.Celda;
import com.preikestolen.world.elements.terreno.elementos.Estatico;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Edu
 */
public class CollisionManager implements PhysicsCollisionListener {
    private CollisionService service;
    private List<CollisionResolver> resolvers=new ArrayList<CollisionResolver>();
    private HashMap<String, Method> metodos=new HashMap<String, Method>();
    
    public CollisionManager(MundoClient mundo){
        this.service=new CollisionService(mundo);
        
        service.initResolvers(this);
        
        
        for(Method m:service.getClass().getMethods()){
            if(m.getName().startsWith("on")){                
                metodos.put(getId(m), m);
            }
        }        
    }
    
    private String getId(Method m){
        return m.getParameterTypes()[0].getSimpleName()+"#"+m.getParameterTypes()[1].getSimpleName();
    }
    
    private String getId(CollisionResolver cr1, CollisionResolver cr2){
        return cr1.getItem().getClass().getSimpleName()+"#"+cr2.getItem().getClass().getSimpleName();
    }
    
    
    public void addResolver(CollisionResolver resolver){
        resolvers.add(resolver);
    }

    @Override
    public void collision(PhysicsCollisionEvent event) {
        CollisionResolver r1=null;
        CollisionResolver r2=null;
        
        if(event.getObjectA() instanceof GhostControl || event.getObjectB() instanceof GhostControl)
            return;
        
        for(CollisionResolver resolver:resolvers){
            if(resolver.is(event.getNodeA()) || resolver.is(event.getNodeB())){
                if(r1==null){
                    r1=resolver;
                }else{
                    r2=resolver;
                }
            }

            if(r2!=null)
                break;
        }
        
        if(r1!=null && r2!=null){
            //service.onColision(r1.getItem(), r2.getItem(), event);
            Method m=metodos.get(getId(r1, r2));
            if(m!=null){
                try {
                    m.invoke(service, r1.getItem(), r2.getItem(), event);
                } catch (Exception ex) {
                    Logger.getLogger(CollisionManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                m=metodos.get(getId(r2, r1));
                if(m!=null){
                    try {
                        m.invoke(service, r2.getItem(), r1.getItem(), event);
                    } catch (Exception ex) {
                        Logger.getLogger(CollisionManager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
    
    
        
    private Player getPlayer(PhysicsCollisionEvent e){
        if(e.getNodeA() instanceof Player)
                return (Player)e.getNodeA();
        if(e.getNodeB() instanceof Player)
                return (Player)e.getNodeB();
        return null;
    }
    
    private Celda getCelda(PhysicsCollisionEvent e){
        if(e.getNodeA()!=null && e.getNodeA().getUserData(Celda.CELDA)!=null)
                return (Celda)e.getNodeA().getUserData(Celda.CELDA);
        if(e.getNodeB()!=null && e.getNodeB().getUserData(Celda.CELDA)!=null)
                return (Celda)e.getNodeB().getUserData(Celda.CELDA);
        return null;
    }
    
    private Estatico getEstatico(PhysicsCollisionEvent e){
        if(e.getNodeA() instanceof Estatico)
                return (Estatico)e.getNodeA();
        if(e.getNodeB() instanceof Estatico)
                return (Estatico)e.getNodeB();
        return null;
    }
}
