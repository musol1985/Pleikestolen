/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.world.elements;


import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.control.CameraControl;
import com.jme3.scene.shape.Sphere;
import com.preikestolen.world.MundoClient;
import com.preikestolen.world.elements.characters.Player;

/**
 *
 * @author Edu
 */
public class Camara {
    public CameraNode camNode;
    public Node posicion;
    
    public Camara(SimpleApplication c, MundoClient mundo){
        c.getFlyByCamera().setMoveSpeed(100);    
        c.getFlyByCamera().setEnabled(false);
        
        //c.getCamera().setFrustumFar(500000f);
        
        camNode = new CameraNode("Motion cam", c.getCamera());
        camNode.setControlDir(CameraControl.ControlDirection.SpatialToCamera);
        camNode.setEnabled(true);
        
        posicion=new Node();

        posicion.attachChild(camNode);
        posicion.move(1, 0, 1);
        camNode.setLocalTranslation(0, 40, -40);
        camNode.setLocalRotation(new Quaternion().fromAngles(FastMath.HALF_PI/2, 0, 0));
        
        mundo.player.attachChild(posicion);
    }
    
    public void zoom(float valor){
        camNode.move(0,5*valor,-5*valor);
    }
    
    public void debugCam(SimpleApplication c){
        c.getRootNode().detachChild(posicion);
        c.getFlyByCamera().setEnabled(true);
        c.getInputManager().setCursorVisible(false);
    }
    
    public void setDebug(SimpleApplication c){
        Geometry g=new Geometry("",new Sphere(30, 30, 3));
        Material mat = new Material(c.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Red);
        g.setMaterial(mat);
        g.move(0,10,0);
        posicion.attachChild(g);
    }
}
