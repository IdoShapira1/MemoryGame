package com.example.idoshapira_mbp.memorygame;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

        mDatabaseHelper = new DatabaseHelper(getContext());

        Cursor data = mDatabaseHelper.getAllData();
        ArrayList<String> listData = new ArrayList<>();
        ArrayList<Integer> listDiff = new ArrayList<>();
        ArrayList<Double> listScore = new ArrayList<>();
        while(data.moveToNext()){
            listData.add(data.getString(1));
            listDiff.add(data.getInt(2));
            listScore.add(data.getDouble(3));

        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_dropdown_item_1line,listData);
        lv.setAdapter(adapter);




        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
      //  ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,countries);

    }

}
