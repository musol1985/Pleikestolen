/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.persist.dao;

import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author Edu
 */
@Serializable
public class PosicionDAO implements java.io.Serializable{
    public static final String ID="POSICION";
    

    private Vector3f position;
    private Vector3f velocity;
    private Vector3f direction;

    public PosicionDAO() {
        this.position=new Vector3f();
        this.velocity=new Vector3f();
        this.direction=new Vector3f();
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3f velocity) {
        this.velocity = velocity;
    }

    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }
    
}
