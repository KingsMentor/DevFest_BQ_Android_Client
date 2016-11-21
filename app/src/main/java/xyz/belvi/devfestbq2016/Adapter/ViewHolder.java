package xyz.belvi.devfestbq2016.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import xyz.belvi.devfestbq2016.R;

/**
 * Created by zone2 on 11/21/16.
 */

public class ViewHolder extends RecyclerView.ViewHolder {
    public TextView textView;
    public ViewHolder(View itemView) {
        super(itemView);
        textView = (TextView)itemView.findViewById(R.id.item_text);
    }
}
