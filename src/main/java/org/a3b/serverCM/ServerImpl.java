package org.a3b.serverCM;

import lombok.extern.log4j.Log4j2;
import org.a3b.serverCM.magazzeno.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Log4j2
public class ServerImpl extends UnicastRemoteObject implements ServicesCM {
    public ServerImpl() throws RemoteException {
        super();
    }

    @Override
    public AreaGeografica cercaAreaGeografica(String name, String country) throws RemoteException {
        return new ListaAree().cercaAreaGeografica(name, country).getFirst();
    }

    @Override
    public AreaGeografica cercaAreaGeografica(double latitude, double longitude) throws RemoteException {
        return new ListaAree().cercaAreeGeografiche(latitude, longitude).getFirst();
    }

    @Override
    public AreaGeografica getAreaGeografica(int geoID) throws RemoteException {
        try (var stmt = ServerCM.db.prepareStatement(
                """
                   SELECT *
                   FROM "CoordinateMonitoraggio"
                   WHERE "GeoID" = ?;
                   """
        )) {
            stmt.setInt(1, geoID);
            ResultSet set = stmt.executeQuery();
            set.next();
            return new AreaGeografica(
                    set.getLong("GeoID"),
                    set.getDouble("Latitude"),
                    set.getDouble("Longitude"),
                    set.getString("CountryCode"),
                    set.getString("Name")
            );
        } catch (SQLException e) {
            e.printStackTrace();
		}

		return new AreaGeografica();
    }

    //TODO
    @Override
    public Misurazione visualizzaAreaGeografica(int geoID) throws RemoteException {
        return null;
    }

    //TODO
    @Override
    public Operatore registrazione(Operatore operator, String password) throws RemoteException {
        return null;
    }

    //TODO
    @Override
    public Operatore login(int userID, String password) throws RemoteException {
        return null;
    }

    //TODO
    @Override
    public CentroMonitoraggio registraCentroAree(String name, String street, int civic, int zipcode, String town, String province, ListaAree areas) throws RemoteException {
        return null;
    }

    //TODO
    @Override
    public boolean alterListaAreeCentro(CentroMonitoraggio center, ListaAree newlist) throws RemoteException {
        return false;
    }

    //TODO
    @Override
    public ListaAree getListaAree(int centerID) throws RemoteException {
        return null;
    }

    //TODO
    @Override
    public boolean inserisciParametriClimatici(Misurazione misurazione) throws RemoteException {
        return false;
    }

    //TODO
    @Override
    public Misurazione getMisurazione(int recordID) throws RemoteException {
        return null;
    }
}
