众所周知，在``Android6.0``以上增加了动态申请权限这玩意（``targetSdkVersion >= 23``）。
那么问题来了，what？when？how？
### What：
在targetSdkVersion <23，当用户安装App时，App会索要了你手机的那些权限，并且给用一个列表进行展示，但是这些提示只是在安装是提示，只要你点击接受或者安装， 表示你允许这个应用在可以获取它申明的所有权限。一般很少有人在安装时，会因为看到某个应用因为申请了某一个敏感权限而放弃安装应用。（实力帮应用宝打广告）

![旧权限显示.jpg](http://upload-images.jianshu.io/upload_images/1787089-1b3637bfb8ad568d.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

那么当用户使用过程中，正准备使用某个权限时，系统能及时的弹出一个提示框是否允许。然后用户结合当前应用判断，是否应该授予应用权限。nice。 这样对用户而言，完全是主动的，相比安装时那种选择，这样的做法无疑是对用户莫大的尊重，同时这也保证了用户的个人隐私。

![权限弹出申请.png](http://upload-images.jianshu.io/upload_images/1787089-e1546ccacb32711b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

故谷歌爸爸自然而然就产出了这个动态申请权限，虽然对于程序员来说多了一部分代码，但正如一句话所说``尊重用户才会赢得用户``。
### When：
不用想的好吗？你要用到权限的时候申请
### How：
用到的API也不多就几个

---

#####上面说到对于用户是友好的，对于程序员是多增加了代码的，当然程序员都是懒惰的，那么一个良好的对申请封装是显得多么的重要与节省功夫、时间。
1. 上GitHub搜一搜，大多数的封装都是直接在BaseActivity进行操作，不是说不可以，当然会存在一点耦合度。
2. 还看到一个RxPermission，思想就是当下火火的Rx。也是相当不错。但问题又来了，假如项目中没有用到Rx呢？又要加入RxJava和RxAndroid两个库，显得有点不值得。
3. 为了将代码从activity中抽离，同时为了在fragment（或者fragment中嵌套fragment）中申请权限不用进行繁琐的操作，故本次封装并没有选择在BaseActivity中进行封装。

[博客链接](http://www.jianshu.com/p/d6e7aa7fe69c)

### 简单使用，一个链式让你调下来(注：内部已进行版本判断，无需再进行判断)
```
PermissionsUtils mPermissionsUtils=new PermissionsUtils();

public void 申请(){
    mPermissionsUtils
                // 设置回调
                .setPermissionsListener(new PermissionsListener() {
                    // 申请的权限中有被拒绝的回调该方法，可以在此进行你的逻辑
                    // 由于某些rom对权限进行了处理，第一次选择了拒绝，则不会出现第二次询问（或者没有不再询问勾选），故拒绝就回调onDenied
                    @Override
                    public void onDenied(String[] deniedPermissions) {
                        // 在deniedPermissions里面包含了所有被拒绝的权限名字
                        for (int i = 0; i < deniedPermissions.length; i++) {
                            System.out(deniedPermissions[i] + " 权限被拒绝");
                        }
                    }

                    // 所有申请的权限同意了回调该方法，可以在此进行你的逻辑
                    @Override
                    public void onGranted() {
                        Toast.makeText(MainActivity.this, "所有权限都被同意", Toast.LENGTH_SHORT).show();
                    }
                })
                // 设置Activity，因为检查权限必须要有activity对象
                .withActivity(this)
                // 最后调用申请权限的方法
                // 三个参数分别是Object，int，String[]
                // 第一个参数对应的是activity或者fragment，如果是在activity中申请就传入activity对象，在fragment申请就传入该fragment对象，这是用于对返回事件的分发进行处理。
                // 第二个对应的是该次申请权限的requestCode
                // 第三个就是权限列表，不定长度
                .getPermissions(MainActivity.this,100, Manifest.permission.READ_CONTACTS);
                // 例如申请多个权限
                /*.getPermissions(MainActivity.this
                        , 100
                        , Manifest.permission.INTERNET
                        , Manifest.permission.READ_EXTERNAL_STORAGE
                        , Manifest.permission.READ_CALENDAR
                        , Manifest.permission.ACCESS_FINE_LOCATION);*/
}

// 在activity或者fragment中
@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 在onRequestPermissionsResult这个方法调用dealResult就可以了
        mPermissionsUtils.dealResult(requestCode, permissions, grantResults);
    }
```

|主要方法|作用|
|--|--|
|getPermissions|进行权限申请，不带拒绝弹框提示|
|getPermissionsWithTips|进行权限申请，带拒绝弹框提示|
|dealResult|对申请权限返回结果进行处理|
|setPermissionsListener|设置回调|
|getAppDetailSettingIntent|跳转到本应用的设置界面|

上面的``getPermissionsWithTips``是什么鬼？其实是对被拒绝权限后一个弹窗，免得大家重复写，可用可不用
怎么用？很简单，传入提示数组即可
```
mPermissionsUtils.getPermissionsWithTips(MainActivity.this
                        ,100
                        , new String[]{"人家要录音"
                                ,"我们需要读取你的信息，磨磨唧唧的"
                                ,"我要上网"
                                ,"我要读内存卡"
                                ,"我要看日历"
                                ,"我要定位"}
                        , Manifest.permission.RECORD_AUDIO
                        , Manifest.permission.READ_SMS
                        , Manifest.permission.INTERNET
                        , Manifest.permission.READ_EXTERNAL_STORAGE
                        , Manifest.permission.READ_CALENDAR
                        , Manifest.permission.ACCESS_FINE_LOCATION);
```
用户拒绝权限后效果图如下

![](http://i1.piimg.com/567571/cf74461b3d3a2b5b.png)

### 我有问题要问
● 在6.0所有权限都需要申请？

曰：当然不是。只有属于危险权限的才需要申请。危险权限看下``表1-2``

● 那危险权限也很多啊，也要一个个申请？

曰：当然不是。你看看下面的表，都分好组了（9组），对于同一组内的权限，只要有一个被同意，其他的都会被同意。




``表1-2``**危险权限分组**

|分组|名字|分割线|分组|名字|
|---|---|---|---|---|
|CALENDAR|READ_CALENDAR||PHONE|READ_PHONE_STATE|
||WRITE_CALENDAR|||CALL_PHONE|
|CAMERA|CAMERA|||READ_CALL_LOG|
|CONTACTS|READ_CONTACTS|||WRITE_CALL_LOG|
||WRITE_CONTACTS|||ADD_VOICEMAIL|
||GET_ACCOUNTS|||USE_SIP|
|LOCATION|ACCESS_FINE_LOCATION|||PROCESS_OUTGOING_CALLS|
||ACCESS_COARSE_LOCATION||SMS|SEND_SMS|
|MICROPHONE|RECORD_AUDIO|||RECEIVE_SMS|
|SENSORS|BODY_SENSORS|||READ_SMS|
|STORAGE|READ_EXTERNAL_STORAGE|||RECEIVE_WAP_PUSH|
||WRITE_EXTERNAL_STORAGE|||RECEIVE_MMS|

再次奉上九组权限
```
<!-- 危险权限 start -->
<!--PHONE-->
<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
<uses-permission android:name="android.permission.CALL_PHONE"/>
<uses-permission android:name="android.permission.READ_CALL_LOG"/>
<uses-permission android:name="android.permission.ADD_VOICEMAIL"/>
<uses-permission android:name="android.permission.WRITE_CALL_LOG"/>
<uses-permission android:name="android.permission.USE_SIP"/>
<uses-permission android:name="android.permission.PROCESS_OUTGOING_CALLS"/>
<!--CALENDAR-->
<uses-permission android:name="android.permission.READ_CALENDAR"/>
<uses-permission android:name="android.permission.WRITE_CALENDAR"/>
<!--CAMERA-->
<uses-permission android:name="android.permission.CAMERA"/>
<!--CONTACTS-->
<uses-permission android:name="android.permission.READ_CONTACTS"/>
<uses-permission android:name="android.permission.WRITE_CONTACTS"/>
<uses-permission android:name="android.permission.GET_ACCOUNTS"/>
<!--LOCATION-->
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
<!--MICROPHONE-->
<uses-permission android:name="android.permission.RECORD_AUDIO"/>
<!--SENSORS-->
<uses-permission android:name="android.permission.BODY_SENSORS"/>
<!--SMS-->
<uses-permission android:name="android.permission.SEND_SMS"/>
<uses-permission android:name="android.permission.RECEIVE_SMS"/>
<uses-permission android:name="android.permission.READ_SMS"/>
<uses-permission android:name="android.permission.RECEIVE_WAP_PUSH"/>
<uses-permission android:name="android.permission.RECEIVE_MMS"/>
<!--STORAGE-->
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
<!-- 危险权限 Permissions end -->
```

代码逻辑非常简单，行数不多，注释也很全，直接去看源码就行

[博客链接](http://www.jianshu.com/p/d6e7aa7fe69c)
