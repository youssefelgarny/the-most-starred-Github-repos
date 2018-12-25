package ma.koursa.most_starred_github_repos;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Item_repos_adapter extends BaseAdapter {


    private Context mContext;
    private List<Repos> mReposList;


    //Constructor

    public Item_repos_adapter(Context mContext, List<Repos> mReposList) {
        this.mContext = mContext;
        this.mReposList = mReposList;
    }

    @Override
    public int getCount() {
        return mReposList.size();
    }

    @Override
    public Object getItem(int position) {
        return mReposList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.item_template_for_repos, null);

        TextView Repos_nom = (TextView) v.findViewById(R.id.Repo_name);
        TextView Repos_description = (TextView) v.findViewById(R.id.Repo_description);
        TextView Repos_owner_name = (TextView) v.findViewById(R.id.Repo_owner_name);
        TextView Repos_number_of_stars = (TextView) v.findViewById(R.id.Repo_number_of_stars);
        ImageView Repos_image_owner=(ImageView)v.findViewById(R.id.Repo_image_owner);


        //final Repos temp = mReposList.get(position);
        final Context context = parent.getContext();


        //Set text for TextView
        Repos_nom.setText(mReposList.get(position).getRepo_name());
        Repos_description.setText(mReposList.get(position).getRepo_description());
        Repos_owner_name.setText(mReposList.get(position).getRepo_owner_name());
        Repos_number_of_stars.setText(getShortNumber(mReposList.get(position).getNumber_of_stars()));

        Picasso.get().load(mReposList.get(position).getImageUrl()).into(Repos_image_owner);


        //Save product id to tag
        v.setTag(mReposList.get(position).getId());

        return v;
    }

    //function to get short number of the stars value if its big than 1000
        public String getShortNumber(int starValue) {
        String shortStarValue = String.valueOf(starValue);

        if(starValue > 1000)
            shortStarValue = String.format("%.1fK",starValue/1000f);

        return shortStarValue;
    }
}
