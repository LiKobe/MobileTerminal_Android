package com.robotlinker.base;

import android.content.Context;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.regions.Regions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import ros.rosbridge.ROSBridgeClient;

/**
 * Created by kobeli on 2017/6/20.
 */

public class AppHelper {
    // App settings

    //rosbridge 连接
    private static ROSBridgeClient currentClient;

    public static ROSBridgeClient getRosClient() {
        return currentClient;
    }

    public static void setRosClient(ROSBridgeClient client) {
        currentClient = client;
    }

    //topic
    private static String[] topicList;
    public static String[] getTopicList() {
        return topicList;
    }
    public static void setTopicList(String[] topicList) {
        AppHelper.topicList = topicList;
    }

    //当前topic detail
    private static String currentTopicDetail;
    public static String getCurrentTopicDetail() {
        if (currentTopicDetail == null)
            return "";
        return currentTopicDetail;
    }
    public static void setCurrentTopicDetail(String currentTopicDetail) {
        AppHelper.currentTopicDetail = currentTopicDetail;
    }

    //topic detail Subscribe or unSubscribe
    public void Subscribe(String detailName, boolean isSubscribe) {
        if(!isSubscribe) {
            if (getCurrentTopicDetail().equalsIgnoreCase(detailName)) {
                currentClient.send("{\"op\":\"unsubscribe\",\"topic\":\"" + detailName + "\"}");
                setCurrentTopicDetail(null);
            }
        } else {
            if (getCurrentTopicDetail().equalsIgnoreCase(detailName)) {
                return;
            }
            currentClient.send("{\"op\":\"subscribe\",\"topic\":\"" + detailName + "\"}");
            setCurrentTopicDetail(detailName);
        }
    }



    //aws cognoti 连接
    private static List<String> attributeDisplaySeq;
    private static Map<String, String> signUpFieldsC2O;
    private static Map<String, String> signUpFieldsO2C;

    private static AppHelper appHelper;
    private static CognitoUserPool userPool;
    private static String user;

    // Change the next three lines of code to run this demo on your user pool

    /**
     * Add your pool id here
     */
    private static final String userPoolId = "ap-northeast-1_PXYjoxvmM";

    /**
     * Add you app id
     */
    private static final String clientId = "5bnuk4dt3c3fto97tfhtdidqm9";

    /**
     * App secret associated with your app id - if the App id does not have an associated App secret,
     * set the App secret to null.
     * e.g. clientSecret = null;
     */
    private static final String clientSecret = "vh0t5ggpmgdv4moeus3f3o7fsrbqd9ee4qd0qpulnl6nv844iqs";

    /**
     * Set Your User Pools region.
     * e.g. if your user pools are in US East (N Virginia) then set cognitoRegion = Regions.US_EAST_1.
     */
    private static final Regions cognitoRegion = Regions.AP_NORTHEAST_1;

    // User details from the service
    private static CognitoUserSession currSession;
    private static CognitoUserDetails userDetails;

    private static boolean emailVerified;
    private static boolean emailAvailable;

    public static void init(Context context) {
        setData();

        if (appHelper != null && userPool != null) {
            return;
        }

        if (appHelper == null) {
            appHelper = new AppHelper();
        }

        if (userPool == null) {

            // Create a user pool with default ClientConfiguration
            userPool = new CognitoUserPool(context, userPoolId, clientId, clientSecret, cognitoRegion);

            // This will also work
            /*
            ClientConfiguration clientConfiguration = new ClientConfiguration();
            AmazonCognitoIdentityProvider cipClient = new AmazonCognitoIdentityProviderClient(new AnonymousAWSCredentials(), clientConfiguration);
            cipClient.setRegion(Region.getRegion(cognitoRegion));
            userPool = new CognitoUserPool(context, userPoolId, clientId, clientSecret, cipClient);
            */

        }

        emailVerified = false;
        emailAvailable = false;

//        currUserAttributes = new HashSet<String>();
//        currDisplayedItems = new ArrayList<ItemToDisplay>();
//        trustedDevices = new ArrayList<ItemToDisplay>();
//        firstTimeLogInDetails = new ArrayList<ItemToDisplay>();
//        firstTimeLogInUpDatedAttributes= new HashMap<String, String>();
    }

    public static CognitoUserPool getPool() {
        return userPool;
    }

    public static void setCurrSession(CognitoUserSession session) {
        currSession = session;
    }
    public static  CognitoUserSession getCurrSession() {
        return currSession;
    }

    public static void setUserDetails(CognitoUserDetails details) {
        userDetails = details;
//        refreshWithSync();
    }
    public static  CognitoUserDetails getUserDetails() {
        return userDetails;
    }

    public static String getCurrUser() {
        return user;
    }
    public static void setUser(String newUser) {
        user = newUser;
    }


    public static boolean isEmailVerified() {
        return emailVerified;
    }


    public static boolean isEmailAvailable() {
        return emailAvailable;
    }

    public static void setEmailVerified(boolean emailVerif) {
        emailVerified = emailVerif;
    }


    public static void setEmailAvailable(boolean emailAvail) {
        emailAvailable = emailAvail;
    }

//    public static void clearCurrUserAttributes() {
//        currUserAttributes.clear();
//    }

    private static void setData() {
        // Set attribute display sequence
        attributeDisplaySeq = new ArrayList<String>();
        attributeDisplaySeq.add("given_name");
        attributeDisplaySeq.add("middle_name");
        attributeDisplaySeq.add("family_name");
        attributeDisplaySeq.add("nickname");
        attributeDisplaySeq.add("phone_number");
        attributeDisplaySeq.add("email");

        signUpFieldsC2O = new HashMap<String, String>();
        signUpFieldsC2O.put("Given name", "given_name");
        signUpFieldsC2O.put("Family name", "family_name");
        signUpFieldsC2O.put("Nick name", "nickname");
        signUpFieldsC2O.put("Phone number", "phone_number");
        signUpFieldsC2O.put("Phone number verified", "phone_number_verified");
        signUpFieldsC2O.put("Email verified", "email_verified");
        signUpFieldsC2O.put("Email","email");
        signUpFieldsC2O.put("Middle name","middle_name");

        signUpFieldsO2C = new HashMap<String, String>();
        signUpFieldsO2C.put("given_name", "Given name");
        signUpFieldsO2C.put("family_name", "Family name");
        signUpFieldsO2C.put("nickname", "Nick name");
        signUpFieldsO2C.put("phone_number", "Phone number");
        signUpFieldsO2C.put("phone_number_verified", "Phone number verified");
        signUpFieldsO2C.put("email_verified", "Email verified");
        signUpFieldsO2C.put("email", "Email");
        signUpFieldsO2C.put("middle_name", "Middle name");

    }

}
