/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.common;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Edu
 */
public class Line extends Geometry{
    private List<Vector3f> puntos=new ArrayList<Vector3f>();
    
    public Line(AssetManager asset,  ColorRGBA color){
        Mesh mesh=new Mesh();
        mesh.setMode(Mesh.Mode.Lines);
        setMesh(mesh);
        Material mat = new Material(asset, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        setMaterial(mat);
    }
    
    public Line(AssetManager asset, Vector3f from, Vector3f to, ColorRGBA color){
        puntos.add(from);
        puntos.add(to);
        Mesh mesh=new Mesh();
        mesh.setMode(Mesh.Mode.Lines);
        generar(mesh);
        
        setMesh(mesh);
        Material mat = new Material(asset, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        setMaterial(mat);
    }
    
    public void addPunto(Vector3f p){
        puntos.add(p);
        generar(getMesh());
        
    }
    
    public void addPunto(int x, int y, int z){
        puntos.add(new Vector3f(x,y,z));
        generar(getMesh());
    }
    
    private void generar(Mesh mesh){      
        Vector3f[] f=new Vector3f[puntos.size()];
        f=puntos.toArray(f);
        mesh.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(f));

        short[] indexes=new short[2*puntos.size()]; //Indexes are in pairs, from a vertex and to a vertex

        for(short i=0;i<puntos.size();i++){
            indexes[2*i]=i;
            indexes[2*i+1]=(short)(i+1);
        }

        mesh.setBuffer(VertexBuffer.Type.Index, 2, indexes);

        mesh.updateBound();
        mesh.updateCounts();
    }
}
