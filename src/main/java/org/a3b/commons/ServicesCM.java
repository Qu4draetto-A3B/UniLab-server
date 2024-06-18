package org.a3b.commons;

import org.a3b.commons.magazzeno.*;
import org.a3b.commons.result.Result;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicesCM extends Remote {
	Result<ListaAree> cercaAreaGeografica(String name, String country) throws RemoteException;

	Result<ListaAree> cercaAreaGeografica(double latitude, double longitude) throws RemoteException;

	Result<Misurazione> visualizzaAreaGeografica(long geoID) throws RemoteException;

	Result<Operatore> registrazione(Operatore operator, String password) throws RemoteException;

	Result<Operatore> login(long userID, String password) throws RemoteException;

	Result<CentroMonitoraggio> registraCentroAree(CentroMonitoraggio centro) throws RemoteException;

	Result<Boolean> alterListaAreeCentro(CentroMonitoraggio center, ListaAree newlist) throws RemoteException;

	Result<Boolean> inserisciParametriClimatici(Misurazione misurazione) throws RemoteException;

	Result<Misurazione> getMisurazione(long recordID) throws RemoteException;

	Result<CentroMonitoraggio> getCentroMonitoraggio(long centerID) throws RemoteException;

	Result<Operatore> getOperatore(long userID) throws RemoteException;

	Result<AreaGeografica> getAreaGeografica(long geoID) throws RemoteException;

	Result<ListaAree> getListaAree(long centerID) throws RemoteException;
}