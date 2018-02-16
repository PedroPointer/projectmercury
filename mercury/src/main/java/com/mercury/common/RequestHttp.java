package com.mercury.common;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class RequestHttp {

	public String callURL (String sURL) throws IOException  {
	    String data = null;
	    URL url = new URL(sURL);
	    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
	    try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            data = readStream(in);
        } catch (RuntimeException  e) {
//            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return data;
	}
	
    public  String readStream(InputStream in) throws IOException {
        String data = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringBuffer sb = new StringBuffer();
        String line = "";
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        data = sb.toString();
        return data;
    }
}
