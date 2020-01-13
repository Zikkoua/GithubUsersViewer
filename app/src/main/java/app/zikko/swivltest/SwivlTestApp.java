package app.zikko.swivltest;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class SwivlTestApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
