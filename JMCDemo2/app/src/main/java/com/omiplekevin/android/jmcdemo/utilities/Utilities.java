package com.omiplekevin.android.jmcdemo.utilities;

import com.omiplekevin.android.jmcdemo.helpers.Constants;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by OMIPLEKEVIN on Jun 13, 2016.
 */

public class Utilities {

    /**
     * Request Configuration from <i>configuration source</i>
     *
     * @param sourceUrl source url where we can get our configuration
     * @param method    POST/GET
     * @param map       Parameters for the POST method
     * @return <b>responseString</b> result of request
     */
    public static String sendHttpRequest(String sourceUrl, String method, TreeMap<String, String> map) {
        StringBuilder responseString = new StringBuilder();
        try {
            //throws MalformedURLException
            URL urlSource = new URL(sourceUrl);
            //throws IOException
            HttpURLConnection httpUrlConnection = (HttpURLConnection) urlSource.openConnection();
            httpUrlConnection.setRequestMethod(method.toUpperCase(Locale.getDefault()));
            httpUrlConnection.setConnectTimeout(Constants._REQUEST_TIMEOUT);

            if (method.equals("POST")) {
                StringBuilder params = new StringBuilder();
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    params.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                }
                httpUrlConnection.setDoOutput(true);
                httpUrlConnection.setInstanceFollowRedirects(false);
                httpUrlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                httpUrlConnection.setRequestProperty("charset", "utf-8");
                OutputStreamWriter writer = new OutputStreamWriter(httpUrlConnection.getOutputStream());
                writer.write(params.substring(0, params.length() - 1));
                writer.flush();
            }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                responseString.append(line);
            }

            httpUrlConnection.getInputStream().close();
        } catch (Exception e) {
            e.printStackTrace();
            responseString = new StringBuilder("");
            return responseString.toString();
        }
        return responseString.toString();
    }
}
