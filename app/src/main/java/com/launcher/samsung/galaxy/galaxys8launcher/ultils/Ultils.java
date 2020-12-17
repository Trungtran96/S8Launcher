package com.launcher.samsung.galaxy.galaxys8launcher.ultils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.eftimoff.viewpagertransformers.AccordionTransformer;
import com.eftimoff.viewpagertransformers.BackgroundToForegroundTransformer;
import com.eftimoff.viewpagertransformers.CubeInTransformer;
import com.eftimoff.viewpagertransformers.CubeOutTransformer;
import com.eftimoff.viewpagertransformers.DefaultTransformer;
import com.eftimoff.viewpagertransformers.DepthPageTransformer;
import com.eftimoff.viewpagertransformers.DrawFromBackTransformer;
import com.eftimoff.viewpagertransformers.FlipHorizontalTransformer;
import com.eftimoff.viewpagertransformers.FlipVerticalTransformer;
import com.eftimoff.viewpagertransformers.ForegroundToBackgroundTransformer;
import com.eftimoff.viewpagertransformers.RotateDownTransformer;
import com.eftimoff.viewpagertransformers.RotateUpTransformer;
import com.eftimoff.viewpagertransformers.StackTransformer;
import com.eftimoff.viewpagertransformers.TabletTransformer;
import com.eftimoff.viewpagertransformers.ZoomInTransformer;
import com.eftimoff.viewpagertransformers.ZoomOutSlideTransformer;
import com.eftimoff.viewpagertransformers.ZoomOutTranformer;
import com.google.gson.Gson;
import com.launcher.samsung.galaxy.galaxys8launcher.App;
import com.launcher.samsung.galaxy.galaxys8launcher.DefaultApp;
import com.launcher.samsung.galaxy.galaxys8launcher.R;
import com.launcher.samsung.galaxy.galaxys8launcher.activities.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.provider.Telephony.Sms.getDefaultSmsPackage;

/**
 * Created by Trung Tran Thanh on 10/3/2017.
 */

public class Ultils {

    public static final String TAG = "Ultils";

    public static int checkPermission(String[] permissions, Context context) {
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (String permission : permissions) {
            permissionCheck += ContextCompat.checkSelfPermission(context, permission);
        }
        return permissionCheck;
    }

    public static ArrayList<App> getAppInfor(Context context) {
        ArrayList<App> arr = new ArrayList<>();
        ArrayList<App> arrayList = new ArrayList<>();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> pkgAppsList = context.getPackageManager().queryIntentActivities(mainIntent, 0);

        arr.addAll(customS8(context));

        for (int i = 0; i < pkgAppsList.size(); i++) {
            Drawable urlIcon = pkgAppsList.get(i).activityInfo.applicationInfo.loadIcon(context.getPackageManager());
            String appName = pkgAppsList.get(i).activityInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
            String packageName = pkgAppsList.get(i).activityInfo.applicationInfo.packageName;

            if (!appName.equals("S8 Launcher")){
                App appMobile = new App(urlIcon, appName, packageName);
                arrayList.add(appMobile);
            }
        }

        for (int i=arrayList.size()-1; i>=0; i--){
            if (checkDel(arrayList.get(i), context)){
                arrayList.remove(i);
            }
        }

        arr.addAll(arrayList);

        return arr;
    }

    public static ArrayList<App> getAllApp(Context context) {
        ArrayList<App> arr = new ArrayList<>();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> pkgAppsList = context.getPackageManager().queryIntentActivities(mainIntent, 0);

        for (int i = 0; i < pkgAppsList.size(); i++) {
            Drawable urlIcon = pkgAppsList.get(i).activityInfo.applicationInfo.loadIcon(context.getPackageManager());
            String appName = pkgAppsList.get(i).activityInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
            String packageName = pkgAppsList.get(i).activityInfo.applicationInfo.packageName;

            App appMobile = new App(urlIcon, appName, packageName);
            arr.add(appMobile);
        }

        return arr;
    }

