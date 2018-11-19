package org.apache.cordova.firebase;

import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import me.leolin.shortcutbadger.ShortcutBadger;

public class FirebasePluginMessageReceiverManager {

    private static List<FirebasePluginMessageReceiver> receivers = new ArrayList<FirebasePluginMessageReceiver>();

    public static void register(FirebasePluginMessageReceiver receiver) {
        receivers.add(receiver);
    }

    public static boolean onMessageReceived(RemoteMessage remoteMessage, Context context) {
        boolean handled = false;
        try{
            ShortcutBadger.applyCount(context, Integer.parseInt(remoteMessage.getData().get("badge")));
        }
        catch(Exception e){
            Log.e("NO CONTEXT PRESENT", e.getMessage());
        }
        for (FirebasePluginMessageReceiver receiver : receivers) {
            boolean wasHandled = receiver.onMessageReceived(remoteMessage);
            if (wasHandled) {
                handled = true;
            }
        }

        return handled;
    }
}
