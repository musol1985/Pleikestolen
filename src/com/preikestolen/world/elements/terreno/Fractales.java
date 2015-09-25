/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.world.elements.terreno;

import com.jme3.terrain.noise.ShaderUtils;
import com.jme3.terrain.noise.basis.FilteredBasis;
import com.jme3.terrain.noise.fractal.FractalSum;
import com.jme3.terrain.noise.modulator.NoiseModulator;

/**
 *
 * @author Edu
 */
public class Fractales implements NoiseModulator{
    public MiFractal altura;
    public FractalSum t1;
    public FractalSum t2;
    public FractalSum t3;
    public FractalSum t4;
    public FractalSum color;
    public FractalSum settings;
 
    
    public Fractales(long seed){
        altura = new MiFractal();
        altura.setRoughness(0.3f);
        altura.setFrequency(0.25f);
        altura.setAmplitude(5.0f);
        altura.setLacunarity(2.6f);
        altura.setOctaves(8);
        altura.addModulator(this);


        t1 = new FractalSum();
        t1.setRoughness(0.2f);
        t1.setFrequency(0.5f);
        t1.setAmplitude(10.0f);
        t1.setLacunarity(2.12f);
        t1.setOctaves(8);
        t1.addModulator(this);
        
        t2 = new FractalSum();
        t2.setRoughness(0.2f);
        t2.setFrequency(0.5f);
        t2.setAmplitude(4.0f);
        t2.setLacunarity(2.12f);
        t2.setOctaves(8);
        t2.addModulator(this);
        
        t3 = new FractalSum();
        t3.setRoughness(0.2f);
        t3.setFrequency(0.5f);
        t3.setAmplitude(4.0f);
        t3.setLacunarity(2.12f);
        t3.setOctaves(8);
        t3.addModulator(this);
        
        t4 = new FractalSum();
        t4.setRoughness(0.2f);
        t4.setFrequency(0.5f);
        t4.setAmplitude(4.0f);
        t4.setLacunarity(2.12f);
        t4.setOctaves(8);
        t4.addModulator(this);
        
        color = new FractalSum();
        color.setRoughness(0.01f);
        color.setFrequency(15f);
        color.setAmplitude(1.0f);
        color.setLacunarity(2.12f);
        color.setOctaves(8);
        color.addModulator(this);    
    }

    public MiFractal getAltura() {
        return altura;
    }

    public FractalSum getT1() {
        return t1;
    }

    public FractalSum getT2() {
        return t2;
    }

    public FractalSum getT3() {
        return t3;
    }
    
    public FractalSum getT4() {
        return t4;
    }
    
    public FractalSum getColor(){
        return color;
    }


    public float value(float... in) {
        return ShaderUtils.clamp(in[0] * 0.5f + 0.5f, 0, 1);
    }
    
 
    
}
