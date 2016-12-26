package com.fourtyonestudio.rcr.utils;

import android.util.Log;

import com.fourtyonestudio.rcr.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Response;

/**
 * Created by mohamadsodiq on 6/24/16.
 */
public class Retrofit2Utils {

    private static final String ERROR = "error";
    private static final String ERRORS = "errors";
    private static final String EMAIL = "email";

    public static String getMessageError(Response response) {
        String newMessage = Constant.MESSAGE.ERROR_GET;
        Log.d("response", newMessage);
        if (response != null) {
            if (response.code() == 522) {
                newMessage = Constant.MESSAGE._522;
            } else {
                try {
                    String ss = new String(response.errorBody().bytes());
                    JSONObject json = new JSONObject(ss);
                    if (json.has(ERROR)) {
                        newMessage = json.getString(ERROR);
                    } else if (json.has(ERRORS)) {
                        JSONObject obj = new JSONObject(json.getString(ERRORS));
                        if (obj.has(EMAIL)) {
                            JSONArray arr = obj.getJSONArray(EMAIL);
                            newMessage = arr.get(1).toString();
                        }
                    }
                } catch (IOException e) {
                } catch (JSONException e) {
                }
            }
        }
        return newMessage;
    }

    public static boolean isUnautorized(Response response) {
        return response.code() == 401;
    }

    public static String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
}
