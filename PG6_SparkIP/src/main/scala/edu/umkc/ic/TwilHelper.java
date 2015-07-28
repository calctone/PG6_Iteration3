package edu.umkc.ic;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.SmsFactory;
import com.twilio.sdk.resource.instance.Sms;
import com.twilio.sdk.resource.list.SmsList;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/**
 * Created by jeff on 7/27/15.
 */
public class TwilHelper {
    // Find your Account Sid and Token at twilio.com/user/account
    public static final String ACCOUNT_SID = "AC58d6371746b89f7e0b89a52492fee638";
    public static final String AUTH_TOKEN = "23613fd085a376bcfdbfe916118c6713";

    public static void sendMessage(String msg) throws TwilioRestException {
    //public static void main(String[] args) throws TwilioRestException {
        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

        // Build a filter for the SmsList
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("Body", msg));
        params.add(new BasicNameValuePair("To", "+19137306622"));
        params.add(new BasicNameValuePair("From", "+18163988624"));

        SmsFactory smsFactory = client.getAccount().getSmsFactory();
        Sms sms = smsFactory.create(params);
        System.out.println(sms.getSid());
    }
}
