package in.nighthawk.wificonqrer.Network;

import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import in.nighthawk.wificonqrer.Models.Scan;

/**
 * Created by adi on 4/5/16.
 */
public class HttpManager {

    public static void sendScan(Scan scan) {
        String json = new GsonBuilder().create().toJson(scan, Scan.class);
        // Send scan data to cloud server
        try {
            makeRequest("http://192.168.0.1:80", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void makeRequest(String url, String json) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // Add request header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "Android");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(json);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post json : " + json);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());
    }
}