    public static boolean checkDel(App app, Context context){
        PackageManager packman = context.getPackageManager();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String camera = intent.resolveActivity(packman).getPackageName();
        if (app.getPackageName().equals("com.android.settings") || app.getPackageName().equals(getDefaultSmsPackage(context)) || app.getPackageName().equals(camera) || app.getName().equals("Danh bạ") || app.getName().equals("Liên hệ")
                || app.getName().contains("Contact") || app.getPackageName().equals("com.google.android.gms")
                || app.getName().toLowerCase().equals("email")  || app.getName().toLowerCase().equals("ghi chú") || app.getName().toLowerCase().equals("lịch")
                || app.getName().toLowerCase().equals("đồng hồ"))
        {
            return true;
        }
        return false;
    }

    public static ArrayList<App> customS8(Context context){
        ArrayList<App> arr = new ArrayList<>();

        Drawable alarm = context.getResources().getDrawable(R.drawable.alarm);
        Drawable caculator = context.getResources().getDrawable(R.drawable.calculator);
        Drawable calendar = context.getResources().getDrawable(R.drawable.calendar);
        Drawable camera = context.getResources().getDrawable(R.drawable.camera);
        Drawable clock = context.getResources().getDrawable(R.drawable.clock);
        Drawable contact = context.getResources().getDrawable(R.drawable.contacts);
        Drawable email = context.getResources().getDrawable(R.drawable.email);
        Drawable gallery = context.getResources().getDrawable(R.drawable.gallery);
        Drawable internet = context.getResources().getDrawable(R.drawable.internet);
        Drawable mess = context.getResources().getDrawable(R.drawable.messaging);
        Drawable music = context.getResources().getDrawable(R.drawable.music);
        Drawable file = context.getResources().getDrawable(R.drawable.my_files);
        Drawable note = context.getResources().getDrawable(R.drawable.notes);
        Drawable phone = context.getResources().getDrawable(R.drawable.phone);
        Drawable record = context.getResources().getDrawable(R.drawable.voice_recorder);
        Drawable video = context.getResources().getDrawable(R.drawable.video);
        Drawable setting = context.getResources().getDrawable(R.drawable.settings);
        Drawable weather = context.getResources().getDrawable(R.drawable.weather);

        arr.add(new App(alarm, context.getResources().getString(R.string.alarm), ""));
        arr.add(new App(caculator, context.getResources().getString(R.string.calculator), ""));
        arr.add(new App(calendar, context.getResources().getString(R.string.calendar), ""));
        arr.add(new App(camera, context.getResources().getString(R.string.camera), ""));
        arr.add(new App(clock, context.getResources().getString(R.string.clock), ""));
        arr.add(new App(contact, context.getResources().getString(R.string.contact), ""));
        arr.add(new App(email, context.getResources().getString(R.string.email), ""));
        arr.add(new App(gallery, context.getResources().getString(R.string.photos), ""));
        arr.add(new App(internet, context.getResources().getString(R.string.internet), ""));
        arr.add(new App(mess, context.getResources().getString(R.string.mess), ""));
        arr.add(new App(music, context.getResources().getString(R.string.music), ""));
        arr.add(new App(file, context.getResources().getString(R.string.file), ""));
        arr.add(new App(note, context.getResources().getString(R.string.note), ""));
        arr.add(new App(phone, context.getResources().getString(R.string.phone), ""));
        arr.add(new App(record, context.getResources().getString(R.string.record), ""));
        arr.add(new App(video, context.getResources().getString(R.string.video), ""));
        arr.add(new App(setting, context.getResources().getString(R.string.setting_2), ""));
        arr.add(new App(weather, context.getResources().getString(R.string.weather), ""));

        return arr;
    }

    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    public static DisplayMetrics getScreenSize(Context context){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager mWindowManager =  (WindowManager)context.getSystemService(context.WINDOW_SERVICE);
        mWindowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static ViewPager.PageTransformer getAnimation(int i){
        switch (i){
            case 0:
                return new AccordionTransformer();
            case 1:
                return new BackgroundToForegroundTransformer();
            case 2:
                return new CubeInTransformer();
            case 3:
                return new CubeOutTransformer();
            case 4:
                return new DefaultTransformer();
            case 5:
                return new DepthPageTransformer();
            case 6:
                return new DrawFromBackTransformer();
            case 7:
                return new FlipHorizontalTransformer();
            case 8:
                return new FlipVerticalTransformer();
            case 9:
                return new ForegroundToBackgroundTransformer();
            case 10:
                return new RotateDownTransformer();
            case 11:
                return new RotateUpTransformer();
            case 12:
                return new StackTransformer();
            case 13:
                return new TabletTransformer();
            case 14:
                return new ZoomInTransformer();
            case 15:
                return new ZoomOutSlideTransformer();
            case 16:
                return new ZoomOutTranformer();
        }
        return new RotateUpTransformer();
    }

    public static String defaultSnack(Context context){
        ArrayList<DefaultApp> arr = new ArrayList<>();
        arr.add(new DefaultApp(context.getResources().getString(R.string.phone), R.drawable.phone));
        arr.add(new DefaultApp(context.getResources().getString(R.string.mess), R.drawable.messaging));
        arr.add(new DefaultApp(context.getResources().getString(R.string.internet), R.drawable.internet));
        arr.add(new DefaultApp(context.getResources().getString(R.string.camera), R.drawable.camera));

        Gson gson = new Gson();
        String json = gson.toJson(arr);

        return json;
    }

    public static ArrayList<DefaultApp> getArrSnack(Context context){
        String json = SharedPreferencesManager.getSnack(context);
        ArrayList<DefaultApp> arr = new ArrayList<>();

        try {
            JSONArray array = new JSONArray(json);
            for (int i=0; i<array.length(); i++){
                JSONObject object = array.getJSONObject(i);
                String name = object.getString("name");
                int icon = object.getInt("icon");

                DefaultApp app = new DefaultApp(name, icon);
                arr.add(app);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return arr;
    }

    public static void goToAction(Context context, App app) {
        Intent intent;
        boolean isApp = false;
        if (app.getPackageName().equals("")) {
            switch (app.getName()) {
                case "Báo thức":
                    for (int i = 0; i < MainActivity.arr2.size(); i++) {
                        if (MainActivity.arr2.get(i).getPackageName().contains("alarm")
                                ||(MainActivity.arr2.get(i).getName().toLowerCase().contains("alarm") && !MainActivity.arr2.get(i).getPackageName().equals(""))
                                || (MainActivity.arr2.get(i).getName().toLowerCase().contains("báo thức") && !MainActivity.arr2.get(i).getPackageName().equals(""))) {
                            isApp = true;
                            intent = context.getPackageManager().getLaunchIntentForPackage(MainActivity.arr2.get(i).getPackageName());
                            context.startActivity(intent);
                            break;
                        }
                    }
                    if (!isApp) {
                        Toast.makeText(context, "Chưa có ứng dụng Báo Thức", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case "Alarm":
                    for (int i = 0; i < MainActivity.arr2.size(); i++) {
                        if (MainActivity.arr2.get(i).getPackageName().contains("calcul")) {
                            isApp = true;
                            intent = context.getPackageManager().getLaunchIntentForPackage(MainActivity.arr2.get(i).getPackageName());
                            context.startActivity(intent);
                            break;
                        }
                    }
                    if (!isApp) {
                        Toast.makeText(context, "No Alarm Application", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case "Máy tính":
                    boolean isCal = false;
                    for (int i = 0; i < MainActivity.arr2.size(); i++) {
                        if (MainActivity.arr2.get(i).getPackageName().contains("calcul")) {
                            isCal = true;
                            intent = context.getPackageManager().getLaunchIntentForPackage(MainActivity.arr2.get(i).getPackageName());
                            context.startActivity(intent);
                            break;
                        }
                    }
                    if (!isCal) {
                        Toast.makeText(context, "Chưa có ứng dụng máy tính", Toast.LENGTH_SHORT).show();
                    }

                    break;
                case "Caculator":
                    boolean isCal2 = false;
                    for (int i = 0; i < MainActivity.arr2.size(); i++) {
                        if (MainActivity.arr2.get(i).getPackageName().contains("calcul")) {
                            isCal2 = true;
                            intent = context.getPackageManager().getLaunchIntentForPackage(MainActivity.arr2.get(i).getPackageName());
                            context.startActivity(intent);
                            break;
                        }
                    }
                    if (!isCal2) {
                        Toast.makeText(context, "No Calculator Application", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case "Lịch":
                    try {
                        intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_APP_CALENDAR);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                        boolean isLich = false;
                        for (int i = 0; i < MainActivity.arr2.size(); i++) {
                            if (MainActivity.arr2.get(i).getPackageName().toLowerCase().contains("calend")) {
                                isLich = true;
                                intent = context.getPackageManager().getLaunchIntentForPackage(MainActivity.arr2.get(i).getPackageName());
                                context.startActivity(intent);
                                break;
                            }
                        }
                        if (!isLich) {
                            Toast.makeText(context, "Chưa có ứng dụng lịch", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case "Calendar":
                    try {
                        intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_APP_CALENDAR);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                        boolean isLich = false;
                        for (int i = 0; i < MainActivity.arr2.size(); i++) {
                            if (MainActivity.arr2.get(i).getPackageName().toLowerCase().contains("calend")) {
                                isLich = true;
                                intent = context.getPackageManager().getLaunchIntentForPackage(MainActivity.arr2.get(i).getPackageName());
                                context.startActivity(intent);
                                break;
                            }
                        }
                        if (!isLich) {
                            Toast.makeText(context, "No Calendar Application", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case "Máy ảnh":
                    intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    context.startActivity(intent);
                    break;
                case "Camera":
                    intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    context.startActivity(intent);
                    break;
                case "Đồng hồ":
                    boolean isAlarm = false;
                    for (int i = 0; i < MainActivity.arr2.size(); i++) {
                        if (MainActivity.arr2.get(i).getPackageName().contains("clock") || MainActivity.arr2.get(i).getPackageName().contains("alarm")
                                ||(MainActivity.arr2.get(i).getName().toLowerCase().contains("clock") && !MainActivity.arr2.get(i).getPackageName().equals(""))
                                || (MainActivity.arr2.get(i).getName().toLowerCase().contains("đồng hồ") && !MainActivity.arr2.get(i).getPackageName().equals(""))) {
                            isAlarm = true;
                            intent = context.getPackageManager().getLaunchIntentForPackage(MainActivity.arr2.get(i).getPackageName());
                            context.startActivity(intent);
                            break;
                        }
                    }
                    if (!isAlarm) {
                        Toast.makeText(context, "Chưa có ứng dụng đồng hồ", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case "Clock":
                    boolean isAlarm2 = false;
                    for (int i = 0; i < MainActivity.arr2.size(); i++) {
                        if (MainActivity.arr2.get(i).getPackageName().contains("clock") || MainActivity.arr2.get(i).getPackageName().contains("alarm")) {
                            isAlarm = true;
                            intent = context.getPackageManager().getLaunchIntentForPackage(MainActivity.arr2.get(i).getPackageName());
                            context.startActivity(intent);
                            break;
                        }
                    }
                    if (!isAlarm2) {
                        Toast.makeText(context, "No Clock Application", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case "Danh bạ":
                    intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    context.startActivity(intent);
                    break;
                case "Contacts":
                    intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    context.startActivity(intent);
                    break;
                case "Email":
                    try {
                        intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                        for (int i = 0; i < MainActivity.arr2.size(); i++) {
                            if (MainActivity.arr2.get(i).getPackageName().toLowerCase().contains("email")) {
                                isApp = true;
                                intent = context.getPackageManager().getLaunchIntentForPackage(MainActivity.arr2.get(i).getPackageName());
                                context.startActivity(intent);
                                break;
                            }
                        }
                        if (!isApp) {
                            Toast.makeText(context, "Chưa có ứng dụng Email", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case "Ảnh":
                    intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_APP_GALLERY);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    break;
                case "Photos":
                    intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_APP_GALLERY);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    break;
                case "Trình duyệt":
                    try {
                        intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_APP_BROWSER);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                        boolean isLich = false;
                        for (int i = 0; i < MainActivity.arr2.size(); i++) {
                            if (MainActivity.arr2.get(i).getPackageName().toLowerCase().contains("browser")
                                    || MainActivity.arr2.get(i).getPackageName().toLowerCase().equals("chrome")) {
                                isLich = true;
                                intent = context.getPackageManager().getLaunchIntentForPackage(MainActivity.arr2.get(i).getPackageName());
                                context.startActivity(intent);
                                break;
                            }
                        }
                        if (!isLich) {
                            Toast.makeText(context, "Chưa có ứng dụng trình duyệt", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case "Tin nhắn":
                    intent = context.getPackageManager().getLaunchIntentForPackage(getDefaultSmsPackage(context));
                    context.startActivity(intent);
                    break;
                case "Messages":
                    intent = context.getPackageManager().getLaunchIntentForPackage(getDefaultSmsPackage(context));
                    context.startActivity(intent);
                    break;
                case "Nhạc":
                    try {
                        intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_APP_MUSIC);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                        boolean isLich = false;
                        for (int i = 0; i < MainActivity.arr2.size(); i++) {
                            if (MainActivity.arr2.get(i).getPackageName().toLowerCase().contains("music")
                                    || MainActivity.arr2.get(i).getName().toLowerCase().contains("nhạc") && !MainActivity.arr2.get(i).getPackageName().equals("")
                                    || MainActivity.arr2.get(i).getName().toLowerCase().contains("music") && !MainActivity.arr2.get(i).getPackageName().equals("")
                                    || MainActivity.arr2.get(i).getPackageName().toLowerCase().equals("zing mp3")) {
                                isLich = true;
                                intent = context.getPackageManager().getLaunchIntentForPackage(MainActivity.arr2.get(i).getPackageName());
                                context.startActivity(intent);
                                break;
                            }
                        }
                        if (!isLich) {
                            Toast.makeText(context, "Chưa có ứng dụng nghe nhạc", Toast.LENGTH_SHORT).show();
                        }
                    }

                    break;
                case "Music":
                    try {
                        intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_APP_MUSIC);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                        boolean isLich = false;
                        for (int i = 0; i < MainActivity.arr2.size(); i++) {
                            if (MainActivity.arr2.get(i).getPackageName().toLowerCase().contains("music")
                                    || MainActivity.arr2.get(i).getName().toLowerCase().contains("nhạc") && !MainActivity.arr2.get(i).getPackageName().equals("")
                                    || MainActivity.arr2.get(i).getName().toLowerCase().contains("music") && !MainActivity.arr2.get(i).getPackageName().equals("")
                                    || MainActivity.arr2.get(i).getPackageName().toLowerCase().equals("zing mp3")) {
                                isLich = true;
                                intent = context.getPackageManager().getLaunchIntentForPackage(MainActivity.arr2.get(i).getPackageName());
                                context.startActivity(intent);
                                break;
                            }
                        }
                        if (!isLich) {
                            Toast.makeText(context, "No Music Application", Toast.LENGTH_SHORT).show();
                        }
                    }

                    break;
                case "Quản lý file":
                    for (int i = 0; i < MainActivity.arr2.size(); i++) {
                        if (MainActivity.arr2.get(i).getPackageName().contains("file")
                                ||(MainActivity.arr2.get(i).getName().toLowerCase().contains("quản lý file") && !MainActivity.arr2.get(i).getPackageName().equals(""))
                                || (MainActivity.arr2.get(i).getName().toLowerCase().contains("file") && !MainActivity.arr2.get(i).getPackageName().equals(""))) {
                            isApp = true;
                            intent = context.getPackageManager().getLaunchIntentForPackage(MainActivity.arr2.get(i).getPackageName());
                            context.startActivity(intent);
                            break;
                        }
                    }
                    if (!isApp) {
                        Toast.makeText(context, "Chưa có ứng dụng Quản Lý File", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case "Ghi chú":
                    for (int i = 0; i < MainActivity.arr2.size(); i++) {
                        if (MainActivity.arr2.get(i).getPackageName().contains("note")
                                ||(MainActivity.arr2.get(i).getName().toLowerCase().contains("note") && !MainActivity.arr2.get(i).getPackageName().equals(""))
                                || (MainActivity.arr2.get(i).getName().toLowerCase().contains("ghi chú") && !MainActivity.arr2.get(i).getPackageName().equals(""))) {
                            isApp = true;
                            intent = context.getPackageManager().getLaunchIntentForPackage(MainActivity.arr2.get(i).getPackageName());
                            context.startActivity(intent);
                            break;
                        }
                    }
                    if (!isApp) {
                        Toast.makeText(context, "Chưa có ứng dụng Ghi Chú", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case "Điện thoại":
                    intent = new Intent(Intent.ACTION_DIAL);
                    context.startActivity(intent);
                    break;
                case "Phone":
                    intent = new Intent(Intent.ACTION_DIAL);
                    context.startActivity(intent);
                    break;
                case "Ghi âm":
                    boolean isRec = false;
                    for (int i = 0; i < MainActivity.arr2.size(); i++) {
                        if (MainActivity.arr2.get(i).getPackageName().contains("record")
                                || MainActivity.arr2.get(i).getName().toLowerCase().contains("ghi âm") && !MainActivity.arr2.get(i).getPackageName().equals("")
                                || MainActivity.arr2.get(i).getName().toLowerCase().contains("record") && !MainActivity.arr2.get(i).getPackageName().equals("")) {
                            intent = context.getPackageManager().getLaunchIntentForPackage(MainActivity.arr2.get(i).getPackageName());
                            isRec = true;
                            context.startActivity(intent);
                            break;
                        }
                    }
                    if (!isRec) {
                        Toast.makeText(context, "Chưa có ứng dụng ghi âm", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case "Record":
                    boolean isRec2 = false;
                    for (int i = 0; i < MainActivity.arr2.size(); i++) {
                        if (MainActivity.arr2.get(i).getPackageName().contains("record")
                                || MainActivity.arr2.get(i).getName().toLowerCase().contains("ghi âm") && !MainActivity.arr2.get(i).getPackageName().equals("")
                                || MainActivity.arr2.get(i).getName().toLowerCase().contains("record") && !MainActivity.arr2.get(i).getPackageName().equals("")) {
                            intent = context.getPackageManager().getLaunchIntentForPackage(MainActivity.arr2.get(i).getPackageName());
                            isRec2 = true;
                            context.startActivity(intent);
                            break;
                        }
                    }
                    if (!isRec2) {
                        Toast.makeText(context, "No Record Application", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case "Video":
                    for (int i = 0; i < MainActivity.arr2.size(); i++) {
                        if (MainActivity.arr2.get(i).getPackageName().contains("video")
                                ||(MainActivity.arr2.get(i).getName().toLowerCase().contains("video") && !MainActivity.arr2.get(i).getPackageName().equals(""))) {
                            isApp = true;
                            intent = context.getPackageManager().getLaunchIntentForPackage(MainActivity.arr2.get(i).getPackageName());
                            context.startActivity(intent);
                            break;
                        }
                    }
                    if (!isApp) {
                        Toast.makeText(context, "Chưa có ứng dụng Video", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case "Cài đặt":
                    intent = context.getPackageManager().getLaunchIntentForPackage("com.android.settings");
                    context.startActivity(intent);
                    break;
                case "Settings":
                    intent = context.getPackageManager().getLaunchIntentForPackage("com.android.settings");
                    context.startActivity(intent);
                    break;
                case "Thời tiết":
                    boolean isWea = false;
                    for (int i = 0; i < MainActivity.arr2.size(); i++) {
                        if (MainActivity.arr2.get(i).getPackageName().toLowerCase().contains("weather")
                                || MainActivity.arr2.get(i).getName().toLowerCase().contains("weather") && !MainActivity.arr2.get(i).getPackageName().equals("")
                                || MainActivity.arr2.get(i).getName().toLowerCase().contains("thời tiết") && !MainActivity.arr2.get(i).getPackageName().equals("")) {
                            isWea = true;
                            intent = context.getPackageManager().getLaunchIntentForPackage(MainActivity.arr2.get(i).getPackageName());
                            context.startActivity(intent);
                            break;
                        }
                    }
                    if (!isWea) {
                        Toast.makeText(context, "Chưa có ứng dụng thời tiết", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case "Weather":
                    boolean isWea2 = false;
                    for (int i = 0; i < MainActivity.arr2.size(); i++) {
                        if (MainActivity.arr2.get(i).getPackageName().toLowerCase().contains("weather")
                                || MainActivity.arr2.get(i).getName().toLowerCase().contains("weather") && !MainActivity.arr2.get(i).getPackageName().equals("")
                                || MainActivity.arr2.get(i).getName().toLowerCase().contains("thời tiết") && !MainActivity.arr2.get(i).getPackageName().equals("")) {
                            isWea2 = true;
                            intent = context.getPackageManager().getLaunchIntentForPackage(MainActivity.arr2.get(i).getPackageName());
                            context.startActivity(intent);
                            break;
                        }
                    }
                    if (!isWea2) {
                        Toast.makeText(context, "No Weather Application", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case "Store":
                    try {
                        intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_APP_MARKET);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                default:
                    break;
            }
        } else {
            Intent LaunchIntent = context.getPackageManager().getLaunchIntentForPackage(app.getPackageName());
            Log.e(TAG, app.getPackageName());
            context.startActivity(LaunchIntent);
        }
    }
}
