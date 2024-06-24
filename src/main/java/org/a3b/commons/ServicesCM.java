package org.a3b.commons;

import org.a3b.commons.magazzeno.*;
import org.a3b.commons.result.Result;
import org.a3b.commons.utils.MediaAree;

import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * L'interfaccia {@link ServicesCM} estende {@link Remote} si occupa fornire i metodi che deve utilizzare il Client.
 */
public interface ServicesCM extends Remote {
	Result<ListaAree> cercaAreaGeografica(String name, String country) throws RemoteException;

	Result<ListaAree> cercaAreaGeografica(double latitude, double longitude) throws RemoteException;

	Result<Misurazione> visualizzaAreaGeografica(long geoID) throws RemoteException;

	Result<Operatore> registrazione(Operatore operator, String password) throws RemoteException;

	Result<Operatore> login(long userID, String password) throws RemoteException;

	Result<CentroMonitoraggio> registraCentroAree(CentroMonitoraggio centro) throws RemoteException;

	Result<Misurazione> inserisciParametriClimatici(Misurazione misurazione) throws RemoteException;

	Result<Misurazione> getMisurazione(long recordID) throws RemoteException;

	Result<CentroMonitoraggio> getCentroMonitoraggio(long centerID) throws RemoteException;

	Result<AreaGeografica> getAreaGeografica(long geoID) throws RemoteException;

	Result<ListaAree> getListaAree(long centerID) throws RemoteException;

	Result<ListaAree> getAreeGeografiche() throws RemoteException;

}