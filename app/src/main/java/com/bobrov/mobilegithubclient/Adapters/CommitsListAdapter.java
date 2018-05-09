package com.bobrov.mobilegithubclient.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bobrov.mobilegithubclient.R;
import com.bobrov.mobilegithubclient.Responses.CommitEntity;
import com.bobrov.mobilegithubclient.Responses.CommitsResponse;
import com.bobrov.mobilegithubclient.Responses.Entity;
import com.bobrov.mobilegithubclient.Responses.SeparatorEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CommitsListAdapter extends BaseAdapter {

    private Context context;
    private List<Entity> dataList;
    SimpleDateFormat sdf;

    public CommitsListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<CommitsResponse> commitsList) {
        int currentDate;
        int nextDate;

        dataList = new ArrayList<>();
        int count = 0;

        if (commitsList.size() == 1) {
            dataList.add(new SeparatorEntity(commitsList.get(0).getCommit().getAuthor().getDate()));
            dataList.add(new CommitEntity(commitsList.get(0).getCommit().getMessage(), commitsList.get(0).getAuthor().getLogin()));
        }

        for (int i = 1; i < commitsList.size(); i++) {
            nextDate = commitsList.get(i).getCommit().getAuthor().getDay();
            currentDate = commitsList.get(i - 1).getCommit().getAuthor().getDay();

            if (currentDate != nextDate) {
                dataList.add(new SeparatorEntity(commitsList.get(i).getCommit().getAuthor().getDate()));
                dataList.add(new CommitEntity(commitsList.get(i).getCommit().getMessage(), commitsList.get(i).getAuthor().getLogin()));
            } else {
                count++;
                dataList.add(new CommitEntity(commitsList.get(i).getCommit().getMessage(), commitsList.get(i).getAuthor().getLogin()));
                if (count == commitsList.size() - 1) {
                    dataList.add(0, new SeparatorEntity(commitsList.get(0).getCommit().getAuthor().getDate()));
                }
            }

        }
        sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
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
    public int getViewTypeCount() {
        return 2;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);

        switch (type) {
            case Entity.SEPARATOR_TYPE:
                ViewHolderData viewHolderData;
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.commits_list_separator, parent, false);
                    viewHolderData = new ViewHolderData(convertView);
                    convertView.setTag(viewHolderData);
                } else {
                    viewHolderData = (ViewHolderData) convertView.getTag();
                }
                SeparatorEntity separatorEntity = (SeparatorEntity) getItem(position);

                String dateString = sdf.format(separatorEntity.getDate());
                viewHolderData.date.setText(dateString);
                break;
            case Entity.COMMIT_TYPE:
                ViewHolderCommit viewHolderCommit;
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

    private static class ViewHolderCommit {
        private TextView commitMessage;
        private TextView commitAuthor;

        ViewHolderCommit(View view) {
            commitMessage = view.findViewById(R.id.commits_list_item_message_tv);
            commitAuthor = view.findViewById(R.id.commits_list_item_commiter_tv);
        }
    }

    private static class ViewHolderData {
        private TextView date;

        ViewHolderData(View view) {
            date = view.findViewById(R.id.commits_list_date_tv);
        }
    }

}
