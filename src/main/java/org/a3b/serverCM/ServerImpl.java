package org.a3b.serverCM;

import lombok.extern.log4j.Log4j2;
import org.a3b.serverCM.magazzeno.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

@Log4j2
public class ServerImpl extends UnicastRemoteObject implements ServicesCM {
    public ServerImpl() throws RemoteException {
        super();
    }

    @Override
    public AreaGeografica cercaAreaGeografica(String name, String country) throws RemoteException {
        return null;
    }

    @Override
    public AreaGeografica cercaAreaGeografica(long latitude, long longitude) throws RemoteException {
        return null;
    }

    @Override
    public AreaGeografica getAreaGeografica(int geoID) throws RemoteException {
        return null;
    }

    @Override
    public Misurazione visualizzaAreaGeografica(int geoID) throws RemoteException {
        return null;
    }

    @Override
    public Operatore registrazione(Operatore operator, String password) throws RemoteException {
        return null;
    }

    @Override
    public Operatore login(int userID, String password) throws RemoteException {
        return null;
    }

    @Override
    public CentroMonitoraggio registraCentroAree(String name, String street, int civic, int zipcode, String town, String province, ListaAree areas) throws RemoteException {
        return null;
    }

    @Override
    public boolean alterListaAreeCentro(CentroMonitoraggio center, ListaAree newlist) throws RemoteException {
        return false;
    }

    @Override
    public ListaAree getListaAree(int centerID) throws RemoteException {
        return null;
    }

    @Override
    public boolean inserisciParametriClimatici(Misurazione misurazione) throws RemoteException {
        return false;
    }

    @Override
    public Misurazione getMisurazione(int recordID) throws RemoteException {
        return null;
    }
}
