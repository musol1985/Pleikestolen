/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.preikestolen.common;

import com.jme3.network.serializing.Serializer;
import com.preikestolen.net.msg.game.MsgAccion;
import com.preikestolen.net.msg.game.MsgCogerDinamico;
import com.preikestolen.net.msg.game.MsgDinamicoPosition;
import com.preikestolen.net.msg.game.MsgDinamicoSleep;
import com.preikestolen.net.msg.game.MsgGetCeldas;
import com.preikestolen.net.msg.game.MsgMatarEstatico;
import com.preikestolen.net.msg.game.MsgOnCelda;
import com.preikestolen.net.msg.game.MsgOnMatarEstatico;
import com.preikestolen.net.msg.game.MsgPosition;
import com.preikestolen.net.msg.game.MsgSetTool;
import com.preikestolen.net.msg.game.MsgSoltarDinamico;
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
import com.preikestolen.persist.dao.CeldaDAO;
import com.preikestolen.persist.dao.CosaDAO;
import com.preikestolen.persist.dao.DinamicoDAO;
import com.preikestolen.persist.dao.EstaticoDAO;
import com.preikestolen.persist.dao.JugadorDAO;
import com.preikestolen.persist.dao.PilaCosasDAO;
import com.preikestolen.persist.dao.PosicionDAO;
import com.preikestolen.persist.dao.PosicionRotacionDAO;
import com.utils.Vector2;

/**
 *
 * @author Edu
 */
public class Mensajes {
    public static void init(){
        Serializer.registerClass(MsgJugadorInfo.class);
        Serializer.registerClass(MsgError.class);
        Serializer.registerClass(MsgMundosLista.class);
        Serializer.registerClass(MsgCrearMundo.class);
        Serializer.registerClass(MsgMundoCreado.class);
        Serializer.registerClass(MsgStart.class);
        Serializer.registerClass(MsgOnStart.class);
        Serializer.registerClass(MsgGetCeldas.class);
        Serializer.registerClass(MsgOnCelda.class);
        Serializer.registerClass(MsgPosition.class);
        Serializer.registerClass(MsgJugadorJoined.class);
        Serializer.registerClass(MsgJugadoresLista.class);
        Serializer.registerClass(MsgMatarEstatico.class);
        Serializer.registerClass(MsgOnMatarEstatico.class);
        Serializer.registerClass(MsgDinamicoPosition.class);
        Serializer.registerClass(MsgDinamicoSleep.class);
        Serializer.registerClass(MsgAccion.class);
        Serializer.registerClass(MsgCogerDinamico.class);
        Serializer.registerClass(MsgSetTool.class);
        Serializer.registerClass(MsgSoltarDinamico.class);
        
        
        Serializer.registerClass(MundoDAO.class);
        Serializer.registerClass(JugadorDAO.class);
        Serializer.registerClass(PosicionDAO.class);
        Serializer.registerClass(PosicionRotacionDAO.class);
        Serializer.registerClass(CeldaDAO.class);
        Serializer.registerClass(EstaticoDAO.class);
        Serializer.registerClass(DinamicoDAO.class);
        Serializer.registerClass(CosaDAO.class);
        Serializer.registerClass(PilaCosasDAO.class);
        
        Serializer.registerClass(Vector2.class);
    }
}
