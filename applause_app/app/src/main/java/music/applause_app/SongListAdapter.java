package music.applause_app;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by David on 9/25/2015.
 */
public class SongListAdapter extends BaseAdapter {

    Activity context;
    String[] titles;
    float[] durations;

    public SongListAdapter(Activity context, String[] titles, float[] durations) {
        super();
        this.context = context;
        this.titles = titles;
        this.durations = durations;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.songitem_row, null);
        }
        ((TextView) convertView.findViewById(R.id.textSongTitle))
                .setText(titles[position]);
        ((TextView) convertView.findViewById(R.id.textSongDuration))
                .setText(Float.toString(durations[position]));

        return convertView;
    }
}
