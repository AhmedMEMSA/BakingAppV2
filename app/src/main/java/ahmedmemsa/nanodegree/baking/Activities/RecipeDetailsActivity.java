package ahmedmemsa.nanodegree.baking.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import ahmedmemsa.nanodegree.baking.Adapters.StepAdapter;
import ahmedmemsa.nanodegree.baking.Fragments.StepDetailsFragment;
import ahmedmemsa.nanodegree.baking.Fragments.StepsFragment;
import ahmedmemsa.nanodegree.baking.Pojo.Recipe;
import ahmedmemsa.nanodegree.baking.Pojo.Step;
import ahmedmemsa.nanodegree.baking.R;

public class RecipeDetailsActivity extends AppCompatActivity implements StepsFragment.Interaction, StepAdapter.Interaction {

    public static final String EXTRA_RECIPE = "recipe";
    public static final String EXTRA_STEP = "estep";
    public static final String EXTRA_TITLE = "title";

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent comingIntent = getIntent();

        if (findViewById(R.id.container_step) == null) {
            mTwoPane = false;
        } else {
            mTwoPane = true;
        }

        Recipe recipe = comingIntent.getParcelableExtra(EXTRA_RECIPE);

        getSupportFragmentManager().beginTransaction().replace(R.id.container_steps, StepsFragment.newInstance(recipe)).commit();
        if (recipe != null) {

            getSupportActionBar().setTitle(recipe.getName());
            if (mTwoPane) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container_step, StepDetailsFragment.newInstance(recipe.getSteps().get(0))).commit();
            }
        }

    }


    @Override
    public void displayStep(Step step) {
        if (mTwoPane) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container_step, StepDetailsFragment.newInstance(step)).commit();

        } else {
            Intent intent = new Intent(RecipeDetailsActivity.this, StepDetailsActivity.class);
            intent.putExtra(EXTRA_STEP, step);
            intent.putExtra(EXTRA_TITLE, getSupportActionBar().getTitle().toString());
            startActivity(intent);
        }
    }

}
