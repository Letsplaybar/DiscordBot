package de.letsplaybar.discordbot.record.utils;

import lombok.Getter;
import net.dv8tion.jda.core.audio.AudioReceiveHandler;
import net.dv8tion.jda.core.audio.CombinedAudio;
import net.dv8tion.jda.core.audio.UserAudio;
import net.dv8tion.jda.core.entities.User;
import net.sourceforge.lame.lowlevel.LameEncoder;
import net.sourceforge.lame.mp3.Lame;
import net.sourceforge.lame.mp3.MPEGMode;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecordingHandler implements AudioReceiveHandler {

    private User user;
    private @Getter File file;
    private OutputStream stream;
    private List<Byte> bytes;


    public RecordingHandler(User user) throws IOException {
        this(user,user.getName());
    }

    public RecordingHandler(User user, String name) throws IOException {
        this.user = user;
        this.file = new File(".data/records",name+".wav");
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        file.createNewFile();
        stream = new FileOutputStream(file);
        bytes = new ArrayList<>();
    }

    public RecordingHandler(String name) throws IOException {
        this(null,name);
    }

    @Override
    public boolean canReceiveCombined() {
        return user == null;
    }

    @Override
    public boolean canReceiveUser() {
        return user != null;
    }

    @Override
    public void handleCombinedAudio(CombinedAudio combinedAudio) {
        if(canReceiveCombined()){
            for(byte b: combinedAudio.getAudioData(10))
                bytes.add(b);
        }
    }

    @Override
    public void handleUserAudio(UserAudio userAudio) {
        System.out.println("test2");
        if(canReceiveUser()){
            if(userAudio.getUser().getId().equals(user.getId())) {
                try {
                    stream.write(encodePcmToMp3(userAudio.getAudioData(1)));
                    stream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void end() throws IOException {
        byte[] pcm = new byte[bytes.size()];
        for(int i = 0; i < bytes.size(); i++){
            pcm[i]= bytes.get(i);
        }
        initPCMtoFile(stream,pcm,48000,2,16);
    }

    private byte[] encodePcmToMp3(byte[] pcm)
    {
        System.out.println("1");
        LameEncoder encoder;
        try {
            encoder = new LameEncoder(AudioReceiveHandler.OUTPUT_FORMAT, 256, MPEGMode.STEREO, Lame.QUALITY_HIGHEST, true);
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }

        System.out.println("2");
        ByteArrayOutputStream mp3 = new ByteArrayOutputStream();

        System.out.println("3");
        byte[] buffer = new byte[encoder.getPCMBufferSize()];

        System.out.println("4");
        int bytesToTransfer = Math.min(buffer.length, pcm.length);
        System.out.println("ByteToTrans:"+bytesToTransfer);
        int bytesWritten;

        System.out.println("5");
        int currentPcmPosition = 0;

        System.out.println("6");
        while (0 < (bytesWritten = encoder.encodeBuffer(pcm, currentPcmPosition, bytesToTransfer, buffer)))
        {
            currentPcmPosition += bytesToTransfer;
            bytesToTransfer = Math.min(buffer.length, pcm.length - currentPcmPosition);
            System.out.println(buffer);
            mp3.write(buffer, 0, bytesWritten);
        }

        encoder.close();
        return mp3.toByteArray();
    }

    public void initPCMtoFile(OutputStream os, byte[] pcmdata, int srate, int channel, int format) throws IOException {
        byte[] header = new byte[44];
        byte[] data = get16BitPcm(pcmdata);

        long totalDataLen = data.length + 36;
        long bitrate = srate * channel * format;

        header[0] = 'R';
        header[1] = 'I';
        header[2] = 'F';
        header[3] = 'F';
        header[4] = (byte) (totalDataLen & 0xff);
        header[5] = (byte) ((totalDataLen >> 8) & 0xff);
        header[6] = (byte) ((totalDataLen >> 16) & 0xff);
        header[7] = (byte) ((totalDataLen >> 24) & 0xff);
        header[8] = 'W';
        header[9] = 'A';
        header[10] = 'V';
        header[11] = 'E';
        header[12] = 'f';
        header[13] = 'm';
        header[14] = 't';
        header[15] = ' ';
        header[16] = (byte) format;
        header[17] = 0;
        header[18] = 0;
        header[19] = 0;
        header[20] = 1;
        header[21] = 0;
        header[22] = (byte) channel;
        header[23] = 0;
        header[24] = (byte) (srate & 0xff);
        header[25] = (byte) ((srate >> 8) & 0xff);
        header[26] = (byte) ((srate >> 16) & 0xff);
        header[27] = (byte) ((srate >> 24) & 0xff);
        header[28] = (byte) ((bitrate / 8) & 0xff);
        header[29] = (byte) (((bitrate / 8) >> 8) & 0xff);
        header[30] = (byte) (((bitrate / 8) >> 16) & 0xff);
        header[31] = (byte) (((bitrate / 8) >> 24) & 0xff);
        header[32] = (byte) ((channel * format) / 8);
        header[33] = 0;
        header[34] = 16;
        header[35] = 0;
        header[36] = 'd';
        header[37] = 'a';
        header[38] = 't';
        header[39] = 'a';
        header[40] = (byte) (data.length  & 0xff);
        header[41] = (byte) ((data.length >> 8) & 0xff);
        header[42] = (byte) ((data.length >> 16) & 0xff);
        header[43] = (byte) ((data.length >> 24) & 0xff);

        os.write(header, 0, 44);
        os.write(data);
        os.flush();
        os.close();
    }

    public byte[] get16BitPcm(byte[] data) {
        byte[] resultData = new byte[2 * data.length];
        int iter = 0;
        for (double sample : data) {
            short maxSample = (short)((sample * Short.MAX_VALUE));
            resultData[iter++] = (byte)(maxSample & 0x00ff);
            resultData[iter++] = (byte)((maxSample & 0xff00) >>> 8);
        }
        return resultData;
    }
}
