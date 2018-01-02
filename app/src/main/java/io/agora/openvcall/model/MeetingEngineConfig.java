package io.agora.openvcall.model;

public class MeetingEngineConfig {
    public int mVideoProfile;

    public int mUid;

    public String mChannel;

    public void reset() {
        mChannel = null;
    }

    MeetingEngineConfig() {
    }
}
