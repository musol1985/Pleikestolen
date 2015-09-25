/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.world.colisions;

import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.preikestolen.world.MundoClient;
import com.preikestolen.world.colisions.resolvers.ToolResolver;
import com.preikestolen.world.elements.characters.Player;
import com.preikestolen.world.elements.characters.tools.Tool;
import com.preikestolen.world.elements.terreno.elementos.Dinamico;
import com.preikestolen.world.elements.terreno.elementos.Estatico;
import com.preikestolen.world.elements.terreno.elementos.dinamicos.Tronco;
import com.preikestolen.world.elements.terreno.elementos.estaticos.Arbol;
import com.preikestolen.world.elements.terreno.elementos.estaticos.Roca;

/**
 *
 * @author Edu
 */
public class CollisionService {
    private MundoClient mundo;
    
    public CollisionService(MundoClient mundo){
        this.mundo=mundo;
    }

    public void initResolvers(CollisionManager m){
        m.addResolver(new CollisionResolver<Player>(Player.class));
        m.addResolver(new CollisionResolver<Roca>(Roca.class));
        m.addResolver(new CollisionResolver<Arbol>(Arbol.class));
        m.addResolver(new CollisionResolver<Tronco>(Tronco.class));
        m.addResolver(new ToolResolver());
    }
    
    public void colisionPlayerEstatico(Player p, Estatico e, PhysicsCollisionEvent event){
        if(p.isAutoDestino()){
            p.doAccionNet("accion1", mundo);
        }
    }
    
    public void colisionPlayerDinamico(Player p, Dinamico e, PhysicsCollisionEvent event){
        if(p.isAutoDestino()){
            e.body.setEnabled(false);
            p.parar();
            p.doAccionNet("accionRecoger", mundo);
            
        }
    }
    
    public void onPlayerRoca(Player p, Roca r, PhysicsCollisionEvent event){
        //System.out.println("Colision entre Player y roca!!!!");
        colisionPlayerEstatico(p, r, event);
    }
    
    public void onPlayerArbol(Player p, Arbol a, PhysicsCollisionEvent event){
        colisionPlayerEstatico(p, a, event);
    }
    
    public void onPlayerDinamico(Player p, Tronco d, PhysicsCollisionEvent event){
        d.controlar(mundo);
        colisionPlayerDinamico(p, d, event);
    }
    
    public void onRocaTool(Tool t, Roca r,  PhysicsCollisionEvent event){
        //((Player)t.getPlayer()).addColision(r);
    }
    
    public void onArbolTool(Tool t, Arbol a,  PhysicsCollisionEvent event){
       // ((Player)t.getPlayer()).addColision(a);
    }
    
    public void onDinamico(Tronco t1, Tronco t2,  PhysicsCollisionEvent event){
        if(t1.isControloYo() && !t2.isControloYo()){
            t2.controlar(mundo, t1.getTimeout());
        }else if(!t1.isControloYo() && t2.isControloYo()){
            t1.controlar(mundo, t2.getTimeout());
        }
    }
}
