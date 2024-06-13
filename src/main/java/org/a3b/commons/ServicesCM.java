package org.a3b.commons;

import org.a3b.serverCM.magazzeno.*;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicesCM extends Remote {
	ListaAree cercaAreaGeografica(String name, String country) throws RemoteException;

	ListaAree cercaAreaGeografica(double latitude, double longitude) throws RemoteException;

	AreaGeografica getAreaGeografica(int geoID) throws RemoteException;

	Misurazione visualizzaAreaGeografica(int geoID) throws RemoteException;

	Operatore registrazione(Operatore operator, String password) throws RemoteException;

	Operatore login(int userID, String password) throws RemoteException;

	CentroMonitoraggio registraCentroAree(CentroMonitoraggio centro) throws RemoteException;

	boolean alterListaAreeCentro(CentroMonitoraggio center, ListaAree newlist) throws RemoteException;

	ListaAree getListaAree(int centerID) throws RemoteException;

	boolean inserisciParametriClimatici(Misurazione misurazione) throws RemoteException;

	Misurazione getMisurazione(int recordID) throws RemoteException;
}