package com.example.capstonedesignandroid;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstonedesignandroid.Adapter.MyAdapter;
import com.example.capstonedesignandroid.DTO.ItemObject;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class test1 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<ItemObject> list = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test111);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        new Description().execute();

//        //샘플 html 코드 제시
//        String html = "<html><head><title>첫번째 에제입니다.</title></head>"
//                + "<body><h1>테스트</h1><p>간단히 HTML을 파싱해 보는 샘플예제입니다.</p></body></html>";
//
//        Document doc = Jsoup.parse(html);
//
//        Elements title = doc.select("title");
//        Log.d("result: ", "doc= "+ doc);
//        Log.d("result: ", "title= " + title);
    }

    private class Description extends AsyncTask<Void, Void, Void> {

        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.163 Safari/537.36";

        //진행바표시
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //진행다일로그 시작
            progressDialog = new ProgressDialog(test1.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("잠시 기다려 주세요.");
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
//            try {
//                Document doc = Jsoup.connect("https://movie.naver.com/movie/running/current.nhn").get();
//                Elements mElementDataSize = doc.select("ul[class=lst_detail_t1]").select("li"); //필요한 녀석만 꼬집어서 지정
//                int mElementSize = mElementDataSize.size(); //목록이 몇개인지 알아낸다. 그만큼 루프를 돌려야 하나깐.
//
//                for (Element elem : mElementDataSize) { //이렇게 요긴한 기능이
//                    //영화목록 <li> 에서 다시 원하는 데이터를 추출해 낸다.
//                    String my_title = elem.select("li dt[class=tit] a").text();
//                    String my_link = elem.select("li div[class=thumb] a").attr("href");
//                    String my_imgUrl = elem.select("li div[class=thumb] a img").attr("src");
//                    //특정하기 힘들다... 저 앞에 있는집의 오른쪽으로 두번째집의 건너집이 바로 우리집이야 하는 식이다.
//                    Element rElem = elem.select("dl[class=info_txt1] dt").next().first();
//                    String my_release = rElem.select("dd").text();
//                    Element dElem = elem.select("dt[class=tit_t2]").next().first();
//                    String my_director = "감독: " + dElem.select("a").text();
//                    //Log.d("test", "test" + mTitle);
//                    //ArrayList에 계속 추가한다.
//
//                   // list.add(new ItemObject(my_title, my_imgUrl, my_link, my_release, my_director));
//                }
//
//                //추출한 전체 <li> 출력해 보자.
//                //Log.d("debug :", "List " + mElementDataSize);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            try {

//                Connection.Response response = Jsoup.connect("https://eclass2.ajou.ac.kr/")
//                        .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
//                        .header("Sec-Fetch_Dest", "document")
//                        .method(Connection.Method.GET)
//                        .execute();

                Connection.Response response = Jsoup.connect("https://sso.ajou.ac.kr/jsp/sso/ip/login_meta_form.jsp")
                        .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .header("Origin", "https://ctl.ajou.ac.kr")
                        .header("Upgrade-Insecure-Requests", "1")
                        .method(Connection.Method.GET)
                        .execute();

                Document loginPageDocument = response.parse();
                String action = loginPageDocument.select("input.action").val();
                String new_loc = loginPageDocument.select("input.new_loc").val();

                String RelayState = loginPageDocument.select("input.RelayState").val();
                String token = loginPageDocument.select("input.token").val();
                String returnPage = loginPageDocument.select("input.returnPage").val();
                String mobileAgentType = loginPageDocument.select("input.mobileAgentType").val();
                String UserAgent = loginPageDocument.select("input.UserAgent").val();
                String UserAgentIp = loginPageDocument.select("input.UserAgentIp").val();
                String relogin = loginPageDocument.select("input.relogin").val();

                String html = loginPageDocument.html();
                String text = loginPageDocument.text();
                Log.d("html", text);
                Log.d("useragentip", UserAgentIp);
                Log.d("relogin", relogin);

//                Connection.Response mainPage = Jsoup.connect("https://eclass2.ajou.ac.kr/")
//                        .userAgent(userAgent)
//                        .timeout(3000)
//                        .cookies(response.cookies())
//                        .data("user_id", "rkdcksgur")
//                        .data("password", "galaxy4tab")
//                        .data("action", action)
//                        .data("new_loc", new_loc)
//                        .method(Connection.Method.POST)
//                        .execute();

                Connection.Response mainPage = Jsoup.connect("https://sso.ajou.ac.kr/jsp/sso/ip/login_meta_form.jsp")
                        .userAgent(userAgent)
                        .cookies(response.cookies())
                        .data("userId", "rkdcksgur")
                        .data("password", "galaxy4tab")
                        .data("RelayState", RelayState)
                        .data("token", token)
                        .data("mobileAgentType", mobileAgentType)
                        .data("UserAgent", UserAgent)
                        .data("UserAgentIp", UserAgentIp)
                        .data("relogin", relogin)
                        .method(Connection.Method.POST)
                        .execute();

                Document afterlogin = mainPage.parse();
                Elements lectures = afterlogin.select("ul[class=portletList-img cousreListing coursefakeclass u_indent]").select("li"); //필요한 녀석만 꼬집어서 지정
                int mElementSize = lectures.size(); //목록이 몇개인지 알아낸다. 그만큼 루프를 돌려야 하나깐.
                Log.d("lectures", lectures.val());
                Log.d("lectures", lectures.text());
                for (Element elem : lectures) { //이렇게 요긴한 기능이
                    //영화목록 <li> 에서 다시 원하는 데이터를 추출해 낸다.
                    String my_title = elem.select("li a").text();
                    //String my_link = elem.select("li div[class=thumb] a").attr("href");
                    //String my_imgUrl = elem.select("li div[class=thumb] a img").attr("src");
                    //특정하기 힘들다... 저 앞에 있는집의 오른쪽으로 두번째집의 건너집이 바로 우리집이야 하는 식이다.
//                    Element rElem = elem.select("dl[class=info_txt1] dt").next().first();
//                    String my_release = rElem.select("dd").text();
//                    Element dElem = elem.select("dt[class=tit_t2]").next().first();
//                    String my_director = "감독: " + dElem.select("a").text();
                    //Log.d("test", "test" + mTitle);
                    //ArrayList에 계속 추가한다.
                    list.add(new ItemObject(my_title));
                    Log.d("list", my_title);
                }


                String afterhtml = afterlogin.text();

                Log.d("afterhtml", afterhtml);

                Map<String, String> cookies = mainPage.cookies();

                Connection.Response loginPage = Jsoup.connect("https://eclass2.ajou.ac.kr/webapps/portal/execute/tabs/tabAction?tab_tab_group_id=_1_1")
                        .userAgent(userAgent)
                        .timeout(3000)
                        .cookies(mainPage.cookies())
                        // .data("action", action)
                        //.data("new_loc", new_loc)
                        .method(Connection.Method.POST)
                        .execute();

                Document login = loginPage.parse();
                Elements lec = login.select("ul[class=portletList-img cousreListing coursefakeclass u_indent]").select("li"); //필요한 녀석만 꼬집어서 지정
               // int mElementSize = lec.size(); //목록이 몇개인지 알아낸다. 그만큼 루프를 돌려야 하나깐.
                Log.d("lectures", lec.val());
                Log.d("lectures", lec.text());

                String a = login.text();

                Log.d("a", a);

            }
            catch (IOException e){
                Log.d("d", "error");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //ArraList를 인자로 해서 어답터와 연결한다.
            MyAdapter myAdapter = new MyAdapter(list);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(myAdapter);

            progressDialog.dismiss();
        }
    }
}
