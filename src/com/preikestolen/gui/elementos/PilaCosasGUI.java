/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.gui.elementos;

import com.jm3.gui.events.ClickEvent;
import com.jm3.gui.events.ClickEvent.Button;
import com.jm3.gui.sprites.SpriteText;
import com.preikestolen.gui.GameGUI1;
import com.preikestolen.net.msg.game.MsgSoltarDinamico;
import com.preikestolen.persist.dao.PilaCosasDAO;
import com.preikestolen.world.elements.terreno.Celda;
import com.preikestolen.world.elements.terreno.elementos.Dinamico;

/**
 *
 * @author Edu
 */
public class PilaCosasGUI extends ItemGUI<PilaCosasDAO>{    
    private SpriteText txt;

    public PilaCosasGUI(String name, GameGUI1 gui, PilaCosasDAO dao) {
        super(name, gui, "cuadro", dao);
        
        
        actualizarImagen();
    }
    
    @Override
    public boolean isRelleno() {
        return !dao.isVacio();
    }

    
    @Override
    public void actualizarImagen(){
        super.actualizarImagen();
        if(txt!=null){
            if(!dao.isVacio()){
                txt.setText(String.valueOf(dao.getCantidad()));            
                txt.centerXInParent();
            }else{
                detachChild(txt);
                txt=null;
            }
        }
    }
    
    @Override
    public void cargarImagen(GameGUI1 gui){
        super.cargarImagen(gui);
        if(txt==null){
            txt=new SpriteText(name, gui.app.font34);   
            attachChild(txt);
            txt.centerInParent();
            txt.move(0, 35);
        }
    }
    
    @Override
    public boolean onClick(ClickEvent event) {
        if(event.isPressed()){
            if(event.getButton()==Button.LEFT && dao!=null && dao.isTool()){
                gui.onClickTool(this);
                actualizarImagen();
            }else if(event.getButton()==Button.RIGHT && !isVacio()){
                Celda c=gui.app.getMundoClient().terreno.getCurrentCelda();                
                Dinamico din=c.addDinamico(dao.cosa, dao.cantidad);
                System.out.println("Soltado el dinamico "+din.dao.id);
                gui.app.getNet().send(new MsgSoltarDinamico(c.getDao().getID(), din.getDAO().id, dao.id));
                dao.vaciar();
                
                actualizarImagen();
            }
        }
            
        return false;
    }

    @Override
    public void onCambiarTipo() {
        if(txt!=null){
            detachChild(txt);
            txt=null;
        }
    }
}
