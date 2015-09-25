/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.persist.dao;

import com.jme3.network.serializing.Serializable;
import com.preikestolen.common.Cosas;
import com.preikestolen.gui.GameGUI1;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Edu
 */
@Serializable
public class JugadorDAO implements java.io.Serializable{
    public static final String ID="JUGADOR";
    public static final int SIZE_COSAS=8;
    
    private String nombre;
    private boolean owner;
    private PosicionDAO posicion;
    private List<PilaCosasDAO> cosas;
    
    
    public JugadorDAO() {
        iniciarCosas();
    }

    public JugadorDAO(String nombre, boolean owner) {
        this.nombre = nombre;
        this.owner = owner;
        posicion=new PosicionDAO();
        
        iniciarCosas();
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public PosicionDAO getPosicion() {
        return posicion;
    }

    public void setPosicion(PosicionDAO pos) {
        this.posicion = pos;
    }

    public void addCosa(EstaticoDAO e, GameGUI1 gui){        
        PilaCosasDAO pila=getPilaByTipo(e.tipo);
        int paso=0;
        if(pila!=null){
            System.out.println("OnCoger cosa!!"+pila.cantidad+"   - - "+pila);
            if(e instanceof DinamicoDAO){                
                DinamicoDAO dina=(DinamicoDAO)e;
                System.out.println("OnCoger cosa!! es dinamica "+dina.cantidad);
                do{
                    dina.cantidad=pila.addCosa(new CosaDAO(e), dina.cantidad);
                    System.out.println("OnCoger cosa!! "+dina.cantidad+" "+pila);
                    if(gui!=null)
                        gui.onCogerCosa(pila);
                    
                    pila=getPilaByTipo(e.tipo);
                    if(pila!=null && dina.cantidad>0){
                        //Hay que clonar el dinamico
                        paso++;
                        dina=dina.clonar(paso);
                    }
                }while(pila!=null && dina.cantidad>0);
            }else if(e instanceof EstaticoDAO){
                pila.addCosa(new CosaDAO(e));                            
            }     
            
        }else{
            System.out.println("no hay sitio!!!");
        }
    }
    
    private void iniciarCosas(){
        if(cosas==null){
            cosas=new ArrayList<PilaCosasDAO>(SIZE_COSAS);
            for(int i=0;i<SIZE_COSAS;i++){
                cosas.add(new PilaCosasDAO(i));
            }
        }
    }
    
    public PilaCosasDAO getPila(int index){
        return cosas.get(index);
    }
    
    public PilaCosasDAO getPilaByTipo(Cosas.Tipo tipo){
        PilaCosasDAO vacia=null;
        for(PilaCosasDAO pila:cosas){
            if(vacia==null && pila.isVacio())
                vacia=pila;
            
            if(pila.isPuedeApilar(tipo))
                return pila;
        }
        return vacia;
    }
    

}
