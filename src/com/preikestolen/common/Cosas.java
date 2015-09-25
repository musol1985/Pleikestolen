/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.common;

import java.util.HashMap;

/**
 *
 * @author Edu
 */
public class Cosas {
    public enum Tipo{
        ARBOL, ROCA, SETA, FLOR, HIERBA, TRONCO, HACHA, ANTORCHA
    }
    
    public final static Tipo[] tipos = Tipo.values();

    public final static HashMap<Integer, TipoCosa> cosas=new HashMap<Integer, TipoCosa>();
    
    
    static{
        cosas.put(Tipo.ARBOL.ordinal(), new TipoCosa(20, 100));
        cosas.put(Tipo.ROCA.ordinal(), new TipoCosa(20, 100));
        cosas.put(Tipo.SETA.ordinal(), new TipoCosa(20, 100));
        cosas.put(Tipo.FLOR.ordinal(), new TipoCosa(20, 100));
        cosas.put(Tipo.HIERBA.ordinal(), new TipoCosa(20, 100));
        cosas.put(Tipo.TRONCO.ordinal(), new TipoCosa(20, 100));
        cosas.put(Tipo.HACHA.ordinal(), new TipoCosa(1, 100));
        cosas.put(Tipo.ANTORCHA.ordinal(), new TipoCosa(1, 100));
    }
    
    public static TipoCosa getTipo(Tipo id){        
        return cosas.get(id.ordinal());
    }
    
    public static Tipo getTipo(int id){        
        return tipos[id];
    }
}
