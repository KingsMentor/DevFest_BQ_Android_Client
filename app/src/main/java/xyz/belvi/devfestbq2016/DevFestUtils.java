package xyz.belvi.devfestbq2016;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.bigquery.Bigquery;
import com.google.api.services.bigquery.BigqueryScopes;
import com.google.api.services.bigquery.model.Dataset;
import com.google.api.services.bigquery.model.DatasetReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Collection;

/**
 * Created by zone2 on 11/21/16.
 */

public class DevFestUtils {

    String PROJECT_ID = "bq-devfest-demo-2016";
    public void createDataSet(Context context){
        try {
            Bigquery service = createAuthorizedClient(context);
            Dataset devAndroidClient = new Dataset();
            DatasetReference reference = new DatasetReference();
            reference.setDatasetId("DEVFEST_ANDROID_CLIENT");
            reference.setProjectId(PROJECT_ID);
            devAndroidClient.setDatasetReference(reference);
            service.datasets().insert(PROJECT_ID,devAndroidClient).execute();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

    }

    private Bigquery createAuthorizedClient(Context mContext) throws IOException, GeneralSecurityException {
        // Create the credential
        HttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();
        AssetManager am = mContext.getAssets();
        InputStream inputStream = am.open("devfest.p12");
        File file = createFileFromInputStream(mContext,inputStream);
        GoogleCredential credential = new GoogleCredential.Builder().setTransport(transport)
                .setJsonFactory(jsonFactory)
                .setServiceAccountId("bq-devfest-demo-2016@appspot.gserviceaccount.com")
                .setServiceAccountScopes(BigqueryScopes.all())
                .setServiceAccountPrivateKeyFromP12File(file)
                .build();

        // Depending on the environment that provides the default credentials (e.g. Compute Engine, App
        // Engine), the credentials may require us to specify the scopes we need explicitly.
        // Check for this case, and inject the Bigquery scope if required.
        if (credential.createScopedRequired()) {
            Collection<String> bigqueryScopes = BigqueryScopes.all();
            credential = credential.createScoped(bigqueryScopes);
        }
        file.delete();
        inputStream.close();

        return new Bigquery.Builder(transport, jsonFactory, credential)
                .setApplicationName("BigQuery Samples").build();
    }

    private File createFileFromInputStream(Context mContext,InputStream inputStream) {

        try {

            FileOutputStream fileOuputStream = mContext.openFileOutput("key.p12", Context.MODE_PRIVATE);
            byte buffer[] = new byte[1024];
            int length = 0;

            while ((length = inputStream.read(buffer)) > 0) {
                fileOuputStream.write(buffer, 0, length);
            }
            fileOuputStream.close();
            inputStream.close();

            return new File(mContext.getFilesDir().getAbsolutePath() + "/key.p12");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
