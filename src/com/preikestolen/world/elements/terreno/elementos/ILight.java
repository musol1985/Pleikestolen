/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.world.elements.terreno.elementos;

import com.jme3.light.PointLight;

/**
 *
 * @author Edu
 */
public interface ILight {
    public PointLight getLuz();
    public boolean isLighting();
}
