/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.persist.dao;

import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;
import com.preikestolen.common.Cosas;
import com.preikestolen.common.TipoCosa;

/**
 *
 * @author Edu
 */
@Serializable
public class EstaticoDAO implements java.io.Serializable{
    public Vector3f pos;
    public float ang;
    public int subtipo;
    public Cosas.Tipo tipo;
    public String id;
    public int vida;
    
    public EstaticoDAO(){
        
    }

    public EstaticoDAO(String id, Vector3f pos, Cosas.Tipo tipo, float ang, int subtipo, int vida) {
        this.id=id;
        this.pos = pos;
        this.ang = ang;
        this.subtipo = subtipo;
        this.tipo=tipo;
        this.vida=vida;
    }
    
    public boolean isArbol(){
        return tipo==Cosas.Tipo.ARBOL;
    }
    
    public boolean isSeta(){
        return tipo==Cosas.Tipo.SETA;
    }
    
    public boolean isRoca(){
        return tipo==Cosas.Tipo.ROCA;
    }
    
    public boolean isFlor(){
        return tipo==Cosas.Tipo.FLOR;
    }
    
    public boolean isHierba(){
        return tipo==Cosas.Tipo.HIERBA;
    }

}
