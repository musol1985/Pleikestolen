/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3.light;

import com.jme3.math.ColorRGBA;

/**
 *
 * @author Edu
 */
public class Sol extends DirectionalLight{
    @Override
    public void setColor(ColorRGBA color){
        this.color=color;
    }
}
