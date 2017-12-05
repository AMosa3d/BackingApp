package com.example.abdel.backingapp.Utilites;

import android.net.Uri;

import com.example.abdel.backingapp.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by abdel on 11/18/2017.
 */

public final class NetworkUtils {

    public static URL buildURL()
    {
        Uri uri = Uri.parse(BuildConfig.RECIPE_URL).buildUpon().build();

        URL url = null;

        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getDataFromURL(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        try
        {
            InputStream stream = connection.getInputStream();

            Scanner scanner = new Scanner(stream);
            scanner.useDelimiter("\\A");

            if (scanner.hasNext())
                return scanner.next();
            return null;
        }
        finally {
            connection.disconnect();
        }
    }
}
