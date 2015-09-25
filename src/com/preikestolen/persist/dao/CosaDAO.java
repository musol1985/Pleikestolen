/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.persist.dao;

import com.jme3.network.serializing.Serializable;
import com.preikestolen.common.Cosas;


/**
 *
 * @author Edu
 */
@Serializable
public class CosaDAO implements java.io.Serializable, IDaoGui{
    
    public Cosas.Tipo tipo;
    public int vida;
    
    public CosaDAO(){
        
    }

    public CosaDAO(Cosas.Tipo tipo, int vida) {
        this.tipo=tipo;
        this.vida=vida;
    }
    
    public CosaDAO(EstaticoDAO e){
        tipo=e.tipo;
        vida=e.vida;
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
    
    public boolean isTool(){
        return tipo==Cosas.Tipo.HACHA || tipo==Cosas.Tipo.ANTORCHA;
    }
    
    public boolean isHacha(){
        return tipo==Cosas.Tipo.HACHA;
    }
    
    public boolean isAntorcha(){
        return tipo==Cosas.Tipo.ANTORCHA;
    }

    @Override
    public boolean isVacio() {
        return false;
    }

    @Override
    public int getCantidad() {
        if(!isTool())
            return 1;
        return vida;
    }

    @Override
    public Cosas.Tipo getTipo() {
        return tipo;
    }
    
    public int getMaxPila(){
        return Cosas.getTipo(getTipo()).pila;
    }
}
