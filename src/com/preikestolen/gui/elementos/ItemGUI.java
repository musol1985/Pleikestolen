/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.gui.elementos;

import com.jm3.gui.events.ClickEvent;
import com.jm3.gui.events.MouseMoveEvent;
import com.jm3.gui.sprites.IMouseClick;
import com.jm3.gui.sprites.IMouseMove;
import com.jm3.gui.sprites.Sprite;
import com.jm3.gui.sprites.SpriteGeometry;
import com.preikestolen.common.Cosas.Tipo;
import com.preikestolen.gui.GameGUI1;
import com.preikestolen.persist.dao.IDaoGui;

/**
 *
 * @author Edu
 */
public abstract class ItemGUI<T extends IDaoGui> extends SpriteGeometry implements IMouseClick, IMouseMove{
    protected Sprite imagen;    
    protected GameGUI1 gui;
    protected String imagenId;
    protected T dao;
    
    public ItemGUI(String name, GameGUI1 gui, String imagenId, T dao) {
        super(name, gui.app.getAssetManager(), "Interface/"+imagenId+".png");
        this.imagenId=imagenId;
        this.gui=gui;
        this.dao=dao;
        
        actualizarImagen();
        
        setListenerMouseClick(this);
        setListenerMouseMove(this);
    }
    
    public T getDAO(){
        return dao;
    }

    
    public abstract boolean isRelleno();
    public abstract void onCambiarTipo();
    
    public void actualizarImagen(){
        if(isRelleno()){
            if(isVacio()){
                setImage(gui.app.getAssetManager(), "Interface/"+imagenId+"Relleno.png", true);
                cargarImagen(gui);
            }else{
                if(!imagen.getName().equals(getImagenByTipo(dao.getTipo()))){
                    detachChild(imagen);                    
                    imagen=null;
                    onCambiarTipo();
                    cargarImagen(gui);
                }
                //TODO comprobar si es el mismo tipo                
            }
        }else{
            if(!isVacio()){
                setImage(gui.app.getAssetManager(), "Interface/"+imagenId+".png", true);
                detachChild(imagen);
                imagen=null;
            }
        }        
    }
    
    public boolean isVacio(){
        return imagen==null;
    }
    
    public void cargarImagen(GameGUI1 gui){
        if(imagen==null){
            imagen=new SpriteGeometry("", gui.app.getAssetManager(), getImagenByTipo(dao.getTipo()));          
            attachChild(imagen);
            imagen.centerInParent();
        }
    }
    
    public String getImagenByTipo(Tipo tipo){
        return "Interface/items/"+dao.getTipo().name().toLowerCase()+".png";
    }

    @Override
    public boolean onClick(ClickEvent event) {
        System.out.println("click!");

        return false;
    }

    @Override
    public boolean onMouseMove(MouseMoveEvent cursor) {
        return false;
    }

    @Override
    public boolean onMouseIn(MouseMoveEvent cursor) {
        if(!isVacio())
            setImage(gui.app.getAssetManager(), "Interface/"+imagenId+"Hover.png", true);
        
        return false;
    }

    @Override
    public boolean onMouseOut(MouseMoveEvent cursor) {
        if(isVacio()){
            setImage(gui.app.getAssetManager(), "Interface/"+imagenId+".png", true);
        }else{
            setImage(gui.app.getAssetManager(), "Interface/"+imagenId+"Relleno.png", true);
        }
        
        return false;
    }
    
    
}
