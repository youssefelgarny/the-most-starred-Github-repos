package ma.koursa.most_starred_github_repos;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView lvRepos;
    private Item_repos_adapter adapter;
    private List<Repos> mReposList;
    LinearLayout footer;
    int num_page=0;
    private int firstVisibleItem, visibleItemCount,totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvRepos = (ListView)findViewById(R.id.listview_repos);
        footer = (LinearLayout)findViewById(R.id.footer);

        mReposList = new ArrayList<Repos>();

        fetchNextRepos();

        footer.setVisibility(View.GONE);

        //[BONUS] As a User I should be able to keep scrolling and new results should appear (pagination).
        lvRepos.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScroll(AbsListView view, int firstVisibleItemm, int visibleItemCountt, int totalItemCountt) {
                firstVisibleItem = firstVisibleItemm;
                visibleItemCount = visibleItemCountt;
                totalItemCount = totalItemCountt;

            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount && scrollState == SCROLL_STATE_IDLE) {

                    fetchNextRepos();

                }
            }
        });

    }

    public void fetchNextRepos(){
        num_page++;
        MyAsyncTaskresources fetch_repos= new MyAsyncTaskresources();
        fetch_repos.execute("https://api.github.com/search/repositories?q=created:>2017-10-22&sort=stars&order=desc&page="+num_page);
    }

    //***********************************************fetching repos in background using AsyncTask***********************************

    String result = "";
    public class MyAsyncTaskresources extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            footer.setVisibility(View.VISIBLE);
        }

        @Override
        protected String  doInBackground(String... params) {

            InputStream isr = null;

            try{
                String url1=params[0];
                URL url = new URL( url1);
                URLConnection urlConnection = url.openConnection();
                isr  = new BufferedInputStream(urlConnection.getInputStream());


            }catch(Exception e){
                Log.e("log_tag", "Error in http connection " + e.toString());
            }

//convert response to string

            try{

                BufferedReader reader = new BufferedReader(new InputStreamReader(isr,"iso-8859-1"),8);

                StringBuilder sb = new StringBuilder();

                String line = null;

                while ((line = reader.readLine()) != null) {

                    sb.append(line + "\n");

                }

                isr.close();

                result=sb.toString();


            }

            catch(Exception e){

                Log.e("log_tag", "Error  converting result " + e.toString());

            }

//parse json data


            return null;
        }

        protected void onPostExecute(String  result2){


            try {
                String s = "";
                String Repos_nom="",Repos_description="",Repos_owner_name = "",ImageUrl= "";
                int id, Repos_number_of_stars;

                JSONObject mainObject = new JSONObject(result);
                JSONArray array = (JSONArray)mainObject.get("items");

                for (int i = 0; i < array.length(); i++) {

                    JSONObject childObject = array.getJSONObject(i);
                    s = s + childObject.getString("name");

                     id = childObject.getInt("id");
                     Repos_nom = childObject.getString("name");
                    Repos_description = childObject.getString("description");


                    JSONObject owner = childObject.getJSONObject("owner");
                    Repos_owner_name= owner.getString("login");
                    ImageUrl = owner.getString("avatar_url");

                    Repos_number_of_stars = childObject.getInt("stargazers_count");

                    mReposList.add(new Repos(id,Repos_nom,Repos_description,Repos_owner_name,Repos_number_of_stars,ImageUrl));

                }

                if(s.length()>0){
                    footer.setVisibility(View.GONE);
                    adapter = new Item_repos_adapter(getApplicationContext(), mReposList);
                    lvRepos.setAdapter(adapter);
                }
            }catch(Exception e) { Log.e("log_tag", "Error Parsing Data "+e.toString());}
        }




    }

    //**********************************************************************
}
