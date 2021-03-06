package in.tosc.bookd.ui;

/**
 * Created by championswimmer on 4/4/15.
 *
 * Dummy RecyclerView adapter generator
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import in.tosc.bookd.R;

public class NumberedAdapter extends RecyclerView.Adapter<TextViewHolder> {
    private List<String> labels;
    public NumberedAdapter(int count) {
        labels = new ArrayList<String>(count);
        for (int i = 0; i < count; ++i) {
            labels.add(String.valueOf(i));
        }
    }
    @Override
    public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        return new TextViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final TextViewHolder holder, final int position) {
        final String label = labels.get(position);

    }
    @Override
    public int getItemCount() {
        return labels.size();
    }
}