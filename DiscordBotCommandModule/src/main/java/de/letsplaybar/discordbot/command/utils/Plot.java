package de.letsplaybar.discordbot.command.utils;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lombok.Getter;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Plot {

    public static List<Color> colors;

    static {
        try {
            colors = getColors();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static List<Color> getColors() throws IllegalAccessException {
        List<Color> colors = new ArrayList<>();

        for(Field field : Color.class.getDeclaredFields()){
            if(Modifier.isStatic(field.getModifiers())){
                field.setAccessible(true);
                if(field.get(null) instanceof Color)
                    colors.add((Color) field.get(null));
            }
        }

        return colors;
    }


    public File savePlot(int wight, int height, List<Point> cordinantes, String picturename) throws IOException, InterruptedException {
        final WritableImage[] wim = {new WritableImage(wight,height)};
        HashMap<Integer,Canvas> canvas = new HashMap<>();
        HashMap<Integer, GraphicsContext> graphicsContexts = new HashMap<>();
        cordinantes.stream().forEach(cordinate -> {
            if(!canvas.containsKey(cordinate.getClazz())){
                System.out.println("create");
                canvas.put(cordinate.getClazz(),new Canvas(wight, height));
                graphicsContexts.put(cordinate.getClazz(),canvas.get(cordinate.getClazz()).getGraphicsContext2D());
                graphicsContexts.get(cordinate.getClazz()).setFill(colors.get(cordinate.getClazz()));
                graphicsContexts.get(cordinate.getClazz()).setLineWidth(10);
            }
            GraphicsContext gc = graphicsContexts.get(cordinate.getClazz());
            gc.fillOval(cordinate.getX(),cordinate.getY(),10,10);
            System.out.println(cordinate.getClazz()+" "+cordinate.getX()+" "+cordinate.getY());
            System.out.println(gc);
        });

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Canvas c = canvas.get(0);
                GraphicsContext cx = graphicsContexts.get(0);
                SnapshotParameters params = new SnapshotParameters();
                params.setFill(Color.TRANSPARENT);
                canvas.values().stream().skip(1).forEach(canvass ->{
                    System.out.println("print");
                    cx.drawImage(canvass.snapshot(params, null),canvass.getWidth(),canvass.getHeight());
                });

                wim[0] = c.snapshot(params,wim[0]);
                synchronized (Plot.this){
                    Plot.this.notify();
                }
            }
        });
        synchronized (this){
            wait();
        }
        if(!new File(".data/plots").exists())
            new File(".data/plots").mkdirs();
        ImageIO.write(
        SwingFXUtils.fromFXImage(wim[0],null),"png",new File(".data/plots/",picturename+".png"));
        return new File(".data/plots/",picturename+".png");
    }

    public static class Point{
        @Getter double x;
        @Getter double y;
        @Getter int clazz;

        public Point(double x, double y, int clazz){
            this.x = x;
            this.y = y;
            this.clazz = clazz;
        }
    }

}
