
package ahmedmemsa.nanodegree.baking.Widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import ahmedmemsa.nanodegree.baking.Activities.RecipeDetailsActivity;
import ahmedmemsa.nanodegree.baking.Pojo.Recipe;
import ahmedmemsa.nanodegree.baking.R;
import ahmedmemsa.nanodegree.baking.Utils.Preferences;

public class RecipesViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private final Preferences preferences;
    private List<Recipe> items = new ArrayList<>();
    private Context context = null;
    private int appWidgetId;

    public RecipesViewsFactory(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        preferences = new Preferences(context);
        if (preferences.getRecipes().size() < 1) {
            Toast.makeText(context, "You must run the app before using this widget", Toast.LENGTH_LONG).show();
        } else {
            items.addAll(preferences.getRecipes());
        }
    }

    @Override
    public void onCreate() {
        // no-op
    }

    @Override
    public void onDestroy() {
        // no-op
    }

    @Override
    public int getCount() {
        return (items.size());
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews row = new RemoteViews(context.getPackageName(),
                R.layout.item);

        row.setTextViewText(android.R.id.text1, items.get(position).getName());

        Intent i = new Intent();
        Bundle extras = new Bundle();

        extras.putParcelable(RecipeDetailsActivity.EXTRA_RECIPE, items.get(position));
        i.putExtras(extras);
        row.setOnClickFillInIntent(android.R.id.text1, i);

        return (row);
    }

    @Override
    public RemoteViews getLoadingView() {
        return (null);
    }

    @Override
    public int getViewTypeCount() {
        return (1);
    }

    @Override
    public long getItemId(int position) {
        return (position);
    }

    @Override
    public boolean hasStableIds() {
        return (true);
    }

    @Override
    public void onDataSetChanged() {

    }
}