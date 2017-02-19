package com.samanlan.permissionhelper;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
public class MyFragmentInFragment extends Fragment {

    PermissionsUtils mPermissionsUtils=new PermissionsUtils();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout_app2, null, false);
        Button button = (Button) view.findViewById(R.id.btn_fragment_app);
        button.setText("在fragment中嵌套fragment申请权限");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPermissionsUtils.withActivity(getActivity())
                        .setPermissionsListener(new PermissionsListener() {
                            @Override
                            public void onDenied(String[] deniedPermissions) {
                                Toast.makeText(getActivity(), "嵌套的不同意", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onGranted() {
                                Toast.makeText(getActivity(), "嵌套的同意了", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .getPermissions(MyFragmentInFragment.this, 100, Manifest.permission.CALL_PHONE);
            }
        });
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissionsUtils.dealResult(requestCode, permissions, grantResults);
    }
}
