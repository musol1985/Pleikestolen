/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.client.listeners;

import com.jme3.network.Client;
import com.preikestolen.Game;
import com.preikestolen.common.MensajesListener;
import com.preikestolen.net.msg.lobby.MsgCrearMundo;
import com.preikestolen.net.msg.lobby.MsgError;
import com.preikestolen.net.msg.lobby.MsgJugadorInfo;
import com.preikestolen.net.msg.lobby.MsgJugadorJoined;
import com.preikestolen.net.msg.lobby.MsgJugadoresLista;
import com.preikestolen.net.msg.lobby.MsgMundoCreado;
import com.preikestolen.persist.dao.MundoDAO;
import com.preikestolen.net.msg.lobby.MsgMundosLista;
import com.preikestolen.net.msg.lobby.MsgOnStart;
import com.preikestolen.net.msg.lobby.MsgStart;
import com.preikestolen.persist.dao.JugadorDAO;
import com.preikestolen.world.MundoClient;

/**
 *
 * @author Edu
 */
public class LobbyListener extends MensajesListener<Client>{
    private Game game;
    
    private MundoDAO mundoTemp;//variable temporal hasta q se haga la gui para seleccionar mundo

    public LobbyListener(Game game){
        super();
        this.game=game;
    }
    
    public void onJugadorInfo(Client source, MsgJugadorInfo m){                
        System.out.println("Te has unido a la partida con el nick "+m.nick+" owner: "+m.owner);
        game.getJugador().setOwner(m.owner);
    }
  
    public void onError(Client source, MsgError m){                
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!------------->>>>>>>>>>>>>>>>>>>>on error!!"+m.id);
    }
    
    public void onListaMundos(Client source, MsgMundosLista m){
        System.out.println("Recibidos los mundos: "+m.mundos.size());
        
        //TODO hacer la logica de esto
        if(m.mundos.size()==0){
            System.out.println("No hay mundos... se pide crear uno!");
            source.send(new MsgCrearMundo("Mundo1"));
        }else{
           
            MundoDAO mundo=m.mundos.get(0);
            mundoTemp=mundo;
            //TODO hacer toda la logica de aqui
            System.out.println("Se selecciona el mundo 0 por defecto."+mundo.getID());
            
            //source.send(new MsgStart(mundo.getID()));
        }
    }
    
    public void onMundoCreado(Client source, MsgMundoCreado m){
        System.out.println("Mundo creado!");
        //TODO hacer toda la logica de aqui
        
        source.send(new MsgStart(m.mundo.getID()));
    }
    
    public void onStart(Client source, MsgOnStart m){
        System.out.println("Start!!!!!"+m.mundo.getID());
        game.goJugando(m.mundo);
        game.getStateManager().attach(new MundoClient(m.mundo, game.getJugador(), game.getJugadoresExt()));
        //TODO hacer toda la logica de aqui
    }
    
    
    public void onJugadorJoined(Client source, MsgJugadorJoined m){
        System.out.println("Jugador joined!!!!!"+m.nick);
        game.addJugadorExt(new JugadorDAO(m.nick, m.owner));

        if(game.getJugador().isOwner())
            source.send(new MsgStart(mundoTemp.getID()));
        //TODO hacer toda la logica de aqui
    }
    
    public void onJugadoresList(Client source, MsgJugadoresLista m){
        for(JugadorDAO j:m.jugadores)
            game.addJugadorExt(j);
    }
}
