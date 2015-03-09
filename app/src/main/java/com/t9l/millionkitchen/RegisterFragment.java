package com.t9l.millionkitchen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.t9l.millionkitchen.tools.Methods;

/**
 * Created by praneet on 24-02-2015.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {
    EditText firstNameET, lastNameET, emailET, phoneNumberET, passwordET;

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
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        view.findViewById(R.id.nextBtn).setOnClickListener(this);
        view.findViewById(R.id.backBtn).setOnClickListener(this);
        firstNameET = (EditText) view.findViewById(R.id.firstNameET);
        lastNameET = (EditText) view.findViewById(R.id.lastNameET);
        emailET = (EditText) view.findViewById(R.id.emailET);
        phoneNumberET = (EditText) view.findViewById(R.id.phoneNumberET);
        passwordET = (EditText) view.findViewById(R.id.passwordET);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity) getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nextBtn:
                if (firstNameET.getText().toString().isEmpty()) {
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(firstNameET);
                } else if (lastNameET.getText().toString().isEmpty()) {
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(lastNameET);
                } else if (emailET.getText().toString().isEmpty()) {
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(emailET);
                } else if (!Methods.isValidEmail(emailET.getText().toString())) {
                    Toast.makeText(getActivity(), "Invalid email address", Toast.LENGTH_SHORT).show();
                } else if (phoneNumberET.getText().toString().isEmpty()) {
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(phoneNumberET);
                } else if (passwordET.getText().toString().isEmpty()) {
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(passwordET);
                } else {
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, new AddressFragment(),
                                    "Address").addToBackStack("Address")
                            .commit();
                }
                break;
            case R.id.backBtn:
                getFragmentManager()
                        .popBackStack();
                break;
        }
    }
}
