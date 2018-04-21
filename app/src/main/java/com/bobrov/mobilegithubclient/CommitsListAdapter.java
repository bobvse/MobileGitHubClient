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
        dataList = new ArrayList<Entity>();

        for (int i = 0; i < commitsList.size(); i++) {
            currentDate = commitsList.get(i).getCommit().getAuthor().getDate();
            if (i < commitsList.size() - 1) {
                nextDate = commitsList.get(i + 1).getCommit().getAuthor().getDate();
            } else {
                nextDate = commitsList.get(i).getCommit().getAuthor().getDate();
            }
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

    //TODO Это важно почему то, и при 2х не пашет, узнать где используется
    @Override
    public int getViewTypeCount() {
        return 3;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderCommit viewHolderCommit;
        ViewHolderData viewHolderData;
        int type = getItemViewType(position);

        switch (type) {
            case Entity.SEPARATOR_TYPE:
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.commits_list_separator, parent, false);
                    viewHolderData = new ViewHolderData(convertView);
                    convertView.setTag(viewHolderData);
                } else {
                    viewHolderData = (ViewHolderData) convertView.getTag();
                }
                SeparatorEntity separatorEntity = (SeparatorEntity) getItem(position);

                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy H:mm", Locale.getDefault());
                String dateString = sdf.format(separatorEntity.getDate());
                viewHolderData.date.setText(dateString);
                break;
            case Entity.COMMIT_TYPE:
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.commits_list_item, parent, false);
                    viewHolderCommit = new ViewHolderCommit(convertView);
                    convertView.setTag(viewHolderCommit);
                } else {
                    viewHolderCommit = (ViewHolderCommit) convertView.getTag();
                }
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
            commitMessage = view.findViewById(R.id.commits_list_item_message_tv);
            commitAuthor = view.findViewById(R.id.commits_list_item_commiter_tv);
        }
    }

    private class ViewHolderData {
        TextView date;

        public ViewHolderData(View view) {
            date = view.findViewById(R.id.commits_list_date_tv);
        }
    }

}
