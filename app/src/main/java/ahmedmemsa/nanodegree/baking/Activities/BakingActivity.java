package ahmedmemsa.nanodegree.baking.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ahmedmemsa.nanodegree.baking.Adapters.RecipeAdapter;
import ahmedmemsa.nanodegree.baking.Pojo.Recipe;
import ahmedmemsa.nanodegree.baking.R;
import ahmedmemsa.nanodegree.baking.Utils.Handler;
import ahmedmemsa.nanodegree.baking.Utils.Preferences;
import ahmedmemsa.nanodegree.baking.Utils.SimpleIdlingResource;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BakingActivity extends AppCompatActivity {


    private static final String URL_RECIPES = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";


    @BindView(R.id.rv_recipes)
    RecyclerView mRecipesRecyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.tv_progress_status)
    TextView mStatusTextView;
    @BindView(R.id.iv_progress_try_again)
    ImageView mTryAgain;
    @Nullable
    private SimpleIdlingResource mIdlingResource;


    private RecipeAdapter mRecipeAdapter;
    private ArrayList<Recipe> mRecipes;
    private Preferences preferences;
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        preferences = new Preferences(this);
        GridLayoutManager recipesLayoutManager;
        if (getResources().getConfiguration().screenWidthDp > 800) {
            recipesLayoutManager = new GridLayoutManager(this, 4);
        } else if (getResources().getConfiguration().screenWidthDp > 600) {
            recipesLayoutManager = new GridLayoutManager(this, 3);
        } else if (getResources().getConfiguration().screenWidthDp > 400) {
            recipesLayoutManager = new GridLayoutManager(this, 2);
        } else {
            recipesLayoutManager = new GridLayoutManager(this, 1);
        }

        mRecipeAdapter = new RecipeAdapter();
        mRecipes = new ArrayList<>();

        mRecipeAdapter.setItems(mRecipes);

        mRecipesRecyclerView.setAdapter(mRecipeAdapter);
        mRecipesRecyclerView.setLayoutManager(recipesLayoutManager);

        mTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRecipes();
            }
        });

        getRecipes();
    }


    private void getRecipes() {
        displayProgress();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, URL_RECIPES
                ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        preferences.setData(response);
                        hideProgress();
                        parseRecipesResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        displayProgressError(Handler.volleyErrorHandler(error));
                    }
                }
        );
        queue.add(request);
    }

    private void parseRecipesResponse(String response) {
        try {
            JSONArray array = new JSONArray(response);
            if (array.length() < 0) {
                displayProgressError(getString(R.string.no_recipe_to_show));

            } else {
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    Gson gson = new GsonBuilder().create();
                    Recipe recipe = gson.fromJson(jsonObject.toString(), Recipe.class);
                    mRecipes.add(recipe);
                    mRecipeAdapter.notifyDataSetChanged();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            displayProgressError(getString(R.string.server_response_error));
        }
    }

    private void displayProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
        mStatusTextView.setVisibility(View.VISIBLE);
        mTryAgain.setVisibility(View.GONE);
        mStatusTextView.setText(getString(R.string.loading));
    }

    private void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
        mStatusTextView.setVisibility(View.GONE);
        mTryAgain.setVisibility(View.GONE);
        mStatusTextView.setText("");
    }

    private void displayProgressError(String error) {
        mProgressBar.setVisibility(View.GONE);
        mStatusTextView.setVisibility(View.VISIBLE);
        mTryAgain.setVisibility(View.VISIBLE);
        mStatusTextView.setText(error);
    }
}
