package ahmedmemsa.nanodegree.baking.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ahmedmemsa.nanodegree.baking.Fragments.StepsFragment;
import ahmedmemsa.nanodegree.baking.Pojo.Step;
import ahmedmemsa.nanodegree.baking.R;
import butterknife.BindView;
import butterknife.ButterKnife;


public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {

    private Context context;
    private List<Step> items;
    private Interaction interaction;
Step selcted ;
    public StepAdapter(Context context) {
        items = new ArrayList<>();

        if (context instanceof StepsFragment.Interaction) {
            interaction = (Interaction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement Interaction");
        }
    }

    public void setItems(List<Step> items) {
        this.items = items;
    }

    public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.item_step_description)
        TextView title;
        @BindView(R.id.step_layout)
        LinearLayout layout;


        StepViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
                Step step = items.get(getAdapterPosition());
                interaction.displayStep(step);
            }



    }



    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.item_step, parent, false);
        StepViewHolder holder = new StepViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        Step step = items.get(position);
        holder.title.setText(step.getShortDescription());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface Interaction {
        void displayStep(Step step);
    }
}
