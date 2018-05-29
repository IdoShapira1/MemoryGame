package com.example.idoshapira_mbp.memorygame;



import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by idoshapira-mbp on 25/05/2018.
 */


public class PlayerEntryAdapter extends ArrayAdapter<PlayerEntry> {

    private static final String TAG = "PlayerEntryListAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView name;
        TextView birthday;
        TextView sex;
    }

    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public PlayerEntryAdapter(Context context, int resource, ArrayList<PlayerEntry> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the persons information
        String name = getItem(position).getplayerName();
        Double score = getItem(position).getScore();
        String diff = getItem(position).getDiff();

        //Create the person object with the information
        PlayerEntry person = new PlayerEntry(name,score,diff);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView tvName = (TextView) convertView.findViewById(R.id.listPlayeName);
        TextView tvScore = (TextView) convertView.findViewById(R.id.listPlayerScore);
        TextView tvDiff = (TextView) convertView.findViewById(R.id.listPlayerLocation);

        tvName.setText(name);
        tvScore.setText(score.toString());
        tvDiff.setText(diff);

        return convertView;
    }
}


