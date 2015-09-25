/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3.scene;

import com.jme3.bounding.BoundingBox;
import com.jme3.scene.BatchNode;

/**
 *
 * @author Edu
 */
public class BatchNodeBackground extends BatchNode{

    public BatchNodeBackground() {
    }

    public BatchNodeBackground(String name) {
        super(name);
    }
    
    public void batch(){
        super.batch();
    }
    
    public void postBatch(){
        setModelBound(new BoundingBox());
        updateModelBound(); 
    }
    
    public int batches(){
        return batches.getArray().length;
    }
}
