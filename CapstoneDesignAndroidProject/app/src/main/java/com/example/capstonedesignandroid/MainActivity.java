package com.example.capstonedesignandroid;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.kakao.util.maps.helper.Utility;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    ImageView imageView;
    private Button StudyBulletinBoardActivityButton;
    private Button LectureroomReservationActivityButton;
    private Button CafeMapActivityButton;
    private Button testActivity;
    private Button LectureroomCheckActivityButton;
    private Button test2Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        StudyBulletinBoardActivityButton = findViewById(R.id.StudyBulletinBoardActivityButton);
        LectureroomReservationActivityButton = findViewById(R.id.LectureroomReservationActivityButton);
        CafeMapActivityButton = findViewById(R.id.CafeMapActivityButton);
        testActivity = findViewById(R.id.testButton);
        LectureroomCheckActivityButton = findViewById(R.id.LectureroomCheckActivityButton);
        test2Button = findViewById(R.id.test2Button);

        StudyBulletinBoardActivityButton.setOnClickListener(this);
        LectureroomReservationActivityButton.setOnClickListener(this);
        CafeMapActivityButton.setOnClickListener(this);
        testActivity.setOnClickListener(this);
        LectureroomCheckActivityButton.setOnClickListener(this);
        test2Button.setOnClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View nav_header_view = navigationView.getHeaderView(0);
        TextView user = (TextView) nav_header_view.findViewById(R.id.name);
        imageView = (ImageView) nav_header_view.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.profile);

        Log.d("asd", getKeyHash(getApplicationContext()));

        Intent intent3 = getIntent();
        String id = intent3.getStringExtra("id");
        String pw = intent3.getStringExtra("pw");
        Log.d("aa", id);
        Log.d("aa", pw);

        //Log.d("ofp", executeLogin(id, pw));
        String html = "<html><head><title>첫번째 에제입니다.</title></head>"
                + "<body><h1>테스트</h1><p>간단히 HTML을 파싱해 보는 샘플예제입니다.</p></body></html>";

        Document doc = Jsoup.parse(html);

        Elements title = doc.select("title");


    } //onCreate


    public String executeLogin(String id, String pw){
        try {
            Connection.Response loginForm = Jsoup.connect("http://www.naver.com")
                    .method(Connection.Method.GET)
                    .execute();

//            Connection.Response mainPage = Jsoup.connect("http://www.naver.com")
//                    .data("user", id)
//                    .data("senha", pw)
//                    .cookies(loginForm.cookies())
//                    .execute();

            Map<String, String> cookies = loginForm.cookies();

            Document logindocument = loginForm.parse();



            return logindocument.select("input. ofp").val();


        }catch (IOException ioe) {
            return null;
        }

    }

    public String getKeyHash(final Context context) {
        PackageInfo packageInfo = Utility.getPackageInfo(context, PackageManager.GET_SIGNATURES);
        if (packageInfo == null)
            return null;

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());

                return Base64.encodeToString(md.digest(), Base64.NO_WRAP);
            } catch (NoSuchAlgorithmException e) {
            }
        }
        return null;
    }

    @Override
    public void onClick(View view) {
        Intent activityintent;
        switch (view.getId()){

            case R.id.StudyBulletinBoardActivityButton:
                activityintent = new Intent(this, StudyBulletinBoardActivity.class);
                startActivity(activityintent);
                break;
            case R.id.LectureroomReservationActivityButton:
                activityintent = new Intent(this, LectureroomReservationActivity.class);
                startActivity(activityintent);
                break;
            case R.id.CafeMapActivityButton:
                activityintent = new Intent(this, CafeMapActivity.class);
                startActivity(activityintent);
                break;
            case R.id.testButton:
                activityintent = new Intent(this, testActivity.class);
                startActivity(activityintent);
                break;
            case R.id.LectureroomCheckActivityButton:
                activityintent = new Intent(this, LectureroomCheckActivity.class);
                startActivity(activityintent);
                break;
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }  else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_question){

        }
        else if(id == R.id.nav_logout){

        }
        else if(id == R.id.nav_promise){

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    public static String getSigneture(Context context){
//        PackageManager pm = context.getPackageManager();
//        try{
//            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
//
//            for(int i = 0; i < packageInfo.signatures.length; i++){
//                Signature signature = packageInfo.signatures[i];
//                try {
//                    MessageDigest md = MessageDigest.getInstance("SHA");
//                    md.update(signature.toByteArray());
//                    return Base64.encodeToString(md.digest(), Base64.NO_WRAP);
//                } catch (NoSuchAlgorithmException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//        }catch(PackageManager.NameNotFoundException e){
//            e.printStackTrace();
//        }
//        return null;
//    }
}
