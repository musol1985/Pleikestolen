/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.world.elements.controllers.interfaces;

import com.preikestolen.persist.dao.PosicionDAO;

/**
 *
 * @author Edu
 */
public interface INetPosSync {
    public PosicionDAO getPosicion();
    public String getID();
}
