package com.t9l.millionkitchen;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.t9l.millionkitchen.dao.CustomImage;
import com.t9l.millionkitchen.tools.ImageUtil;
import com.t9l.millionkitchen.tools.Methods;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by praneet on 24-02-2015.
 */
public class AboutMeEditFragment extends Fragment implements View.OnClickListener {
    ImageUtil util;
    Uri outputFileUri;
    final int YOUR_SELECT_PICTURE_REQUEST_CODE = 1001;
    ImageView dishImageView;
    Boolean isIntentOpened=false;
    ImageButton addDishButton,editButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        util = new ImageUtil(getActivity());
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        Methods.setMenuItems(menu, false, false, false);
        return;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_me_edit, container, false);
        view.findViewById(R.id.backBtn).setOnClickListener(this);
        dishImageView = (ImageView) view.findViewById(R.id.dishImage);
        addDishButton = (ImageButton) view.findViewById(R.id.addDishButton);
        editButton = (ImageButton) view.findViewById(R.id.editButton);
        addDishButton.setOnClickListener(this);
        editButton.setOnClickListener(this);
        view.findViewById(R.id.save_btn).setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        isIntentOpened=false;
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == YOUR_SELECT_PICTURE_REQUEST_CODE) {
                final boolean isCamera;
                if (data == null) {
                    isCamera = true;
                } else {
                    final String action = data.getAction();
                    if (action == null) {
                        isCamera = false;
                    } else {
                        isCamera = action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
                    }
                }

                Uri selectedImageUri;
                if (isCamera) {
                    selectedImageUri = outputFileUri;
                    setImage(selectedImageUri);
                } else {
                    selectedImageUri = data == null ? null : data.getData();
                    setImage(selectedImageUri);
                }
            }
        }
    }

    private void setImage(Uri selectedImageUri) {
        CustomImage customImage = new CustomImage(selectedImageUri.toString(), true);
        assignImageToImageView(dishImageView, customImage);
        loadImageFromTag(dishImageView);
    }

    private void assignImageToImageView(ImageView v, CustomImage image) {
//        if (!isImageViewAssigned(v))
            v.setTag(image);
    }

    private boolean isImageViewAssigned(ImageView v) {
        CustomImage customImage = (CustomImage) v.getTag();
        if (customImage == null)
            return false;
        else
            return true;
    }

    private void loadImageFromTag(ImageView v) {
        CustomImage customImage = (CustomImage) v.getTag();
        if (customImage != null) {
            util.loadImage(dishImageView, customImage);
            editButton.setVisibility(View.VISIBLE);
            addDishButton.setVisibility(View.GONE);
        }
    }

    private void openImageIntent() {
        isIntentOpened=true;
// Determine Uri of camera image to save.
        final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "MyDir" + File.separator);
        boolean made = root.mkdirs();
        Log.e("Make Directory", String.valueOf(made));
        final String fname = util.getUniqueImageFilename();
        final File sdImageMainDirectory = new File(root, fname);
        outputFileUri = Uri.fromFile(sdImageMainDirectory);

        // Camera.
        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getActivity().getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            cameraIntents.add(intent);
        }

        // Filesystem.
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

        // Chooser of filesystem options.
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

        // Add the camera options.
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));

        startActivityForResult(chooserIntent, YOUR_SELECT_PICTURE_REQUEST_CODE);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(!isIntentOpened)
        ((MainActivity) getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backBtn:
                getFragmentManager()
                        .popBackStack();
                break;
            case R.id.addDishButton:
                openImageIntent();
                break;
            case R.id.editButton:
                openImageIntent();
                break;
            case R.id.save_btn:
                Toast.makeText(getActivity(),"Saved",Toast.LENGTH_SHORT).show();
                getFragmentManager()
                        .popBackStack();
                break;
        }
    }
}
