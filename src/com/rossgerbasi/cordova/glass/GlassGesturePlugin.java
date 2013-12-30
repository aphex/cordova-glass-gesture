package com.rossgerbasi.cordova.glass;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONException;
import org.json.JSONObject;

public class GlassGesturePlugin extends CordovaPlugin implements GestureDetector.BaseListener, GestureDetector.FingerListener, GestureDetector.ScrollListener, GestureDetector.TwoFingerScrollListener, View.OnGenericMotionListener {
    private GestureDetector mGestureDetector;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
//        Log.d("GlassGesturePlugin", "Glass Gesture Initialize");
        super.initialize(cordova, webView);

        CordovaPlugin glassCore = this.webView.pluginManager.getPlugin("GlassCore");
        if(glassCore != null) {
            glassCore.onMessage("setMotionListener", this);
        } else {
            Log.d("GlassTouchPlugin", "Unable to find GlassCore Plugin to attach listener");
        }

        mGestureDetector = new GestureDetector(this.webView.getContext());
        mGestureDetector.setBaseListener(this);
        mGestureDetector.setFingerListener(this);
        mGestureDetector.setScrollListener(this);
        mGestureDetector.setTwoFingerScrollListener(this);
    }

    @Override
    public boolean onGenericMotion(View v, MotionEvent event) {
        mGestureDetector.onMotionEvent(event);
        return true;
    }

    @Override
    public boolean onGesture(Gesture gesture) {
        String type = gesture.toString().toLowerCase().replace("_","");
        this.fireEvent(type, null);
//        Log.d("GlassGesturePlugin", type);
        return false;
    }

    @Override
    public void onFingerCountChanged(int i, int i2) {
        JSONObject data = new JSONObject();
        try {
            data.put("from", i);
            data.put("to", i2);
        } catch (JSONException e) {
            Log.d("GlassGesturePlugin", "Exception setting FingerCountChanged data");
        }

        this.fireEvent("fingercountchanged", data);
    }

    @Override
    public boolean onScroll(float v, float v2, float v3) {
        JSONObject data = new JSONObject();
        try {
            data.put("displacement", v);
            data.put("delta", v2);
            data.put("velocity", v3);
        } catch (JSONException e) {
            Log.d("GlassGesturePlugin", "Exception setting inScroll data");
        }

        this.fireEvent("scroll", data);
        return false;
    }

    @Override
    public boolean onTwoFingerScroll(float v, float v2, float v3) {
        JSONObject data = new JSONObject();
        try {
            data.put("displacement", v);
            data.put("delta", v2);
            data.put("velocity", v3);
        } catch (JSONException e) {
            Log.d("GlassGesturePlugin", "Exception setting onTwoFingerScroll data");
        }

        this.fireEvent("twofingerscroll", data);
        return false;
    }

    private void fireEvent(String type, JSONObject data) {
        String js = "javascript:try{cordova.fireDocumentEvent('"+type+"'" + (data != null  ? "," + data : "") +" );}catch(e){console.log('exception firing gesture event from native');};";
        webView.loadUrl(js);
        //Log.d("GlassGesturePlugin", "Sent: " + type + " with: " + data);
    }
}
