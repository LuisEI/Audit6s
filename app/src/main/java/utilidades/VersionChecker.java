package utilidades;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class VersionChecker {

    public static final String INFO_FILE = "https://sistemas.avxslv.com/lean/Apk/version.txt";

    private int currentVersionCode;

    private String currentVersionName;

    private int latestVersionCode;

    private String latestVersionName;

    private String downloadURL;

    public void getData(Context context) {
        try{
            PackageInfo pckginfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            currentVersionCode = pckginfo.versionCode;
            currentVersionName = pckginfo.versionName;

            String data = downloadHttp(new URL(INFO_FILE));
            JSONObject json = new JSONObject(data);
            latestVersionCode = json.getInt("versionCode");
            latestVersionName = json.getString("versionName");
            downloadURL = json.getString("downloadURL");
            Log.d("AutoUpdate", "Datos obtenidos con exito");
        }catch(JSONException e){
            Log.e("AutoUpdate", "Ha habido un error con el JSON", e);
        }catch(PackageManager.NameNotFoundException e){
            Log.e("AutoUpdate", "Ha habido un error con el packete :S", e);
        }catch(IOException e){
            Log.e("AutoUpdate", "Ha habido un error con la descarga", e);
        }
    }

    public boolean isNewVersionAvailable() {
        return getLatestVersionCode() > getCurrentVersionCode();
    }

    public int getCurrentVersionCode() {
        return currentVersionCode;
    }

    public String getCurrentVersionName() {
        return currentVersionName;
    }

    public int getLatestVersionCode() {
        return latestVersionCode;
    }

    public String getLatestVersionName() {
        return latestVersionName;
    }

    public String getDownloadURL() {
        return downloadURL;
    }

    private static String downloadHttp(URL url) throws IOException {
        HttpURLConnection c = (HttpURLConnection)url.openConnection();
        c.setRequestMethod("GET");
        c.setReadTimeout(15 * 1000);
        c.setUseCaches(false);
        c.connect();
        BufferedReader reader = new BufferedReader(new InputStreamReader(c.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        while((line = reader.readLine()) != null){
            stringBuilder.append(line + "\n");
        }
        return stringBuilder.toString();
    }
}
