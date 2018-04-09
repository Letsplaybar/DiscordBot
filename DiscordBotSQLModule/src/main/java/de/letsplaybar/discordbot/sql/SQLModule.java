package de.letsplaybar.discordbot.sql;

import de.letsplaybar.discordbot.main.module.Module;
import de.letsplaybar.discordbot.sql.utils.SQLLite;
import lombok.Getter;

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
            sql.createTableIfNotExist("Settings", new String[]{"Key", "Value"}, new String[]{"VARCHAR[100]", "VARCHAR[100]"});
            sql.createTableIfNotExist("Permissions", new String[]{"Username", "Userid"}, new String[]{"VARCHAR[100]", "VARCHAR[100]"});
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unload() {
        sql.disconect();
    }
}
