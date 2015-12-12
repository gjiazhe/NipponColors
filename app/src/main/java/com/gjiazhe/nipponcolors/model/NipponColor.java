package com.gjiazhe.nipponcolors.model;

import android.content.Context;
import android.content.res.AssetManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gjz on 11/29/15.
 */
public class NipponColor {
    private String id;
    private String nameJA;
    private String nameEN;
    private String rgb;
    private String cmyk;

    public NipponColor(String id, String nameJA, String nameEN, String rgb, String cmyk) {
        this.id = id;
        this.nameJA = nameJA;
        this.nameEN = nameEN;
        this.rgb = rgb;
        this.cmyk = cmyk;
    }

    public String getId() {
        return id;
    }

    public String getNameJA() {
        return nameJA;
    }

    public String getNameEN() {
        return nameEN;
    }

    public String getRgb() {
        return rgb;
    }

    public String getCmyk() {
        return cmyk;
    }

    public int getCmyk_c() {
        String c = cmyk.substring(0, 3);
        return Integer.parseInt(c);
    }

    public int getCmyk_m() {
        String m = cmyk.substring(3, 6);
        return Integer.parseInt(m);
    }

    public int getCmyk_y() {
        String y = cmyk.substring(6, 9);
        return Integer.parseInt(y);
    }

    public int getCmyk_k() {
        String k = cmyk.substring(9, 12);
        return Integer.parseInt(k);
    }

    private static List<NipponColor> colorList = null;

    public static List<NipponColor> getColorList(Context context) {
        if (colorList == null) {
            initColorData(context);
        }
        return colorList;
    }

    private static String getJsonString(Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader br = new BufferedReader(new InputStreamReader(assetManager.open("ColorsData.json")));
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    public static void initColorData(Context context) {
        colorList = new ArrayList<>();
        String jsonStr = getJsonString(context);
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            int colorCount = jsonArray.length();
            for (int i = 0; i<colorCount; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String id = jsonObject.getString("id");
                String nameJA = jsonObject.getString("nameJA");
                String nameEN = jsonObject.getString("nameEN");
                String rgb = jsonObject.getString("rgb");
                String cmyk = jsonObject.getString("cmyk");
                NipponColor nipponColor = new NipponColor(id, nameJA, nameEN, rgb, cmyk);

                colorList.add(i, nipponColor);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
