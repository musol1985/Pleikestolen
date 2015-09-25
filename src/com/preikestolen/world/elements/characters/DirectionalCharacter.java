/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.world.elements.characters;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;
import com.jme3.bullet.control.AbstractPhysicsControl;
import com.jme3.bullet.control.PhysicsControl;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.preikestolen.Game;
import com.preikestolen.world.MundoClient;

/**
 *
 * @author Edu
 */
public abstract class DirectionalCharacter<T extends PhysicsControl> extends Node implements AnimEventListener{
    protected Node gNode;
    protected float ang=0;
    protected Vector3f direccion=new Vector3f();
    protected AnimChannel chanel;
    
    protected float v=20;
    protected T body;
    
    public DirectionalCharacter(String nombre){
        super(nombre);
        gNode=new Node();
        attachChild(gNode);
    }
    
     public void iniciar(Game game, MundoClient mundo, String model, Vector3f posicion){
        Node n=(Node)game.getAssetManager().loadModel(model);                

        if(posicion.y==0){
            posicion.x=1;
            posicion.z=1;
            posicion.y=30;//mundo.terreno.getAltura(posicion)+10;
        }
        move(posicion);
        
        gNode.attachChild(n);
        
        initPlayer(n, game);
        body=getBody(n);       

        addControl(body);
        
        
        AnimControl anim=n.getChild(0).getControl(AnimControl.class);
        chanel=anim.createChannel(); 
        anim.addListener(this);

        rotar(0);
    }
    
    public abstract T getBody(Node n);
    public abstract void initPlayer(Node n, Game g);
    
    
    public void attach(MundoClient mundo){
        mundo.escena.attachChild(this);
        mundo.getApp().getFisicas().add(this);
    }
    
    public void dettach(MundoClient mundo){
        mundo.escena.detachChild(this);
        mundo.getApp().getFisicas().remove(this);
    }
    
        
    public void rotar(float a){
        ang+=a;
        ang=getAngle(ang);
        gNode.setLocalRotation(new Quaternion().fromAngles(0, ang+FastMath.HALF_PI, 0));        
    }
    
    private float getAngle(float in){
        if(in> FastMath.TWO_PI){
            in=in-FastMath.TWO_PI;
        }else if(in<0){
            in=FastMath.TWO_PI+in;
        }
        return in;
    }    
    
    public T getBody(){
        return body;
    }
    
    public void actualizarDireccion(Vector3f direccion, float tpf){
        this.direccion=direccion.clone();
        
        float angulo=direccion.normalize().angleBetween(new Vector3f(1,0,0));
        if(direccion.z>0)
            angulo*=-1;
        
        if(angulo<0)
            angulo=FastMath.TWO_PI+angulo;
        
        float izq=getAngle(angulo-ang);
        float der=getAngle(ang-angulo);
        float dif=Math.abs(ang-angulo);

        if(dif>0.1f){
            if(der>izq){
                rotar(10*tpf);
            }else{
                rotar(-10*tpf);
            }
        }

    }
    
    public Vector3f getDireccion(){
        return direccion;
    }
    
    
    protected void setAnim(String name, boolean loop){
        if(!(name.equals(chanel.getAnimationName()))){
            chanel.setAnim(name);
            if(loop){
                chanel.setLoopMode(LoopMode.Loop);
            }else{
                chanel.setLoopMode(LoopMode.DontLoop);
            }
        }
    }
}
