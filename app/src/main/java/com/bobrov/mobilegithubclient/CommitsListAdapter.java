package com.bobrov.mobilegithubclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bobrov.mobilegithubclient.Responses.CommitsResponse;

import java.util.List;

public class CommitsListAdapter extends BaseAdapter {

    private static final long SECOND_IN_DAY = 60 * 60 * 24;

    private Context context;
    private List<CommitsResponse> commitsList;

    public CommitsListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<CommitsResponse> commitsList) {
//        for (int i = 0; i<commitsList.size(); i++) {
//            //TODO index of bound ex
//            long currentDate = commitsList.get(i).getCommit().getAuthor().getDate();
//            long nextDate= commitsList.get(i + 1).getCommit().getAuthor().getDate();
//            if ((Math.abs(currentDate - nextDate) > SECOND_IN_DAY)) {
//
//            } else {
//
//            }
//        }
        this.commitsList = commitsList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return commitsList == null ? 0 : commitsList.size();
    }

    @Override
    public CommitsResponse getItem(int position) {
        return commitsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.commits_list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        CommitsResponse commitsResponse = getItem(position);
        viewHolder.commitMessage.setText(commitsResponse.getCommit().getMessage());
        viewHolder.commitAuthor.setText("committed by " + commitsResponse.getAuthor().getLogin());

        return convertView;
    }

    private class ViewHolder {
        TextView commitMessage;
        TextView commitAuthor;

        public ViewHolder(View view) {
            commitMessage = (TextView) view.findViewById(R.id.commits_list_item_message_tv);
            commitAuthor = (TextView) view.findViewById(R.id.commits_list_item_commiter_tv);
        }
    }

}
