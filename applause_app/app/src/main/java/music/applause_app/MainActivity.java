package music.applause_app;

import android.content.IntentSender;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 0;

    private GoogleApiClient apiClient;
    private MediaPlayer mp = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(new Scope(Scopes.PROFILE))
                .build();

        final ListView lv = (ListView) findViewById(R.id.songsList);
        String[] titles = getSongTitles();
        float[] durations = new float[titles.length];
        SongListAdapter slAdapter = new SongListAdapter(this, titles, durations);
        lv.setAdapter(slAdapter);

        lv.setClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int position, long arg3) {
                try {
                    AssetFileDescriptor afd = getAssets().openFd(
                            "sample_music" + File.separator + getSongTitles()[position]);
                    if (mp.isPlaying()) {
                        mp.stop();
                        mp.release();
                        mp = new MediaPlayer();

                    }
                    mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                    mp.prepare();
                    mp.start();
                } catch (IOException e) {

                }
                //Object o = lv.getItemAtPosition(position);
                //System.out.println("Item clicked...");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String[] getSongTitles() {
        List<String> titles = new ArrayList<String>();
        try {
            for (String file : getAssets().list("sample_music")) {
                titles.add(file);
            }
        } catch(Exception e) {
            System.out.println(e);
        }

        String[] titlesArray = new String[titles.size()];
        for (int i = 0; i < titles.size(); i++) {
            titlesArray[i] = titles.get(i);
        }
        return titlesArray;
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d("MainActivity", "Connection Successful!!!");
        Person p = Plus.PeopleApi.getCurrentPerson(apiClient);
        if (p != null) {
            Log.d("MainActivity", "Id: " + p.getId());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this, RC_SIGN_IN);
                apiClient.connect();
            } catch (IntentSender.SendIntentException e) {
                Log.e("MainActivity", e.toString());
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        apiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        apiClient.disconnect();
    }
}
