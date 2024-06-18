package org.a3b.commons;

import org.a3b.commons.magazzeno.*;
import org.a3b.commons.result.Result;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicesCM extends Remote {
	Result<ListaAree> cercaAreaGeografica(String name, String country) throws RemoteException;

	Result<ListaAree> cercaAreaGeografica(double latitude, double longitude) throws RemoteException;

	Result<AreaGeografica> getAreaGeografica(int geoID) throws RemoteException;

	Result<Misurazione> visualizzaAreaGeografica(int geoID) throws RemoteException;

	Result<Operatore> registrazione(Operatore operator, String password) throws RemoteException;

	Result<Operatore> login(int userID, String password) throws RemoteException;

	Result<CentroMonitoraggio> registraCentroAree(CentroMonitoraggio centro) throws RemoteException;

	Result<Boolean> alterListaAreeCentro(CentroMonitoraggio center, ListaAree newlist) throws RemoteException;

	Result<ListaAree> getListaAree(int centerID) throws RemoteException;

	Result<Boolean> inserisciParametriClimatici(Misurazione misurazione) throws RemoteException;

	Result<Misurazione> getMisurazione(int recordID) throws RemoteException;
}