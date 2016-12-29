package com.samanlan.permissionshelper;

/**
 * 作者(Author)：蓝深铭(LanSaman)
 * 邮箱(E-Mail)：lansaman@163.com
 * 时间(Time)：on 2016/12/30 00:58
 */

public interface PermissionsListener {

    void onDenied(String[] deniedPermissions);

    void onGranted();
}
