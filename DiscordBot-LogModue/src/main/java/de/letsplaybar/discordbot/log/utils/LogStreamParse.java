package de.letsplaybar.discordbot.log.utils;

import javafx.scene.control.TextArea;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class LogStreamParse {

    private File file = new File(".data/log/","last.log");
    private FileWriter writer;
    private BufferedReader reader;
    private DateFormat formater;

    public LogStreamParse() {
        if(file.exists()){
            formater = new SimpleDateFormat("yyyy_MM_dd HH:mm:ss:SSS");
            try {
                File zipfile = new File(".data/log/",formater.format(new Date()).replace(" ","_").replace(":",".")+".zip");
                zipfile.createNewFile();
                ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipfile));
                out.putNextEntry(new ZipEntry(file.getName()));
                byte[] buf = new byte[1024*4];
                FileInputStream fis = new FileInputStream(file);
                int len = fis.read(buf);
                while(len > -1){
                    out.write(buf,0,len);
                    len= fis.read(buf);
                }
                fis.close();
                out.closeEntry();
                out.close();
                file.delete();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
            writer = new FileWriter(file);
            reader = new BufferedReader(new FileReader(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void register(){
        System.setOut(new PrintStream(System.out){
            @Override
            public void print(Object obj) {
                super.print(obj);
                log(obj,true);
            }

            @Override
            public void print(boolean b) {
                super.print(b);
                log(b,true);
            }

            @Override
            public void print(char c) {
                super.print(c);
                log(c,true);
            }

            @Override
            public void print(int i) {
                super.print(i);
                log(i,true);
            }

            @Override
            public void print(long l) {
                super.print(l);
                log(l,true);
            }

            @Override
            public void print(float f) {
                super.print(f);
                log(f,true);
            }

            @Override
            public void print(double d) {
                super.print(d);
                log(d,true);
            }

            @Override
            public void print(char[] s) {
                super.print(s);
                log(s,true);
            }

            @Override
            public void print(String s) {
                super.print(s);
                log(s,true);
            }
        });
        System.setErr(new PrintStream(System.err){
            @Override
            public void print(Object obj) {
                super.print(obj);
                log(obj,false);
            }

            @Override
            public void print(boolean b) {
                super.print(b);
                log(b,false);
            }

            @Override
            public void print(char c) {
                super.print(c);
                log(c,false);
            }

            @Override
            public void print(int i) {
                super.print(i);
                log(i,false);
            }

            @Override
            public void print(long l) {
                super.print(l);
                log(l,false);
            }

            @Override
            public void print(float f) {
                super.print(f);
                log(f,false);
            }

            @Override
            public void print(double d) {
                super.print(d);
                log(d,false);
            }

            @Override
            public void print(char[] s) {
                super.print(s);
                log(s,false);
            }

            @Override
            public void print(String s) {
                super.print(s);
                log(s,false);
            }
        });
    }

    private TextArea log_field;

    public void setLog_field(TextArea log_field){
        this.log_field = log_field;
    }

    public void reset(){
        log_field = null;
    }

    private void log(Object o, boolean isOut){
        try {
            writer.write(formater.format(new Date())+" ["+(isOut?"Info":"Error")+"] "+o.toString()+"\n");
            writer.flush();
            if(log_field != null)
                log_field.appendText(formater.format(new Date())+" ["+(isOut?"Info":"Error")+"]" +o.toString()+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getLog() throws IOException {
        String out = "";
        String line;
        while ((line= reader.readLine())!=null )
            out += line+"\n";
        reader.close();
        reader = new BufferedReader(new FileReader(file));
        return out;
    }

    public void onUnload(){
        try {
            reader.close();
            writer.close();
            reader = null;
            writer = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onLoad() throws IOException {
        if( writer == null)
            writer = new FileWriter(file);
        if (reader == null)
            reader = new BufferedReader(new FileReader(file));
    }

}
