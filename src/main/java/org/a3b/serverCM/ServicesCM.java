package org.a3b.serverCM;

import org.a3b.serverCM.magazzeno.*;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicesCM extends Remote {
    AreaGeografica cercaAreaGeografica(String name, String country) throws RemoteException;
    AreaGeografica cercaAreaGeografica(long latitude, long longitude) throws RemoteException;
    AreaGeografica getAreaGeografica(int geoID) throws RemoteException;
    Misurazione visualizzaAreaGeografica(int geoID) throws RemoteException;
    Operatore registrazione(Operatore operator, String password) throws RemoteException;
    Operatore login(int userID, String password) throws RemoteException;
    CentroMonitoraggio registraCentroAree(String name, String street, int civic, int zipcode, String town, String province, ListaAree areas) throws RemoteException;
    boolean alterListaAreeCentro(CentroMonitoraggio center, ListaAree newlist) throws RemoteException;
    ListaAree getListaAree(int centerID) throws RemoteException;
    boolean inserisciParametriClimatici(Misurazione misurazione) throws RemoteException;
    Misurazione getMisurazione(int recordID) throws RemoteException;
}