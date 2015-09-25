/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.world.elements.terreno;

import com.jme3.terrain.noise.Basis;
import com.jme3.terrain.noise.basis.FilteredBasis;
import com.jme3.terrain.noise.filter.IterativeFilter;
import com.jme3.terrain.noise.filter.OptimizedErode;
import com.jme3.terrain.noise.filter.PerturbFilter;
import com.jme3.terrain.noise.filter.SmoothFilter;
import com.jme3.terrain.noise.fractal.FractalSum;
import com.jme3.terrain.noise.modulator.Modulator;


/**
 *
 * @author Edu
 */
public class MiFractal extends FilteredBasis{
    private PerturbFilter perturb;
    private OptimizedErode therm;
    private SmoothFilter smooth;
    private IterativeFilter iterate;
  
    public MiFractal(){
        super(new FractalSum());

        perturb = new PerturbFilter();
        
        therm = new OptimizedErode();
        smooth = new SmoothFilter();

        iterate = new IterativeFilter();
        iterate.addPreFilter(this.perturb);
        iterate.addPostFilter(this.smooth);
        iterate.setFilter(this.therm);
        iterate.setIterations(1);

        addPreFilter(iterate);
    }
    
    public void setThermRadius(int v){
        therm.setRadius(v);
    }
    
    public int getThermRadius(){
        return therm.getRadius();
    }
    
    public void setThermTalus(float v){
        therm.setTalus(v);
    }
    
    public float getThermTalus(){
        return therm.getTalus();
    }
    
    public void setSmoothEffect(float v){
        smooth.setEffect(v);
    }
    
    public float getSmoothEffect(){
        return smooth.getEffect();
    }
    
    public void setPerturbMagnitude(float v){
        perturb.setMagnitude(v);
    }
    
    public float getPerturbMagnitude(){
        return perturb.getMagnitude();
    }
    
    
    public void setSmoothRadius(int v){
        smooth.setRadius(v);
    }
    
    public int getSmoothRadius(){
        return smooth.getRadius();
    }

    public FractalSum getFractal(){
        return (FractalSum)getBasis();
    }
    
    public void setRoughness(float v){
        getFractal().setRoughness(v);
    }
    
    public float getRoughness(){
        return getFractal().getRoughness();
    }
    
    public void setFrequency(float v){
        getFractal().setFrequency(v);
    }
    
    public float getFrequency(){
        return getFractal().getFrequency();
    }
    
    public void setAmplitude(float v){
        getFractal().setAmplitude(v);
    }
    
    public float getAmplitude(){
        return getFractal().getAmplitude();
    }
    
    public void setLacunarity(float v){
        getFractal().setLacunarity(v);
    }
    
    public float getLacunarity(){
        return getFractal().getLacunarity();
    }
    
    public void setOctaves(float v){
        getFractal().setOctaves(v);
    }
    
    public float getOctaves(){
        return getFractal().getOctaves();
    }
    
    public Basis addModulator(Modulator m){
        getFractal().addModulator(m);
        return getBasis();
    }
    

}
