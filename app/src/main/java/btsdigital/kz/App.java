package btsdigital.kz;

import android.app.Application;

import com.facebook.stetho.Stetho;
import btsdigital.kz.di.DaggerDiComponent;
import btsdigital.kz.di.DiComponent;
import btsdigital.kz.di.DiModule;
import btsdigital.kz.utils.ReleaseTree;
import timber.log.Timber;

public class App extends Application {
    private DiComponent mDiComponent;

    private static App app;

    public static App getApp() {
        return app;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        app = this;

        mDiComponent = DaggerDiComponent.builder()
                .diModule(new DiModule(this)).build();

        mDiComponent.inject(this);

        Stetho.initializeWithDefaults(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree() {
                @Override
                protected String createStackElementTag(StackTraceElement element) {
                    return super.createStackElementTag(element) + ":" + element.getLineNumber();
                }
            });
        } else {
            //Release mode
            Timber.plant(new ReleaseTree());
        }
    }

    public DiComponent getmDiComponent() {
        return mDiComponent;
    }
}
