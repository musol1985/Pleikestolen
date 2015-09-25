/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.persist.dao;

import com.preikestolen.common.Cosas.Tipo;

/**
 *
 * @author Edu
 */
public interface IDaoGui {
    public boolean isVacio();
    public int getCantidad();
    public Tipo getTipo();
    public boolean isTool();
}
