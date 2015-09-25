/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.common;

import com.jme3.network.Message;
import com.jme3.network.MessageConnection;
import com.jme3.network.MessageListener;
import com.jme3.network.message.SerializerRegistrationsMessage;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Edu
 */
public class MensajesListener<T extends MessageConnection> implements MessageListener<T>{
    private HashMap<Class, Method> metodos=new HashMap<Class, Method>();
    
    public MensajesListener(){
        for(Method m:getClass().getDeclaredMethods()){
            if(m.getName().startsWith("on")){
                metodos.put(m.getParameterTypes()[1], m);
            }
        }
    }
    
    public void messageReceived(T source, Message m) {
        if(m instanceof SerializerRegistrationsMessage)
            return;
        
        try {            
            metodos.get(m.getClass()).invoke(this, source, m);
        } catch (Exception ex) {
            Logger.getLogger(MensajesListener.class.getName()).log(Level.SEVERE,getClass().getName()+" -- "+m.getClass()+ "->Error en el mensajesListener: "+ ex.getMessage(), ex);
            ex.printStackTrace();
        }
    }
}
