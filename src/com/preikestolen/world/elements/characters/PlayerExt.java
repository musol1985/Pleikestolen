/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.world.elements.characters;

import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.scene.Node;
import com.preikestolen.Game;
import com.preikestolen.persist.dao.JugadorDAO;
import com.preikestolen.world.MundoClient;
import com.preikestolen.world.elements.characters.tools.Axe;

/**
 *
 * @author Edu
 */
public class PlayerExt extends CharacterSync<RigidBodyControl>{    
    
    public PlayerExt(JugadorDAO jugador, MundoClient m){
        super("NodePlayer_"+jugador.getNombre(), m, jugador);        
    }

    @Override
    public void initPlayer(Node n, Game g) {
        super.initPlayer(n, g); //To change body of generated methods, choose Tools | Templates.
        //setTool(new Axe(g, false, null), g);
    }

    @Override
    public void onMover() {
        animAndar(posicion.getVelocity());       
    }        

    @Override
    public void onAccionDone(String accion) {
        
    }

    @Override
    public RigidBodyControl getBody(Node n) {
        RigidBodyControl b= new RigidBodyControl(new CapsuleCollisionShape(1, 3), 50);    
        b.setKinematic(true);
        return b;
    }    
}
