/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.server.service;

import com.jme3.math.Vector3f;
import com.jme3.network.HostedConnection;
import com.preikestolen.GameServer;
import com.preikestolen.net.msg.game.MsgOnCelda;
import com.preikestolen.net.msg.game.MsgOnMatarEstatico;
import com.preikestolen.persist.dao.CeldaDAO;
import com.preikestolen.persist.dao.DinamicoDAO;
import com.preikestolen.persist.dao.JugadorDAO;
import com.preikestolen.persist.dao.MundoDAO;
import com.preikestolen.persist.dao.PilaCosasDAO;
import com.preikestolen.persist.dao.PosicionRotacionDAO;
import com.preikestolen.world.elements.terreno.Fractales;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Edu
 */
public class ServerService {
    public GameServer server;
    public Fractales f;
    
    public ServerService(GameServer server){
        this.server=server;
    }
    
    
    public List<MundoDAO> getMundos(){
        List<MundoDAO> mundos=new ArrayList<MundoDAO>();
        
        File path=new File(MundoDAO.PATH);
        String[] folders=path.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
              return new File(current, name).isDirectory();
            }
          });
        
        for(String folder:folders){            
            mundos.add((MundoDAO)MundoDAO.cargarById(folder));
        }
        
        return mundos;
    }
    
    public MundoDAO crearMundo(String nombre)throws Exception{
        File mPath=new File(MundoDAO.PATH+nombre);
        if(mPath.exists())
            throw new Exception("World name exists");
        
        new File(MundoDAO.PATH+nombre).mkdirs();
        MundoDAO mundo=new MundoDAO(nombre);
        
        mundo.crear();
        mundo.save();
        
        return mundo;
    }
    
    public void iniciarMundo(MundoDAO mundo, GameServer server)throws Exception{
        f=new Fractales(mundo.getSeed());
        if(mundo.getJugadores()!=null){
            //El mundo ya estaba creado
            if(server.getNet().getConnections().size()!=mundo.getJugadores().size()){
                throw new Exception("Faltan jugadores");
            }
        }else{
            //El mundo se acaba de crear
            mundo.setJugadores(new ArrayList<JugadorDAO>());
            
                    
            for(HostedConnection cnn:server.getNet().getConnections()){
                JugadorDAO jugador=(JugadorDAO)cnn.getAttribute(JugadorDAO.ID);
                
                mundo.getJugadores().add(jugador);
            }

            mundo.save();
        }
        
        server.setMundo(mundo);
    }
    
    
    public synchronized CeldaDAO getCelda(String id, boolean crear){
        CeldaDAO c=server.getMundoServer().getCache().get(id);
        if(c==null){
            c=CeldaDAO.cargarById(id);
            if(c==null && crear){
                System.out.println("No existe la celda "+id+". Se crea...");

                c=new CeldaDAO(id);
                c.setTime(-1);                   
            }
            if(c!=null)
                server.getMundoServer().getCache().put(id, c);
        }
        return c;
    }

    public void generarCelda(final CeldaDAO celda, final HostedConnection cnn){
        System.out.println("#########Genrar celda "+celda.getID());
        server.getMundoServer().executor.execute(new Runnable() {
            public void run() {
                try{
                    CeldaDAO c=getCelda(celda.getID(), true);
                    float t=System.currentTimeMillis();                
                    c.generar(f, c.getPos());
                    c.save();
                    System.out.println("Celda "+c.getID()+" generada y guardada en "+((System.currentTimeMillis()-t)/1000));

                    cnn.send(new MsgOnCelda(c));        
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    
    public MsgOnMatarEstatico matarEstatico(String celdaId, String objetoId){
        System.out.println("OnMatarEstatico "+celdaId+" - "+objetoId);
        CeldaDAO celda=getCelda(celdaId, false);
        if(celda!=null){            
            return new MsgOnMatarEstatico(celdaId, objetoId, celda.time, celda.matarEstatico(objetoId));
        }
        return null;
    }
    
    public void cogerDinamico(String celdaId, String objetoId, String jugador)throws Exception{
        CeldaDAO celda=getCelda(celdaId, false);
        if(celda!=null){   
            DinamicoDAO d=celda.cogerDinamico(objetoId);
            if(d==null){
                throw new Exception("El objeto dinamico "+objetoId+" no existe en la celda "+celdaId);
            }else{
                server.getJugador(jugador).addCosa(d, null);//No enviamos gui ya que es el server, solo queremos la logica
                server.getMundo().change();                
            }
        }else{
            throw new Exception("La celda "+celdaId+" no existe");
        }
        
    }
    
    public DinamicoDAO soltarDinamico(String celdaId, String objetoId, JugadorDAO jugador, int pilaId)throws Exception{
        PilaCosasDAO pila=jugador.getPila(pilaId);
        CeldaDAO celda=getCelda(celdaId, false);
        if(celda!=null){                           
            DinamicoDAO d=celda.addDinamico2(new Vector3f(), objetoId, pila.cosa.tipo, pila.cosa.vida, pila.cantidad);
            System.out.println("dinamico soltado..."+objetoId);
            pila.vaciar();
            celda.change();
            server.getMundo().change(); 
            return d;
        }else{
            throw new Exception("La celda "+celdaId+" no existe");
        }
    }
    
    public boolean posicionarDinamico(String celdaId, String objetoId, PosicionRotacionDAO pos){
        System.out.println("posicionarDinamico "+celdaId+" - "+objetoId);
        CeldaDAO celda=getCelda(celdaId, false);
        if(celda!=null){            
            DinamicoDAO d=celda.dinamicos.get(objetoId);
            if(d!=null){
                d.posicion=pos;
                celda.change();
                return true;
            }else{
                System.out.println("!!!!!!!! error dinamico "+objetoId+" no existe!!!");
            }
        }        
        return false;
    }
}
