package commitware.ayia.notification.model;

public class Channel {

    protected String mChannelId;
    protected CharSequence mChannelName;
    protected String mChannelDescription;
    protected int mChannelImportance;
    protected boolean mChannelEnableVibrate;
    protected int mChannelLockscreenVisibility;

    public Channel(String mChannelId, CharSequence mChannelName, String mChannelDescription, int mChannelImportance, boolean mChannelEnableVibrate, int mChannelLockscreenVisibility) {
        this.mChannelId = mChannelId;
        this.mChannelName = mChannelName;
        this.mChannelDescription = mChannelDescription;
        this.mChannelImportance = mChannelImportance;
        this.mChannelEnableVibrate = mChannelEnableVibrate;
        this.mChannelLockscreenVisibility = mChannelLockscreenVisibility;
    }

    public String getChannelId() {
        return mChannelId;
    }

    public CharSequence getChannelName() {
        return mChannelName;
    }

    public String getChannelDescription() {
        return mChannelDescription;
    }


    public int getChannelImportance() {
        return mChannelImportance;
    }

    public boolean isChannelEnableVibrate() {
        return mChannelEnableVibrate;
    }

    public int getChannelLockscreenVisibility() {
        return mChannelLockscreenVisibility;
    }


}
