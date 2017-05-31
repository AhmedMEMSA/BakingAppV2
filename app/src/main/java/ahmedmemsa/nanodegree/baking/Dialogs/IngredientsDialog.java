package ahmedmemsa.nanodegree.baking.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import ahmedmemsa.nanodegree.baking.Pojo.Ingredient;
import ahmedmemsa.nanodegree.baking.R;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by ahmed on 8/2/2016.
 */
public class IngredientsDialog extends Dialog {
    Activity activity;
    private List<Ingredient> mIngredients;

    @BindView(R.id.ingredients_parent)
    LinearLayout mParent;


    public IngredientsDialog(Activity activity, List<Ingredient> ingredients) {
        super(activity);
        this.activity = activity;
        if (ingredients != null) {
            mIngredients = ingredients;
        }else {
            mIngredients= new ArrayList<>();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setCancelable(true);

        setContentView(R.layout.dialog_ingredients);

        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        ButterKnife.bind(this);

        for (Ingredient in : mIngredients) {
            TextView tv = (TextView) activity.getLayoutInflater().inflate(R.layout.tv_ingredient, null);
            double qty = in.getQuantity();
            String text = "";
            if (qty % ((int) qty) == 0) {
                text = ((int) qty) + activity.getString(R.string.space) + in.getMeasure() + activity.getString(R.string.of) + in.getIngredient();
            } else {
                text = qty + activity.getString(R.string.space) + in.getMeasure() + activity.getString(R.string.of) + in.getIngredient();

            }
            tv.setText(text);
            mParent.addView(tv);
        }


    }

}
