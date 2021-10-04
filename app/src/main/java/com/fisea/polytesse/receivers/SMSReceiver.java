package com.fisea.polytesse.receivers;

import static android.telephony.SmsMessage.createFromPdu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.fisea.polytesse.services.SlangWordsCounterService;

public class SMSReceiver extends BroadcastReceiver {

    public static final String pdu_type = "pdus";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String format = bundle.getString("format");
        Object[] pdus = (Object[]) bundle.get(pdu_type);

        String strMessage = "";
        String origin = null;

        if (pdus != null) {
            for (Object pdu : pdus) {
                SmsMessage message = createFromPdu((byte[]) pdu, format);
                strMessage += message.getMessageBody();

                if (origin == null) {
                    origin = message.getOriginatingAddress();
                }
            }
        }

        Intent serviceIntent = new Intent(context, SlangWordsCounterService.class);
        serviceIntent.putExtra("SMSMessage", strMessage);
        serviceIntent.putExtra("origin", origin);
        context.startService(serviceIntent);
    }
}
