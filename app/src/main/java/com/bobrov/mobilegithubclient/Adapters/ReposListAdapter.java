package com.bobrov.mobilegithubclient.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bobrov.mobilegithubclient.R;
import com.bobrov.mobilegithubclient.Responses.ReposResponse;

import java.util.List;

public class ReposListAdapter extends BaseAdapter {

    private Context context;
    private List<ReposResponse> reposList;

    public ReposListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ReposResponse> reposList) {
        this.reposList = reposList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return reposList == null ? 0 : reposList.size();
    }

    @Override
    public Object getItem(int position) {
        return reposList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.repos_list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ReposResponse currentRepo = (ReposResponse) getItem(position);
        viewHolder.repoName.setText(currentRepo.getName());
        viewHolder.repoDescription.setText(currentRepo.getDescription());

        return convertView;
    }

    private class ViewHolder {
        TextView repoName;
        TextView repoDescription;

        public ViewHolder(View view) {
            repoName = (TextView) view.findViewById(R.id.repos_list_item_name_tv);
            repoDescription = (TextView) view.findViewById(R.id.repos_list_item_description_tv);
        }
    }

}
