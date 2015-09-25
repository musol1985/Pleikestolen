/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.persist;

import com.preikestolen.persist.dao.CeldaDAO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 *
 * @author Edu
 */
public abstract class Persistable implements Serializable {
    protected String id;
    protected boolean changed;
    
    public Persistable(String id){
        this.id=id;
    }
    
    public String getID(){
        return id;
    }
    
    public abstract String getFileName();
 
    public void save(){
        if(changed){
            changed=false;
            try{
                FileOutputStream fileOut = new FileOutputStream(getFileName());
                GZIPOutputStream gz = new GZIPOutputStream(fileOut);
                ObjectOutputStream out = new ObjectOutputStream(gz);
                out.writeObject(this);
                out.close();
                gz.close();
                fileOut.close();
                System.out.println("Serialized data "+getFileName());
                if(this instanceof CeldaDAO){
                    System.out.println("Estaticos de la celda: "+id+" "+((CeldaDAO)this).estaticos.size());
                }
             }catch(IOException i){
                 i.printStackTrace();
             }
        }
    }
    
    
    public static <T> T cargar(String file){
        T obj=null;
        if(new File(file).exists()){
            try{
               FileInputStream fileIn = new FileInputStream(file);
               GZIPInputStream gz = new GZIPInputStream(fileIn);
               ObjectInputStream in = new ObjectInputStream(gz);
               obj = (T) in.readObject();
               in.close();
               fileIn.close();
              // System.out.println("Persistencia cargada!"+file);
            }catch(Exception i){
               i.printStackTrace();
            }
        }
        return obj;
    }

    public void change() {
        this.changed = true;
    }
    
    
}
