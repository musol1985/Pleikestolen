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
public class PosicionRotacionDAO implements java.io.Serializable{
    public static final String ID="POSICION";
    

    private Vector3f position;
    private Vector3f velocity;
    private Vector3f rotacion;

    public PosicionRotacionDAO() {
        this.position=new Vector3f();
        this.velocity=new Vector3f();
        this.rotacion=new Vector3f();
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

    public Vector3f getRotacion() {
        return rotacion;
    }

    public void setRotacion(Vector3f rotacion) {
        this.rotacion = rotacion;
    }

    public void setRotacion(float x, float y, float z) {
        this.rotacion.x=x;
        rotacion.y=y;
        rotacion.z=z;
    }
    
}
