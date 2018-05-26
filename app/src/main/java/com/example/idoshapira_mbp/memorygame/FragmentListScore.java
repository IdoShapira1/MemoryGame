package com.example.idoshapira_mbp.memorygame;

import android.app.Fragment;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by idoshapira-mbp on 25/05/2018.
 */

public class FragmentListScore extends Fragment {

    DatabaseHelper mDatabaseHelper;


    String countries[] = {"Austria","Belgium","Israel"};



    public FragmentListScore(){
        //required empty constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.score_list_fragment,container,false);

        ListView lv = (ListView)view.findViewById(R.id.listScores);
        TextView textView = new TextView(getContext());
        textView.setText(R.string.top_ten);

        lv.addHeaderView(textView);
        lv.setBackgroundColor(Color.parseColor("#ffffff"));

        mDatabaseHelper = new DatabaseHelper(getContext());

        Cursor data = mDatabaseHelper.getAllData();
        ArrayList<PlayerEntry> listDataPlayers = new ArrayList<>();
        while(data.moveToNext()){
            listDataPlayers.add(new PlayerEntry(data.getString(1),data.getDouble(2),data.getInt(3)));

        }

        PlayerEntryAdapter adapter = new PlayerEntryAdapter(getContext(),R.layout.adapter_view_layout,listDataPlayers);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line,listData);
         lv.setAdapter(adapter);



        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
      //  ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,countries);

    }

}
