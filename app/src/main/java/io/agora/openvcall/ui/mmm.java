package io.agora.openvcall.ui;

import android.content.Intent;

/**
 * Created by tianjq1 on 2017/12/26.
 */

public class mmm extends MeetingBaseActivity{


    @Override
    protected void initUIandEvent() {
        startActivity(new Intent(this, MeetingMainActivity.class));
    }

    @Override
    protected void deInitUIandEvent() {

    }

}
