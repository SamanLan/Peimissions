package com.samanlan.permissionhelper;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.samanlan.lib_permisshelper.PermissionsListener;
import com.samanlan.lib_permisshelper.PermissionsUtils;

/**
 * Created by Administrator on 2017/2/19.
 */
public class MainActivity extends AppCompatActivity {

    PermissionsUtils mPermissionsUtils=new PermissionsUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_content,new  MyFragment(), "MyFragment");
        fragmentTransaction.commit();
    }

    /**
     * 一次申请一个权限（使用拒绝弹框）点击事件
     */
    public void getPermissionWithTip(View view) {
        if (mPermissionsUtils == null)
            mPermissionsUtils = new PermissionsUtils();
        mPermissionsUtils
                .setPermissionsListener(new PermissionsListener() {
                    @Override
                    public void onDenied(String[] deniedPermissions) {
                        for (int i = 0; i < deniedPermissions.length; i++) {
                            Toast.makeText(MainActivity.this, deniedPermissions[i] + " 权限被拒绝", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onGranted() {
                        Toast.makeText(MainActivity.this, "所有权限都被同意", Toast.LENGTH_SHORT).show();
                    }
                })
                .withActivity(this)
                .getPermissionsWithTips(MainActivity.this,100,new String[]{"人家想开开相机玩玩嘛"}, Manifest.permission.CAMERA);
    }

    /**
     * 一次申请一组权限（使用拒绝弹框）点击事件
     */
    public void getPermissionsWithTip(View view) {
        if (mPermissionsUtils == null)
            mPermissionsUtils = new PermissionsUtils();
        mPermissionsUtils
                .setPermissionsListener(new PermissionsListener() {
                    @Override
                    public void onDenied(String[] deniedPermissions) {
                        for (int i = 0; i < deniedPermissions.length; i++) {
                            Toast.makeText(MainActivity.this, deniedPermissions[i] + " 权限被拒绝", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onGranted() {
                        Toast.makeText(MainActivity.this, "所有权限都被同意", Toast.LENGTH_SHORT).show();
                    }
                })
                .withActivity(this)
                .getPermissionsWithTips(MainActivity.this
                        ,100
                        , new String[]{"人家要录音","我们需要读取你的信息，磨磨唧唧的","我要上网","我要读内存卡","我要看日历","我要定位"}
                        , Manifest.permission.RECORD_AUDIO
                        , Manifest.permission.READ_SMS
                        , Manifest.permission.INTERNET
                        , Manifest.permission.READ_EXTERNAL_STORAGE
                        , Manifest.permission.READ_CALENDAR
                        , Manifest.permission.ACCESS_FINE_LOCATION);
    }

    /**
     * 一次申请一个权限点击事件
     */
    public void getPermission(View view) {
        if (mPermissionsUtils == null)
            mPermissionsUtils = new PermissionsUtils();
        mPermissionsUtils
                .setPermissionsListener(new PermissionsListener() {
                    @Override
                    public void onDenied(String[] deniedPermissions) {
                        for (int i = 0; i < deniedPermissions.length; i++) {
                            Toast.makeText(MainActivity.this, deniedPermissions[i] + " 权限被拒绝", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onGranted() {
                        Toast.makeText(MainActivity.this, "所有权限都被同意", Toast.LENGTH_SHORT).show();
                    }
                })
                .withActivity(this)
                .getPermissions(MainActivity.this,100, Manifest.permission.READ_CONTACTS);
    }

    /**
     * 一次申请一组权限点击事件
     */
    public void getPermissions(View view) {
        if (mPermissionsUtils == null)
            mPermissionsUtils = new PermissionsUtils();
        mPermissionsUtils
                .setPermissionsListener(new PermissionsListener() {
                    @Override
                    public void onDenied(String[] deniedPermissions) {
                        for (int i = 0; i < deniedPermissions.length; i++) {
                            Toast.makeText(MainActivity.this, deniedPermissions[i] + " 权限被拒绝", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onGranted() {
                        Toast.makeText(MainActivity.this, "所有权限都被同意", Toast.LENGTH_SHORT).show();
                    }
                })
                .withActivity(this)
                .getPermissions(MainActivity.this
                        , 100
                        , Manifest.permission.INTERNET
                        , Manifest.permission.READ_EXTERNAL_STORAGE
                        , Manifest.permission.READ_CALENDAR
                        , Manifest.permission.ACCESS_FINE_LOCATION);
    }

    /**
     * 打电话点击事件
     */
    public void callPhone(View view) {
        if (mPermissionsUtils == null) {
            mPermissionsUtils = new PermissionsUtils();
        }
        mPermissionsUtils.withActivity(MainActivity.this)
                .setPermissionsListener(new PermissionsListener() {
                    @Override
                    public void onDenied(String[] deniedPermissions) {
                    }

                    @Override
                    public void onGranted() {
                        Intent phoneIntent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + "15814871500"));
                        startActivity(phoneIntent);
                    }
                })
                .getPermissionsWithTips(MainActivity.this
                        , 200
                        , new String[]{""}
                        , Manifest.permission.CALL_PHONE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissionsUtils.dealResult(requestCode, permissions, grantResults);
    }
}
