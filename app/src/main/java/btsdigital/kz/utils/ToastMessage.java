package btsdigital.kz.utils;

import android.arch.lifecycle.LifecycleOwner;

public class ToastMessage extends SingleLiveEvent<String> {

    public void observe(LifecycleOwner owner, final ToastObserver observer) {
        super.observe(owner, o -> {
            if (o == null) {
                return;
            }
            observer.onNewMessage(o);
        });
    }

    public interface ToastObserver {
        /**
         * Called when there is a new message to be shown.
         */

        void onNewMessage(String message);
    }
}
