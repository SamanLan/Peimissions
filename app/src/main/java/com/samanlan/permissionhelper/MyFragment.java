package com.samanlan.permissionhelper;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.samanlan.lib_permisshelper.PermissionsListener;
import com.samanlan.lib_permisshelper.PermissionsUtils;

/**
 * Created by Administrator on 2017/2/19.
 */
public class MyFragment extends Fragment {

    PermissionsUtils mPermissionsUtils = new PermissionsUtils();
    FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout_app, null, false);
        Button button = (Button) view.findViewById(R.id.btn_fragment_app);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPermissionsUtils
                        .withActivity(getActivity())
                        .setPermissionsListener(new PermissionsListener() {
                            @Override
                            public void onDenied(String[] deniedPermissions) {
                                Toast.makeText(getActivity(), "不同意", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onGranted() {
                                Toast.makeText(getActivity(), "同意了", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .getPermissions(MyFragment.this,100, Manifest.permission.CALL_PHONE);
            }
        });
        fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction().add(R.id.fragment_content2, new MyFragmentInFragment()).commit();
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissionsUtils.dealResult(requestCode, permissions, grantResults);
    }
}
