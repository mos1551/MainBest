package com.circlemind;

public class Voice {

    private String voiceName;
    private String voiceTime;
    private String voicePath;


    public Voice() {}
    public Voice(String name, String time, String path) {
        this.voiceName = name;
        this.voiceTime = time;
        this.voicePath = path;
    }

    public String getVoiceName() {
        return voiceName;
    }

    public void setVoiceName(String voiceName) {
        this.voiceName = voiceName;
    }

    public String getVoiceTime() {
        return voiceTime;
    }

    public void setVoiceTime(String voiceTime) {
        this.voiceTime = voiceTime;
    }

    public String getVoicePath() {
        return voicePath;
    }

    public void setVoicePath(String voicePath) {
        this.voicePath = voicePath;
    }
}
