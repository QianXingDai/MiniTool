package com.kakacat.minitool.epidemicinquiry.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.kakacat.minitool.R;
import com.kakacat.minitool.common.util.SystemUtil;
import com.kakacat.minitool.epidemicinquiry.model.ChildBean;
import com.kakacat.minitool.epidemicinquiry.model.GroupBean;

import java.util.List;

public class DomesticAdapter extends BaseExpandableListAdapter {

    private List<GroupBean> groupBeanList;
    private LayoutInflater inflater;

    public DomesticAdapter(Context context, List<GroupBean> groupBeanList) {
        this.inflater = LayoutInflater.from(context);
        this.groupBeanList = groupBeanList;
    }

    @Override
    public int getGroupCount() {
        SystemUtil.log("getGroupCount" + groupBeanList.size());
        return groupBeanList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        SystemUtil.log("getChildrenCount" + groupBeanList.get(i).getChildBeanList().size());
        return groupBeanList.get(i).getChildBeanList().size();
    }

    @Override
    public Object getGroup(int i) {
        SystemUtil.log("getGroup");
        return groupBeanList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        SystemUtil.log("getChild");
        return groupBeanList.get(i).getChildBeanList().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        GroupViewHolder holder;
        GroupBean groupBean = groupBeanList.get(i);
        if (view == null) {
            view = inflater.inflate(R.layout.group_layout, viewGroup, false);
            holder = new GroupViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (GroupViewHolder) view.getTag();
        }

        holder.btnLocation.setText(groupBean.getLocation());
        holder.tvCurrentConfirmCount.setText(groupBean.getCurrentConfirmCount());
        holder.tvSuspectCount.setText(groupBean.getSusPectCount());
        holder.tvCuredCount.setText(groupBean.getCuredCount());
        holder.tvTotalConfirmCount.setText(groupBean.getTotalConfirmCount());
        holder.tvDeadCount.setText(groupBean.getDeadCount());

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildViewHolder holder;
        ChildBean childBean = groupBeanList.get(i).getChildBeanList().get(i1);

        if (view == null) {
            view = inflater.inflate(R.layout.child_layout, viewGroup, false);
            holder = new ChildViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ChildViewHolder) view.getTag();
        }

        holder.tvLocation.setText(childBean.getCityName());
        holder.tvCurrentConfirmCount.setText(childBean.getCurrentConfirmedCount());
        holder.tvCuredCount.setText(childBean.getCuredCount());
        holder.tvSuspectCount.setText(childBean.getSusPectedCount());
        holder.tvDeadCount.setText(childBean.getDeadCount());
        holder.tvTotalConfirmCount.setText(childBean.getConfirmedCount());

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    static class GroupViewHolder {

        private MaterialButton btnLocation;
        private TextView tvCurrentConfirmCount;
        private TextView tvTotalConfirmCount;
        private TextView tvSuspectCount;
        private TextView tvCuredCount;
        private TextView tvDeadCount;

        public GroupViewHolder(View itemView) {
            btnLocation = itemView.findViewById(R.id.btn_location);
            tvCurrentConfirmCount = itemView.findViewById(R.id.tv_current_confirm_count);
            tvTotalConfirmCount = itemView.findViewById(R.id.tv_total_confirm_count);
            tvSuspectCount = itemView.findViewById(R.id.tv_suspect_count);
            tvCuredCount = itemView.findViewById(R.id.tv_cured_count);
            tvDeadCount = itemView.findViewById(R.id.tv_dead_count);
        }
    }

    static class ChildViewHolder {

        private TextView tvLocation;
        private TextView tvCurrentConfirmCount;
        private TextView tvTotalConfirmCount;
        private TextView tvSuspectCount;
        private TextView tvCuredCount;
        private TextView tvDeadCount;

        public ChildViewHolder(View itemView) {
            tvLocation = itemView.findViewById(R.id.tv_location);
            tvCurrentConfirmCount = itemView.findViewById(R.id.tv_current_confirm_count);
            tvTotalConfirmCount = itemView.findViewById(R.id.tv_total_confirm_count);
            tvSuspectCount = itemView.findViewById(R.id.tv_suspect_count);
            tvCuredCount = itemView.findViewById(R.id.tv_cured_count);
            tvDeadCount = itemView.findViewById(R.id.tv_dead_count);
        }
    }
}
