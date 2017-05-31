package ahmedmemsa.nanodegree.baking.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ahmedmemsa.nanodegree.baking.Activities.RecipeDetailsActivity;
import ahmedmemsa.nanodegree.baking.Pojo.Recipe;
import ahmedmemsa.nanodegree.baking.R;
import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MainCategoriesViewHolder> {

    private Context context;
    private List<Recipe> items;

    public RecipeAdapter() {
        items = new ArrayList<>();
    }
    public void setItems(List<Recipe> items) {
        this.items = items;
    }

    public class MainCategoriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.item_recipe_name)
        TextView name;
        @BindView(R.id.item_recipe_serving)
        TextView serving;

        MainCategoriesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Recipe recipe = items.get(getAdapterPosition());
            Intent intent = new Intent(context, RecipeDetailsActivity.class);
            intent.putExtra(RecipeDetailsActivity.EXTRA_RECIPE,recipe);
            context.startActivity(intent);
        }
    }


    @Override
    public MainCategoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.item_recipe, parent, false);
        MainCategoriesViewHolder holder = new MainCategoriesViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(MainCategoriesViewHolder holder, int position) {
        Recipe recipe = items.get(position);
        holder.name.setText(recipe.getName());
        String serving = context.getString(R.string.serving);
        holder.serving.setText(serving);
        holder.serving.append(context.getString(R.string.space)+recipe.getServings());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
