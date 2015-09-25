/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
public class DinamicoDAO extends EstaticoDAO{
    public PosicionRotacionDAO posicion;
    public int cantidad;
    
    public DinamicoDAO() {
    }

    public DinamicoDAO(String id, PosicionRotacionDAO pos, Cosas.Tipo tipo, float ang, int subtipo, int vida, int cantidad) {
        super(id, pos.getPosition(), tipo, ang, subtipo, vida);
        this.posicion=pos;
        this.cantidad=cantidad;
    }
    
    public boolean isTronco(){
        return tipo==Cosas.Tipo.TRONCO;
    }
    
    
    public DinamicoDAO clonar(int paso){
        return new DinamicoDAO(id+paso, posicion, tipo, ang, subtipo, vida, cantidad);        
    }
}
