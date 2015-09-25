/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.world.colisions;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Edu
 */
public class CollisionResolver<T> {
    protected Class c;
    protected Spatial n;
    
    public CollisionResolver(Class c){
        this.c=c;
    }
    

    public T getItem(){
        return (T)getParent(n);
    }
    
    public boolean is(Spatial spatial){
        try{
            if(getParent(spatial).getClass()==c){
                n=spatial;
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public Spatial getParent(Spatial n){
        return n;
    }
    

}
