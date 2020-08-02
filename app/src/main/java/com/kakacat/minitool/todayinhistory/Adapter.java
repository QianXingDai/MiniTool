package com.kakacat.minitool.todayinhistory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kakacat.minitool.R;
import com.kakacat.minitool.todayinhistory.model.Article;

import java.util.List;

public class Adapter extends ArrayAdapter<Article> {

    private int resourceId;
    private Context context;

    public Adapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.resourceId = resource;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Article article = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.title = view.findViewById(R.id.title_article);
            viewHolder.time = view.findViewById(R.id.time_article);
            viewHolder.imageView = view.findViewById(R.id.image_view);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        assert article != null;
        viewHolder.title.setText(article.getTitle());
        String time = article.getYear() + "年" + article.getMonth() + "月" + article.getDay() + "日";
        viewHolder.time.setText(time);
        viewHolder.imageView.setImageURI(article.getPic());

        return view;
    }


    static class ViewHolder {
        TextView title;
        TextView time;
        SimpleDraweeView imageView;
    }
}
