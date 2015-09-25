/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.gui.elementos;

import com.jm3.gui.sprites.SpriteText;
import com.preikestolen.gui.GameGUI1;
import com.preikestolen.persist.dao.CosaDAO;
import com.preikestolen.persist.dao.IDaoGui;
import com.preikestolen.persist.dao.MundoDAO;
import com.preikestolen.persist.dao.PilaCosasDAO;

/**
 *
 * @author Edu
 */
public class ToolGUI extends ItemGUI<CosaDAO>{    
    private PilaCosasDAO oldPila;
    
    public ToolGUI(String name, GameGUI1 gui, CosaDAO dao) {
        super(name, gui, "tool", dao);
        
        
        actualizarImagen();
    }
    
    @Override
    public boolean isRelleno() {
        return getDAO()!=null && !getDAO().isVacio();
    }

    public CosaDAO set(PilaCosasDAO pila){        
        oldPila=pila;
        return cambiarDAO(pila.getUnaCosa());
    }
    
        
    public CosaDAO cambiarDAO(CosaDAO nuevoDAO){
        CosaDAO oldDAO=dao;
        dao=nuevoDAO;
        if(oldDAO!=null && dao!=null && oldDAO.getTipo().ordinal()!=nuevoDAO.getTipo().ordinal()){
            detachChild(imagen);
            imagen=null;            
        }
        actualizarImagen();
                
        return oldDAO;
    }

    @Override
    public void onCambiarTipo() {
        
    }
    
    public PilaCosasDAO getOldPila(){
        return oldPila;
    }
}
