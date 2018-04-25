package com.bobrov.mobilegithubclient.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bobrov.mobilegithubclient.R;
import com.bobrov.mobilegithubclient.Responses.BranchResponse;

import java.util.List;

public class BranchesListAdapter extends BaseAdapter {

    private Context context;
    private List<BranchResponse> branchList;

    public BranchesListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<BranchResponse> branchList) {
        this.branchList = branchList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return branchList == null ? 0 : branchList.size();
    }

    @Override
    public Object getItem(int position) {
        return branchList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.branch_list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        BranchResponse branchResponse = (BranchResponse) getItem(position);
        viewHolder.branchName.setText(branchResponse.getName());

        return convertView;
    }

    private class ViewHolder {
        TextView branchName;

        public ViewHolder(View view) {
            branchName = (TextView) view.findViewById(R.id.branch_list_item_name_tv);
        }
    }

}
