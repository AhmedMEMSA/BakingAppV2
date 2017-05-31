package ahmedmemsa.nanodegree.baking.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import ahmedmemsa.nanodegree.baking.Fragments.StepDetailsFragment;
import ahmedmemsa.nanodegree.baking.Fragments.StepsFragment;
import ahmedmemsa.nanodegree.baking.Pojo.Recipe;
import ahmedmemsa.nanodegree.baking.Pojo.Step;
import ahmedmemsa.nanodegree.baking.R;

public class StepDetailsActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);
        Intent comingIntent = getIntent();
        Step step = comingIntent.getParcelableExtra(RecipeDetailsActivity.EXTRA_STEP);
        String title = comingIntent.getStringExtra(RecipeDetailsActivity.EXTRA_TITLE);
        if (step== null){
            finish();
        }
        if( findViewById(R.id.toolbar) !=null) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(title);
            findViewById(R.id.finish).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        }else {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.container_step, StepDetailsFragment.newInstance(step)).commit();
    }

}
