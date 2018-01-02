package io.agora.openvcall.model;


public class Message {

    private String mIcon;
    private User mSender;
    private String mContent;
    private int mType;

    public Message(int type, User sender, String content, String icon) {
        mType = type;
        mSender = sender;
        mContent = content;
        mIcon = icon;

    }

    public Message(User sender, String content, String icon) {
        this(0, sender, content, icon);
    }

    public User getSender() {
        return mSender;
    }

    public String getContent() {
        return mContent;
    }

    public String getIcon()
    {
        return mIcon;
    }

    public int getType() {
        return mType;
    }

    public static final int MSG_TYPE_TEXT = 1; // CHANNEL
}
