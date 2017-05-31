package ahmedmemsa.nanodegree.baking.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ahmedmemsa.nanodegree.baking.Adapters.StepAdapter;
import ahmedmemsa.nanodegree.baking.Dialogs.IngredientsDialog;
import ahmedmemsa.nanodegree.baking.Pojo.Ingredient;
import ahmedmemsa.nanodegree.baking.Pojo.Recipe;
import ahmedmemsa.nanodegree.baking.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsFragment extends Fragment {

    private static final String ARG_RECIPE = "recipe";
    private Recipe mRecipe;
    private Interaction interaction;

    @BindView(R.id.rv_steps)
    RecyclerView mStepsRecyclerView;
    @BindView(R.id.cv_ingredients)
    CardView mIngredientCV;
    private StepAdapter mStepAdapter;


    public StepsFragment() {
    }

    public static StepsFragment newInstance(Recipe recipe) {
        StepsFragment fragment = new StepsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_RECIPE, recipe);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRecipe = getArguments().getParcelable(ARG_RECIPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_steps, container, false);
        ButterKnife.bind(this, rootView);

        if (mRecipe != null) {
            GridLayoutManager StepsLayoutManager = new GridLayoutManager(getActivity(), 1);

            mStepAdapter = new StepAdapter(getActivity());

            mStepAdapter.setItems(mRecipe.getSteps());

            mStepsRecyclerView.setAdapter(mStepAdapter);
            mStepsRecyclerView.setLayoutManager(StepsLayoutManager);
        }
        mIngredientCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //interaction.displayIngredients(mRecipe.getIngredients());
                if(mRecipe != null) {
                    new IngredientsDialog(getActivity(), mRecipe.getIngredients()).show();
                }else {
                    new IngredientsDialog(getActivity(), null).show();
                }
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Interaction) {
            interaction = (Interaction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement Interaction");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interaction = null;
    }

    public interface Interaction {

    }
}
