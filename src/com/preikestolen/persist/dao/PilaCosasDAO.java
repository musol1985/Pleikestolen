/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.persist.dao;

import com.jme3.network.serializing.Serializable;
import com.preikestolen.common.Cosas;
import com.preikestolen.common.TipoCosa;

/**
 *
 * @author Edu
 */
@Serializable
public class PilaCosasDAO implements java.io.Serializable, IDaoGui{
    public int id;
    public CosaDAO cosa;
    public int cantidad;
    
    public PilaCosasDAO(){
        
    }
    
    public PilaCosasDAO(int id){
        this.id=id;
    }

    public PilaCosasDAO(int id, CosaDAO cosa, int cantidad) {
        this.id=id;
        this.cosa=cosa;
        this.cantidad=cantidad;
    }

    public CosaDAO getCosa() {
        return cosa;
    }

    public void setCosa(CosaDAO cosa) {
        this.cosa = cosa;
    }

    public int getCantidad() {
        if(isTool())
            return cosa.getCantidad();
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    public boolean isPuedeApilar(Cosas.Tipo tipo){
        return (!isVacio() && cosa.tipo==tipo && cantidad<Cosas.getTipo(tipo).pila);
    }
    
    public int addCosa(CosaDAO cosa){
        return addCosa(cosa, 1);
    }
    
    public int addCosa(CosaDAO cosa, int _cantidad){
        int res=0;
        
        if(_cantidad==0)
            cantidad++;
        
        if(cantidad+_cantidad>cosa.getMaxPila()){
            res=cantidad+_cantidad-cosa.getMaxPila();
            cantidad=cosa.getMaxPila();
        }else{
            cantidad+=_cantidad;
        }
        
        
        if(this.cosa!=null){
            this.cosa.vida=(this.cosa.vida+cosa.vida)/2;
        }else{
            this.cosa=cosa;
        }
        
        return res;
    }
    
    public CosaDAO getUnaCosa(){
        if(cosa!=null){
            CosaDAO res=cosa;
            if(cantidad>1){
                res=new CosaDAO(cosa.tipo, this.cosa.vida);                
            }else{                
                cosa=null;                
            }
            cantidad--;
            return res;
        }
        return null;
    }

    @Override
    public boolean isVacio() {
        return cosa==null;
    }

    @Override
    public Cosas.Tipo getTipo() {
        if(!isVacio())
            return cosa.tipo;
            
        return null;
    }

    @Override
    public boolean isTool() {
        if(!isVacio())
            return cosa.isTool();
        return false;
    }
    
    public void vaciar(){
        cosa=null;
        cantidad=0;
    }
}
