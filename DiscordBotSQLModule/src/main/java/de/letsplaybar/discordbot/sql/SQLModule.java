package de.letsplaybar.discordbot.sql;

import de.letsplaybar.discordbot.main.module.Module;
import de.letsplaybar.discordbot.sql.utils.SQLLite;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import net.dv8tion.jda.api.OnlineStatus;
import org.json.JSONArray;
import org.json.JSONException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

public class SQLModule implements Module {

    private @Getter static SQLModule instance;
    private @Getter SQLLite sql;

    @Override
    public void register() {
        instance = this;
        sql = new SQLLite();
    }

    @Override
    public void load() {
        try {
            sql.connect();
            sql.createTableIfNotExist("Settings", new String[]{"Key", "Value"}, new String[]{"VARCHAR[100]",
                    "VARCHAR[100]"});
            sql.createTableIfNotExist("Permissions", new String[]{"Username", "Userid","Permissions"},
                    new String[]{"VARCHAR[100]", "VARCHAR[100]","TEXT"});

            sql.createTableIfNotExist("playlist",new String[]{"ListName","TableName"},
                    new String[]{"VARCHAR(100)","VARCHAR(100)"});
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unload() {
        sql.disconect();
    }

    public String getToken(){
        ResultSet rs = SQLModule.getInstance().getSql().getResult("SELECT * FROM Settings WHERE Key='Token'");
        try  {

            try {
                while (rs.next())
                    return rs.getString("Value");
            } catch (SQLException e) {
            }
            return "";
        }finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void setBingAPI(String key){
        try {
            if(sql.isInDatabase("BINGAPI","Settings","Key"))
                sql.update("DELETE FROM Settings WHERE Key='BINGAPI'");
            sql.update("INSERT INTO Settings(Key,Value) VALUES ('BINGAPI','"+key+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setLizenz(String key){
        try {
            if(sql.isInDatabase("Lizenz","Settings","Key"))
                sql.update("DELETE FROM Settings WHERE Key='Lizenz'");
            sql.update("INSERT INTO Settings(Key,Value) VALUES ('Lizenz','"+key+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setGiphy(String key){
        try {
            if(sql.isInDatabase("Giphy","Settings","Key"))
                sql.update("DELETE FROM Settings WHERE Key='Giphy'");
            sql.update("INSERT INTO Settings(Key,Value) VALUES ('Giphy','"+key+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setKey(String key){
        try {
            if(sql.isInDatabase("Weatherkey","Settings","Key"))
                sql.update("DELETE FROM Settings WHERE Key='Weatherkey'");
            sql.update("INSERT INTO Settings(Key,Value) VALUES ('Weatherkey','"+key+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setOnline(String key){
        try {
            if(sql.isInDatabase("Online","Settings","Key"))
                sql.update("DELETE FROM Settings WHERE Key='Online'");
            sql.update("INSERT INTO Settings(Key,Value) VALUES ('Online','"+key+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setStreamt(String key){
        try {
            if(sql.isInDatabase("Streamt","Settings","Key"))
                sql.update("DELETE FROM Settings WHERE Key='Streamt'");
            sql.update("INSERT INTO Settings(Key,Value) VALUES ('Streamt','"+key+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setSpielt(String key){
        try {
            if(sql.isInDatabase("Spielt","Settings","Key"))
                sql.update("DELETE FROM Settings WHERE Key='Spielt'");
            sql.update("INSERT INTO Settings(Key,Value) VALUES ('Spielt','"+key+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getState(){
        ResultSet rs =sql.getResult("SELECT * FROM Settings WHERE Key='State'");
        try {
            while(rs.next())
                return rs.getString("Value");
        } catch (SQLException e) {
        }finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public String getDetails(){
        ResultSet rs =sql.getResult("SELECT * FROM Settings WHERE Key='Details'");
        try {
            while(rs.next())
                return rs.getString("Value");
        } catch (SQLException e) {
        }finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public String getAID(){
        ResultSet rs =sql.getResult("SELECT * FROM Settings WHERE Key='AID'");
        try {
            while(rs.next())
                return rs.getString("Value");
        } catch (SQLException e) {
        }finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public String getLI(){
        ResultSet rs =sql.getResult("SELECT * FROM Settings WHERE Key='LI'");
        try {
            while(rs.next())
                return rs.getString("Value");
        } catch (SQLException e) {
        }finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public String getSI(){
        ResultSet rs =sql.getResult("SELECT * FROM Settings WHERE Key='SI'");
        try {
            while(rs.next())
                return rs.getString("Value");
        } catch (SQLException e) {
        }finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public String getLT(){
        ResultSet rs =sql.getResult("SELECT * FROM Settings WHERE Key='LT'");
        try {
            while(rs.next())
                return rs.getString("Value");
        } catch (SQLException e) {
        }finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public String getST(){
        ResultSet rs =sql.getResult("SELECT * FROM Settings WHERE Key='ST'");
        try {
            while(rs.next())
                return rs.getString("Value");
        } catch (SQLException e) {
        }finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public void setST(String key){
        try {
            if(sql.isInDatabase("ST","Settings","Key"))
                sql.update("DELETE FROM Settings WHERE Key='ST'");
            sql.update("INSERT INTO Settings(Key,Value) VALUES ('ST','"+key+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setLT(String key){
        try {
            if(sql.isInDatabase("LT","Settings","Key"))
                sql.update("DELETE FROM Settings WHERE Key='LT'");
            sql.update("INSERT INTO Settings(Key,Value) VALUES ('LT','"+key+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setSI(String key){
        try {
            if(sql.isInDatabase("SI","Settings","Key"))
                sql.update("DELETE FROM Settings WHERE Key='SI'");
            sql.update("INSERT INTO Settings(Key,Value) VALUES ('SI','"+key+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setLI(String key){
        try {
            if(sql.isInDatabase("LI","Settings","Key"))
                sql.update("DELETE FROM Settings WHERE Key='LI'");
            sql.update("INSERT INTO Settings(Key,Value) VALUES ('LI','"+key+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setAID(String key){
        try {
            if(sql.isInDatabase("AID","Settings","Key"))
                sql.update("DELETE FROM Settings WHERE Key='AID'");
            sql.update("INSERT INTO Settings(Key,Value) VALUES ('AID','"+key+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setDetails(String key){
        try {
            if(sql.isInDatabase("Details","Settings","Key"))
                sql.update("DELETE FROM Settings WHERE Key='Details'");
            sql.update("INSERT INTO Settings(Key,Value) VALUES ('Details','"+key+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setState(String key){
        try {
            if(sql.isInDatabase("State","Settings","Key"))
                sql.update("DELETE FROM Settings WHERE Key='State'");
            sql.update("INSERT INTO Settings(Key,Value) VALUES ('State','"+key+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public OnlineStatus getOnline(){
        ResultSet rs =sql.getResult("SELECT * FROM Settings WHERE Key='Online'");
        try {
            while(rs.next())
                return OnlineStatus.valueOf(rs.getString("Value"));
        } catch (SQLException e) {
        }finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return OnlineStatus.ONLINE;
    }

    public String getStreamt(){
        ResultSet rs =sql.getResult("SELECT * FROM Settings WHERE Key='Streamt'");
        try {
            while(rs.next()){
                return rs.getString("Value");
            }
        } catch (SQLException e) {
        }finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return "DEFAULT";
    }
    public String getSpielt(){
        ResultSet rs =sql.getResult("SELECT * FROM Settings WHERE Key='Spielt'");
        try {
            while(rs.next())
                return rs.getString("Value");
        } catch (SQLException e) {
        }finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public String getGiphy(){
        ResultSet rs =sql.getResult("SELECT * FROM Settings WHERE Key='Giphy'");
        try {
            try {
                while (rs.next())
                    return rs.getString("Value");
            } catch (SQLException e) {
            }
            return "";
        }finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public String getKey(){
        ResultSet rs =sql.getResult("SELECT * FROM Settings WHERE Key='Weatherkey'");
        try {
            while(rs.next())
                return rs.getString("Value");
        } catch (SQLException e) {
        }finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public String getLizenz(){
        ResultSet rs =sql.getResult("SELECT * FROM Settings WHERE Key='Lizenz'");
        try {
            while(rs.next())
                return rs.getString("Value");
        } catch (SQLException e) {
        }finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return "BETA";
    }

    public void setToken(String key){
        try {
            if(SQLModule.getInstance().getSql().isInDatabase("Token","Settings","Key"))
                SQLModule.getInstance().getSql().update("DELETE FROM Settings WHERE Key='Token'");
            SQLModule.getInstance().getSql().update("INSERT INTO Settings(Key,Value) VALUES ('Token','"+key+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getBingAPI(){
        ResultSet rs =sql.getResult("SELECT * FROM Settings WHERE Key='BINGAPI'");
        try {
            while(rs.next())
                return rs.getString("Value");
        } catch (SQLException e) {
        }finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public List<String> getPlayList() throws SQLException {
        ResultSet rs = sql.getResult("SELECT * FROM playlist");
        try {
            List<String> name = new ArrayList<>();
            while (rs.next()) {
                name.add(rs.getString("ListName"));
            }
            return name;
        }finally {
            rs.close();
        }
    }

    public String getPlaylistTableName(String playlist) throws SQLException {
        ResultSet rs = sql.getWert("playlist", "ListName", playlist);
        try {
            while (rs.next())
                return "'" + rs.getString("TableName") + "'";
            throw new NullPointerException("No Playlist found");
        }finally {
            rs.close();
        }
    }

    public void createPlaylist(String playlist){
        UUID id = UUID.randomUUID();
        sql.createDatabaseEintrag("playlist","ListName,TableName","'"+playlist+"','"+id+"'");
        sql.createTableIfNotExist("'"+id.toString()+"'",new String[]{"SongName","SongURL"},
                new String[]{"VARCHAR[100]", "text"});
    }

    public void deletePlaylist(String playlist) throws SQLException, ClassNotFoundException {
        String id = getPlaylistTableName(playlist);
        sql.update("DROP TABLE "+id);
        sql.update("DELETE FROM playlist WHERE ListName='"+playlist+"'");
    }

    public List<String> getPlaylistTitel(String playlist) throws SQLException {
        List<String> titel = new ArrayList<>();
        ResultSet rs = sql.getResult("SELECT * FROM "+getPlaylistTableName(playlist)+"");
        try {
            while (rs.next())
                titel.add(rs.getString("SongName"));
            return titel;
        }finally {
            rs.close();
        }
    }

    public String getSongURL(String playlist, String titel) throws SQLException {
        ResultSet rs = sql.getWert(getPlaylistTableName(playlist),"SongName",titel);
        try {
            while (rs.next())
                return rs.getString("SongURL");
            throw new NullPointerException("Song not Found");
        }finally {
            rs.close();
        }
    }

    public void addSong(String playlist,String name, String url) throws SQLException {
        if(sql.isInDatabase(name,getPlaylistTableName(playlist),"SongName"))
            return;
        sql.createDatabaseEintrag(getPlaylistTableName(playlist),"SongName,SongURL","'"+name+
                "','"+url+"'");
    }

    public void removeSong(String playlist,String name) throws SQLException {
        sql.update("DELETE FROM "+getPlaylistTableName(playlist)+" WHERE SongName='"+name+"'");
    }

    public void addUser(String username, String id) throws SQLException {
        if(sql.isInDatabase(id,"Permissions","Userid"))
            return;
        sql.createDatabaseEintrag("Permissions","Username, Userid, Permissions","'"+username+"','"+id+"','[]'");
    }
    public void removeUser(String id) throws SQLException {
        if(sql.isInDatabase(id,"Permissions","Userid"))
            sql.update("DELETE FROM Permissions WHERE Userid='"+id+"'");
    }

    private JSONArray getPermmisions(String id) throws SQLException {
        ResultSet rs = sql.getWert("Permissions","Userid",id);
        try{
            while (rs.next())
                return new JSONArray(rs.getString("Permissions"));
        }finally {
            rs.close();
        }
        return new JSONArray();
    }

    public void addPermisson(String id, String perm) throws SQLException {
        if(!hasPermission(id,perm))
            sql.update("UPDATE Permissions SET Permissions='"+getPermmisions(id).put(perm).toString()+"' WHERE Userid='"+id+"'");
    }

    public void removePermisson(String id, String perm) throws SQLException {
        if(sql.isInDatabase(id,"Permissions","Userid")){
            JSONArray array= getPermmisions(id);
            ArrayList<Object> perms = convert(array);
            if(perms.contains(perm)){
                perms.remove(perm);
                sql.update("UPDATE Permissions SET Permissions='"+convert(perms).toString()+"' WHERE Userid='"+id+"'");
            }
        }
    }

    public boolean hasPermission(String id, String perm) throws SQLException {
        if(sql.isInDatabase(id,"Permissions","Userid")) {
            String[] perms = perm.split(Pattern.quote("."));
            ArrayList<Object> permissions = convert(getPermmisions(id));
            String permstring = "";
            if (permissions.contains("*"))
                return true;
            for (String pe : perms) {
                permstring += pe;
                if (permissions.contains(permstring) || permissions.contains(permstring + ".*")) {
                    return true;
                }
                permstring += ".";
            }
        }
        return false;
    }

    private ArrayList<Object> convert(JSONArray jArr)
    {
        ArrayList<Object> list = new ArrayList<Object>();
        try {
            for (int i=0, l=jArr.length(); i<l; i++){
                list.add(jArr.get(i));
            }
        } catch (JSONException e) {}

        return list;
    }

    private JSONArray convert(Collection<Object> list)
    {
        return new JSONArray(list);
    }

    public ArrayList<Object> getPermissions(String id) throws SQLException {
        return convert(getPermmisions(id));
    }

    public ArrayList<User> getUser() throws SQLException {
        ArrayList<User> user = new ArrayList<>();
        ResultSet rs = sql.query("SELECT * FROM Permissions");
        try {
            while (rs.next()){
                user.add(new User(rs.getString("Username"),rs.getString("Userid"),rs.getString("Permissions")));
            }
        }finally {
            rs.close();
            return user;
        }
    }

    public static class User{
        private StringProperty name;
        private StringProperty id;
        private StringProperty permissions;

        public User(String name, String id, String permissions){
            this.name = new SimpleStringProperty(name);
            this.id = new SimpleStringProperty(id);
            this.permissions = new SimpleStringProperty(permissions);
        }

        public void setName(String name){
            this.name.set(name);
        }

        public String getName(){
            return name.get();
        }

        public StringProperty namePropertiy(){
            return name;
        }

        public void setId(String id){
            this.id.set(id);
        }

        public String getId(){
            return id.get();
        }

        public StringProperty idPropertiy(){
            return id;
        }

        public void setPermissions(String permissions){
            this.permissions.set(permissions);
        }

        public String getPermissions(){
            return permissions.get();
        }

        public  StringProperty permissionsProperty(){
            return permissions;
        }

    }

}
