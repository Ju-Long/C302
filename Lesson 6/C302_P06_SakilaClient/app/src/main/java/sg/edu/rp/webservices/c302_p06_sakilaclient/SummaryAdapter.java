package sg.edu.rp.webservices.c302_p06_sakilaclient;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class SummaryAdapter extends ArrayAdapter<CategorySummary> {
    private ArrayList<CategorySummary> categorySummaries;
    private Context context;
    private TextView tvCategoryName, tvCategoryCount;
    private LinearLayout view;

    public SummaryAdapter(Context context, int resource, ArrayList<CategorySummary> objects) {
        super(context, resource, objects);
        categorySummaries = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.summary_list, parent, false);

        view = rowView.findViewById(R.id.summary_list);
        tvCategoryName = rowView.findViewById(R.id.tvCategoryName);
        tvCategoryCount = rowView.findViewById(R.id.tvCategoryCount);

        if (position % 2 == 1) {
            view.setBackgroundColor(Color.parseColor("#c4c4c4"));
        }

        CategorySummary currCate = categorySummaries.get(position);

        tvCategoryName.setText(currCate.getName());
        tvCategoryCount.setText(String.valueOf(currCate.getCount()));
        return rowView;
    }
}
