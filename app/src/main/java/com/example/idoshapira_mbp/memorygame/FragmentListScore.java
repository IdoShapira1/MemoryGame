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

/**
 * Created by idoshapira-mbp on 25/05/2018.
 */

public class FragmentListScore extends Fragment {

    String countries[] = {"Austria","Belgium","Israel"};


    public FragmentListScore(){
        //required empty constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){




        View view = inflater.inflate(R.layout.score_list_fragment,container,false);

        ListView lv = (ListView)view.findViewById(R.id.listScores);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,countries);
        lv.setAdapter(adapter);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
      //  ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,countries);

    }
}
