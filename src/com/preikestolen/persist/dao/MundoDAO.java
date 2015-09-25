/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.persist.dao;

import com.jme3.network.serializing.Serializable;
import com.preikestolen.persist.Persistable;
import static com.preikestolen.persist.dao.CeldaDAO.getFileName;
import java.io.File;
import java.util.List;

/**
 *
 * @author Edu
 */
@Serializable
public class MundoDAO extends Persistable{
    public static String PATH="worlds/";
    public static final String FILENAME=PATH+"#/world.id";
    private List<JugadorDAO> jugadores;
    
    private int dias;
    private long seed;

    public MundoDAO(String id) {
        super(id);        
    }
    
    public MundoDAO(){
        super("");
    }

    public List<JugadorDAO> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<JugadorDAO> jugadores) {
        this.jugadores = jugadores;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }
    
    
    public void crear(){
        seed=System.currentTimeMillis();
        dias=0;
        
        change();
    }

    @Override
    public String getFileName() {
        return getFileName(id);
    }
    
    public static String getFileName(String id) {
        return FILENAME.replace("#", id);
    }
    
    public static MundoDAO cargarById(String id){
        return Persistable.cargar(getFileName(id));
    }
    
    
    public void crearClienteCache(){
        File dir=new File(PATH+id);
        if(!dir.exists()){
            dir.mkdirs();
        }
    }
}
