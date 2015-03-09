package com.t9l.millionkitchen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.t9l.millionkitchen.tools.Methods;

/**
 * Created by praneet on 27-02-2015.
 */
public class PaymentInfoFragment extends Fragment implements View.OnClickListener{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        Methods.setMenuItems(menu, false, false, false);
        return;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment_info, container, false);
        view.findViewById(R.id.saveBtn).setOnClickListener(this);
        view.findViewById(R.id.backBtn).setOnClickListener(this);
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity)getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.saveBtn:
                break;
            case R.id.backBtn:
                getFragmentManager()
                        .popBackStack();
                break;
        }
    }
}
