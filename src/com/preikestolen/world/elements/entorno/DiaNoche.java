/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.world.elements.entorno;

import com.jme3.asset.AssetManager;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

/**
 *
 * @author Edu
 */
public class DiaNoche{
    public static final float TIEMPO_DIA=120;//Segundos
    
    public static final float ATARDECER_COLOR=0.6f;//Segundos
    
    
    public static final float INICIO_TARDE=FastMath.HALF_PI;
    public static final float INICIO_NOCHE=FastMath.PI-FastMath.QUARTER_PI/1.5f;
    public static final float INICIO_DIA=FastMath.QUARTER_PI/1.5f;
    
    public float procentajeDia;
    
    public float vDia;
    public float vNoche;
    
    public float hora;
    

    private Luz luz;
    private boolean enabled=true;
    
    public DiaNoche(Luz luz, AssetManager a){
        this.luz=luz;
        
        setPorcentajeNoche(50);
        
       // hora=FastMath.HALF_PI;
    }
    
    public void setPorcentajeNoche(float porcentaje){
        procentajeDia=porcentaje;
        
        float distDia=porcentaje*FastMath.TWO_PI/100f;
        vDia=distDia/TIEMPO_DIA;
        
        float distNoche=FastMath.TWO_PI-distDia;
        vNoche=distNoche/TIEMPO_DIA;
    }
    
    public void setHora(float hora){
        this.hora=hora;
        enabled=true;
        update(1);
        enabled=false;
    }
    
    public float getHora(){
        return hora;
    }
    
    public void update(float tpf){
        if(enabled){
            if(isDia()){
                addHora(vDia*tpf);
            }else{
                addHora(vNoche*tpf);
            }

            if(isAtardecer()){
                toTarde(tpf);            
            }else if(isNoche()){
                toNoche(tpf);
                luz.onAnochecer();
            }else if(isAmanecer()){
                toDia(tpf);
                luz.onAmanacer();
            }

            sombras(tpf);

            luz.sol.setDirection(new Quaternion().fromAngles(0, 0, -hora).mult(Vector3f.UNIT_X));
        }
    }
    
    
    public boolean isDia(){
        return hora>=0 && hora<INICIO_NOCHE;
    }
    
    public boolean isNoche(){
        return hora>=INICIO_NOCHE;
    }
    
    public boolean isAtardecer(){
        return hora<INICIO_NOCHE && hora>INICIO_TARDE;//+FastMath.QUARTER_PI;
    }
    
    public boolean isAmanecer(){
        return hora>=0 && hora<INICIO_DIA;//+FastMath.QUARTER_PI;
    }
    
    public void addHora(float velocidad){
        hora+=velocidad;
        if(hora>FastMath.TWO_PI)
            hora=hora-FastMath.TWO_PI;
    }
    
    private void toDia(float tpf){
        if(luz.color.r<1)luz.color.r+=0.5f*tpf;
        if(luz.color.g<1)luz.color.g+=0.5f*tpf;
        if(luz.color.b<1)luz.color.b+=0.5f*tpf;
        if(luz.color.a<1)luz.color.a+=0.5f*tpf;
    }
    
    private void toNoche(float tpf){
        if(luz.color.r>luz.color.g){
             luz.color.r-=0.5f*tpf;
        }else{
            if(luz.color.a>0.05f){
                luz.color.a-=0.5f*tpf;            
                luz.color.g-=0.5f*tpf;
                luz.color.b-=0.5f*tpf;
                luz.color.r-=0.5f*tpf;
            }
        }
    }
    
    private void toTarde(float tpf){
        if(luz.color.g>ATARDECER_COLOR){
            luz.color.g-=0.05f*tpf;
            luz.color.b-=0.05f*tpf;
        }       
    }
    
    private void sombras(float tpf){
        if(hora>2.5f){
            if(luz.sombrasDia.getShadowIntensity()>0){
                luz.sombrasDia.setShadowIntensity(luz.sombrasDia.getShadowIntensity()-0.1f*tpf);
            }
        }else if(hora<0.5f){
            if(luz.sombrasDia.getShadowIntensity()<0.3f){
                luz.sombrasDia.setShadowIntensity(luz.sombrasDia.getShadowIntensity()+0.1f*tpf);
            }
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    
}
