package com.bobrov.mobilegithubclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bobrov.mobilegithubclient.Responses.CommitEntity;
import com.bobrov.mobilegithubclient.Responses.CommitsResponse;
import com.bobrov.mobilegithubclient.Responses.Entity;
import com.bobrov.mobilegithubclient.Responses.SeparatorEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CommitsListAdapter extends BaseAdapter {

    private static final long SECOND_IN_DAY = 60 * 60 * 24;

    private Context context;
    private List<CommitsResponse> commitsList;
    private List<Entity> dataList;

    public CommitsListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<CommitsResponse> commitsList) {
        long currentDate;
        long nextDate;
        dataList = new ArrayList<>();
        for (int i = 0; i < commitsList.size(); i++) {
            currentDate = commitsList.get(i).getCommit().getAuthor().getDate();
            if (i < commitsList.size() - 1) {
                nextDate = commitsList.get(i + 1).getCommit().getAuthor().getDate();
            } else {
                nextDate = commitsList.get(i).getCommit().getAuthor().getDate();
            }
            //TODO если один объект то дополнительный иф
            if (commitsList.size() == 1) {
                dataList.add(new SeparatorEntity(commitsList.get(i).getCommit().getAuthor().getDate()));
                dataList.add(new CommitEntity(commitsList.get(i).getCommit().getMessage(), commitsList.get(i).getAuthor().getLogin()));
            } else {
                if ((Math.abs(nextDate - currentDate) > SECOND_IN_DAY)) {
                    dataList.add(new SeparatorEntity(commitsList.get(i).getCommit().getAuthor().getDate()));
                    dataList.add(new CommitEntity(commitsList.get(i).getCommit().getMessage(), commitsList.get(i).getAuthor().getLogin()));
                } else {
                    dataList.add(new CommitEntity(commitsList.get(i).getCommit().getMessage(), commitsList.get(i).getAuthor().getLogin()));
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).getType();
    }


    @Override
    public Entity getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //TODO КОСТЫЫЛЬ!
        ViewHolderCommit viewHolderCommit = null;
        ViewHolderData viewHolderData = null;
        int type = getItemViewType(position);
        if (convertView == null) {
            switch (type) {
                case Entity.SEPARATOR_TYPE:
                    convertView = LayoutInflater.from(context).inflate(R.layout.commits_list_separator, parent, false);
                    viewHolderData = new ViewHolderData(convertView);
                    convertView.setTag(viewHolderData);
                    break;
                case Entity.COMMIT_TYPE:
                    convertView = LayoutInflater.from(context).inflate(R.layout.commits_list_item, parent, false);
                    viewHolderCommit = new ViewHolderCommit(convertView);
                    convertView.setTag(viewHolderCommit);
                    break;
            }
        } else {
            switch (type) {
                case Entity.SEPARATOR_TYPE:
                    viewHolderData = (ViewHolderData) convertView.getTag();
                    break;
                case Entity.COMMIT_TYPE:
                    viewHolderCommit = (ViewHolderCommit) convertView.getTag();
                    break;
            }
        }
        switch (type) {
            case Entity.SEPARATOR_TYPE:
                SeparatorEntity separatorEntity = (SeparatorEntity) getItem(position);

                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy H:mm", Locale.getDefault());
                String dateString = sdf.format(separatorEntity.getDate());
                viewHolderData.date.setText(dateString);
                break;
            case Entity.COMMIT_TYPE:
                CommitEntity commitEntity = (CommitEntity) getItem(position);
                viewHolderCommit.commitMessage.setText(commitEntity.getMessage());
                viewHolderCommit.commitAuthor.setText("committed by " + commitEntity.getAuthor());
                break;
        }
        return convertView;
    }

    private class ViewHolderCommit {
        TextView commitMessage;
        TextView commitAuthor;

        public ViewHolderCommit(View view) {
            commitMessage = (TextView) view.findViewById(R.id.commits_list_item_message_tv);
            commitAuthor = (TextView) view.findViewById(R.id.commits_list_item_commiter_tv);
        }
    }

    private class ViewHolderData {
        TextView date;

        public ViewHolderData(View view) {
            date = (TextView) view.findViewById(R.id.commits_list_date_tv);
        }
    }

}
