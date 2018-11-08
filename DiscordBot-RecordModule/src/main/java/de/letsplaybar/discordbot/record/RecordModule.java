package de.letsplaybar.discordbot.record;

import de.letsplaybar.discordbot.command.CommandModule;
import de.letsplaybar.discordbot.main.module.Module;
import de.letsplaybar.discordbot.record.commands.Record;
import de.letsplaybar.discordbot.record.listener.DatenschutzListener;
import de.letsplaybar.discordbot.record.utils.RecordingHandler;
import lombok.Getter;
import lombok.Setter;

public class RecordModule implements Module {

    private static @Getter RecordModule instance;
    private @Getter DatenschutzListener listener;
    private @Getter @Setter RecordingHandler handler;

    @Override
    public void register() {
        instance = this;
        listener = new DatenschutzListener();
    }

    @Override
    public void load() {
        CommandModule.getInstance().registerCommand("record", new Record());
    }

    @Override
    public void unload() {
        CommandModule.getInstance().unregisterCommand("record");
    }
}
