/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.server;

/**
 *
 * @author Edu
 */
public class Baldosa {
    public enum TIPO{
        HIERBA, BOSQUE, ARENA, AGUA
    }
    
    public float altura;
    public TIPO tipo;
    
    public Baldosa(float altura, TIPO tipo){
        this.altura=altura;
        this.tipo=tipo;
    }
    
    public boolean isAgua(){
        return tipo==TIPO.AGUA;
    }
}
