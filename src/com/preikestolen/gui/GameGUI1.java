/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.gui;

import com.jm3.gui.GUIControllerPC;
import com.preikestolen.Game;
import com.preikestolen.gui.elementos.ItemGUI;
import com.preikestolen.gui.elementos.PilaCosasGUI;
import com.preikestolen.gui.elementos.ToolGUI;
import com.preikestolen.persist.dao.CosaDAO;
import com.preikestolen.persist.dao.JugadorDAO;
import com.preikestolen.persist.dao.PilaCosasDAO;
import java.util.HashMap;



/**
 *
 * @author Edu
 */
public class GameGUI1 extends GUIControllerPC<Game>{
    private HashMap<PilaCosasDAO, PilaCosasGUI> pilas=new HashMap<PilaCosasDAO, PilaCosasGUI>();
    private ToolGUI tool;
    
    
    @Override
    public void onStart() {
        for(int i=0;i<JugadorDAO.SIZE_COSAS;i++){
            PilaCosasGUI pila=new PilaCosasGUI("pila"+i, this, app.getJugador().getPila(i));
            pilas.put((PilaCosasDAO)pila.getDAO(), pila);
            pila.setPosition(350+((pila.getWidth()+10)*i), 10);
            gui.attachChild(pila);            
        }  
        
        tool=new ToolGUI("tool", this, null);
        tool.setPosition(200, 0);
        gui.attachChild(tool);
    }
    
    
    public void onCogerCosa(PilaCosasDAO dao){
        pilas.get(dao).actualizarImagen();
    }
    
    public void onClickTool(PilaCosasGUI pila){    
        app.getMundoClient().player.cambiarTool(pila.getDAO().cosa);
        PilaCosasDAO oldPila=tool.getOldPila();
        CosaDAO dao=tool.set(pila.getDAO());
        
        if(oldPila!=null && oldPila.isVacio()){
            pila.getDAO().setCosa(null);
            pila=pilas.get(oldPila);
        }
        pila.getDAO().setCosa(dao);
        pila.actualizarImagen();        
    }
}
