package de.letsplaybar.discordbot.sql.utils;

import java.io.File;
import java.sql.*;

/**
 * @author Letsplaybar
 *         Created on 07.04.2017.
 */
public class SQLLite {
    private Connection con;

    public boolean exist(){
      return new File(".data/",".db.db").exists();
    }

    public void connect() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        try {
            con = DriverManager.getConnection("jdbc:sqlite://"+new File(".data/.").getAbsolutePath()+"db.db");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public boolean isConected() {
        try {
            if (con.isClosed() || con == null) {
                return false;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.err.println(e.getMessage());
        }
        return true;
    }

    public void disconect() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public ResultSet query(String query) {
        ResultSet rs = null;
        try {
            Statement stm = con.createStatement();
            rs = stm.executeQuery(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rs;
    }

    public void update(String query) {
        try {
            Statement stm = con.createStatement();
            System.out.println("Ausgefuehrte Abfrage: " + query);
            stm.executeUpdate(query);
            stm.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public boolean isInDatabase(String key, String tabelle, String spalte) throws SQLException {

        boolean isInDatabase = false;

        ResultSet rs = query("SELECT * FROM " + tabelle + " WHERE " + spalte + " = '" + key + "'");
        try {
            if (rs.next()) {
                isInDatabase = true;
            } else {
                isInDatabase = false;

            }

            return Boolean.valueOf(isInDatabase).booleanValue();
        }finally {
            rs.close();
        }

    }

    public ResultSet getResult(String query) {
        if (isConected()) {
            try {
                return con.createStatement().executeQuery(query);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                System.err.println(e.getMessage());
            }
        } else {
            getResult(query);
        }


        return null;

    }

    public ResultSet getPrepairedResult(String query) {
        if (isConected()) {
            try {
                return con.prepareStatement(query).executeQuery();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        } else {
            getPrepairedResult(query);
        }
        return null;
    }

    public void createTableIfNotExist(String table,String[] namewert,String[] wert) {
        String query = "";
        if(namewert.length != wert.length)
            return;
        for(int i = 0; i<namewert.length;i++){
            query = query +namewert[i]+" "+wert[i]+",";
        }
        query =query.substring(0,query.length()-1);
        update("CREATE TABLE IF NOT EXISTS "+table+"(" + query+")");
    }

    public void createTable(String table,String[] namewert,String[] wert) {
        String query = "";
        if(namewert.length != wert.length)
            return;
        for(int i = 0; i<namewert.length;i++){
            query = query +namewert[i]+" "+wert[i]+",";
        }
        query =query.substring(0,query.length()-1);
        update("CREATE TABLE "+table+"(" + query+")");
    }

    public void createDatabaseEintrag(String table, String spalten, String werte) {
        update("INSERT INTO " + table + "(" + spalten + ") VALUES(" + werte + ")");
    }

    public void setWertInTable(String table, String spalte, String wert, String fromSpalte, String wertFromSpalte) {
        update("UPDATE " + table + " SET " + spalte + "='" + wert + "' WHERE " + fromSpalte + "='" + wertFromSpalte + "'");
    }

    public void setWertInTable(String table, String spalte, int wert, String fromSpalte, String wertFromSpalte) {
        update("UPDATE " + table + " SET " + spalte + "=" + wert + " WHERE " + fromSpalte + "='" + wertFromSpalte + "'");
    }

    public void setNULLInTable(String table, String spalte, String fromSpalte, String wertFromSpalte) {
        update("UPDATE " + table + " SET " + spalte + "=" + null + " WHERE " + fromSpalte + "='" + wertFromSpalte + "'");
    }

    public void setWertInTable(String table, String spalte, double wert, String fromSpalte, String wertFromSpalte) {
        update("UPDATE " + table + " SET " + spalte + "='" + wert + "' WHERE " + fromSpalte + "='" + wertFromSpalte + "'");
    }

    public ResultSet getWert(String table, String spalte, String fromSpalte) {
        return getResult("SELECT * FROM " + table + " WHERE " + spalte + " ='" + fromSpalte + "'");
    }

    public ResultSet getWert(String table, String spalte, int fromSpalte) {
        return getResult("SELECT * FROM " + table + " WHERE " + spalte + " =" + fromSpalte);
    }

    public ResultSet getWert(String table, String spalte, double fromSpalte) {
        return getResult("SELECT * FROM " + table + " WHERE " + spalte + " =" + fromSpalte);
    }

    public ResultSet getWertfromNULL(String table, String spalte) {
        return getResult("SELECT * FROM " + table + " WHERE " + spalte + " =" + null);
    }

    public ResultSet sortierTable(String table, String bySpalte) {
        return getPrepairedResult("SELECT * FROM " + table + " ORDER BY " + bySpalte + " DESC");
    }

    public ResultSet giveTop3(String table, String bySpalte) {
        return getPrepairedResult("SELECT * FROM " + table + " ORDER BY " + bySpalte + " DESC LIMIT 3");
    }
}

