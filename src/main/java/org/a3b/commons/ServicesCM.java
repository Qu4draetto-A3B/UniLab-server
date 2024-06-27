/*
 * Interdisciplinary Workshop B
 * Climate Monitoring
 * A.A. 2023-2024
 *
 * Authors:
 * - Iuri Antico, 753144, VA
 * - Beatrice Balzarini, 752257, VA
 * - Michael Bernasconi, 752259, VA
 * - Gabriele Borgia, 753262, VA
 *
 * Some rights reserved.
 * See LICENSE file for additional information.
 */
package org.a3b.commons;

import org.a3b.commons.magazzeno.*;
import org.a3b.commons.result.Result;

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

	Result<ListaOperatori> getOperatoriRegistrati() throws RemoteException;

	Result<ListaCentri> getCentriMonitoraggio() throws RemoteException;

	Result<CentroMonitoraggio> alterListaAree(CentroMonitoraggio center, ListaAree newList) throws RemoteException;

}