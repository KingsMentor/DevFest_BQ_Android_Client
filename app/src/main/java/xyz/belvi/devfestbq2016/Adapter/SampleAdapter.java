package xyz.belvi.devfestbq2016.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import xyz.belvi.devfestbq2016.R;

/**
 * Created by zone2 on 11/21/16.
 */

abstract public class SampleAdapter extends RecyclerView.Adapter<ViewHolder> {

    private ArrayList<String> items;

    public SampleAdapter(ArrayList<String> item) {
        this.items = item;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.textView.setText(items.get(position));
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemSelected(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public abstract void onItemSelected(int position);
}
