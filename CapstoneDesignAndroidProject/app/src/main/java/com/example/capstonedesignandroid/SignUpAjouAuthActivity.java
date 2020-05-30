package com.example.capstonedesignandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstonedesignandroid.DTO.Lecture;

import java.util.ArrayList;

public class SignUpAjouAuthActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Lecture> list = new ArrayList();
    private WebView mWebView;
    String source, id, pw, name, num, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_ajou_auth);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mWebView = findViewById(R.id.webView);

        Intent intent3 = getIntent();
        id = intent3.getStringExtra("id");
        pw = intent3.getStringExtra("pw");
        name = intent3.getStringExtra("name");
        num = intent3.getStringExtra("num");
        email = intent3.getStringExtra("email");

        mWebView.getSettings().setJavaScriptEnabled(true);//자바스크립트 허용
        mWebView.getSettings().setDomStorageEnabled(true);

        mWebView.loadUrl("https://eclass2.ajou.ac.kr/webapps/");
        mWebView.setWebChromeClient(new WebChromeClient()); //크롬 실행
        mWebView.setWebViewClient(new WebViewClientClass());//새창열기 없이 웹뷰 내에서 다시 열기//페이지 이동 원활히 하기위해 사용

        // 이걸 통해 자바스크립트 내에서 자바함수에 접근할 수 있음.
        mWebView.addJavascriptInterface(new MyJavascriptInterface(), "Android");
        // 페이지가 모두 로드되었을 때, 작업 정의
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // 자바스크립트 인터페이스로 연결되어 있는 getHTML를 실행
                // 자바스크립트 기본 메소드로 html 소스를 통째로 지정해서 인자로 넘김
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                view.loadUrl("javascript:window.Android.getHtml(document.getElementsByTagName('body')[0].innerHTML);");
            }
        });

    } //onCreate

    private class WebViewClientClass extends WebViewClient {//페이지 이동
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("check URL",url);
            view.loadUrl(url);
            return true;
        }
    }
    public class MyJavascriptInterface {
        @JavascriptInterface
        public void getHtml(String html) {
            //위 자바스크립트가 호출되면 여기로 html이 반환됨
            source = html;
            String[] array = source.split("\n");
            String[] temp = new String[10];
            ArrayList<String> lectureArray = new ArrayList<>();
            ArrayList<String> lectureCodeArray = new ArrayList<>();

            Log.d("length", Integer.toString(array.length));

            //로그인 화면이 아닐 때,
            if(array.length > 100) {
                for (int i = 0; i < array.length; i++) {
                    if (array[i].contains("/webapps/blackboard/execute/launcher?type=Course&amp;id=_")) {

                        temp = array[i].substring(104).split("[\\(|_-]");  // _ ( - 없애기
                        if(! temp[1].contains("학습자용"))
                            lectureArray.add(temp[1].substring(1));
                        lectureCodeArray.add(temp[2]);

                        Intent intent = new Intent(getApplicationContext(),SignUpLectureSelectActivity.class);
                        intent.putExtra("id", id);
                        intent.putExtra("pw", pw);
                        intent.putExtra("name", name);
                        intent.putExtra("num", num);
                        intent.putExtra("email", email);
                        intent.putExtra("lectureArray", lectureArray);
                        intent.putExtra("lectureCodeArray", lectureCodeArray);
                        startActivity(intent);
                    }
                }
            }

        }
    }


}
