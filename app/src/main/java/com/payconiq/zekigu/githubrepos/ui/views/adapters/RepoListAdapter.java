package com.payconiq.zekigu.githubrepos.ui.views.adapters;

import android.content.res.TypedArray;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.payconiq.zekigu.githubrepos.R;
import com.payconiq.zekigu.githubrepos.core.app.ApplicationManager;
import com.payconiq.zekigu.githubrepos.core.imageloader.ImageLoad;
import com.payconiq.zekigu.githubrepos.core.model.data.BaseRepo;
import com.payconiq.zekigu.githubrepos.core.model.data.GithubRepo;
import com.payconiq.zekigu.githubrepos.ui.views.BaseActivity;
import com.payconiq.zekigu.githubrepos.ui.views.RepoDetailActivity;
import com.payconiq.zekigu.githubrepos.ui.views.RepoListActivity;
import com.payconiq.zekigu.githubrepos.ui.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by zekigu on 15.09.2017
 */
public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.RepoItemViewHolder>{

    private ArrayList<BaseRepo> repoList;
    private BaseActivity activityContext;

    public static class RepoItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private ClickListener clickListener;
        private View rootLayout;
        private ImageView thumbnail;
        private ImageView thumbnailMask;
        private TextView name;
        private TextView privacyStatusName;
        private TextView ownerName;
        private ImageView forkIcon;

        public RepoItemViewHolder(View itemView) {
            super(itemView);
            rootLayout = itemView.findViewById(R.id.itemMasterLayout);
            thumbnail = (ImageView) itemView.findViewById(R.id.repoThumbnailImageView);
            thumbnailMask = (ImageView) itemView.findViewById(R.id.repoThumbnailMask);
            name = (TextView) itemView.findViewById(R.id.repoNameTextView);
            privacyStatusName = (TextView) itemView.findViewById(R.id.repoPrivacyStatusTextView);
            ownerName = (TextView) itemView.findViewById(R.id.repoOwnerNameTextView);
            forkIcon = (ImageView) itemView.findViewById(R.id.forkImageView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onClick(v, getPosition(), true);
            return true;
        }
        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getPosition(), false);
        }

        public void setClickListener(ClickListener clickListener) {
            this.clickListener = clickListener;
        }

        public interface ClickListener {

            /**
             * Called when the view is clicked.
             *
             * @param v view that is clicked
             * @param position of the clicked item
             * @param isLongClick true if long click, false otherwise
             */
            void onClick(View v, int position, boolean isLongClick);

        }
    }

    public RepoListAdapter(ArrayList<BaseRepo> pRepos, BaseActivity pActivityContext) {
        repoList = pRepos;
        activityContext = pActivityContext;
    }

    @Override
    public RepoItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_list_item_layout, parent, false);
        RepoItemViewHolder vh = new RepoItemViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(RepoItemViewHolder holder, int position) {
        setupViewHolderStyle(holder, position);
        setViewValues(position, holder);
        holder.setClickListener(new RepoItemViewHolder.ClickListener() {
            @Override
            public void onClick(View v, int pos, boolean isLongClick) {
                if(activityContext instanceof RepoListActivity) {
                    UIUtils.startAnyActivityWithRepoIndex(activityContext,
                            RepoDetailActivity.class, pos);
                }
            }

        });
    }

    private void setViewValues(int position, RepoItemViewHolder holder) {
        BaseRepo repo = repoList.get(position);
        holder.name.setText(repo.getName());
        holder.privacyStatusName.setText(repo.isPrivate() ? activityContext.getString(R.string.private_string)
                : activityContext.getString(R.string.public_string));
        holder.ownerName.setText(repo.getOwnerLoginName());

        ApplicationManager.getInstance().getImageLoader().loadImageByUrl(
                new ImageLoad.ImageLoadBuilder(
                        Uri.parse(repo.getOwnerAvatarUrl()), holder.thumbnail)
                        .width(60)
                        .height(60)
                        .hasPlaceHolder(true).build());

        holder.forkIcon.setVisibility(repo.isForked() ? View.VISIBLE : View.GONE);
    }

    /**
     *
     * Desc: Method to setup binder views initially
     *
     * @param pViewHolder
     * @param pPosition
     */
    private void setupViewHolderStyle(RepoItemViewHolder pViewHolder, int pPosition) {
        int[] attrs = new int[]{R.attr.selectableItemBackground};
        TypedArray typedArray = activityContext.obtainStyledAttributes(attrs);
        int backgroundResource = typedArray.getResourceId(0, 0);
        pViewHolder.rootLayout.setBackgroundResource(backgroundResource);
        pViewHolder.name.setTextColor(activityContext.getResources().getColor(R.color.colorPrimary));
        pViewHolder.privacyStatusName.setTextColor(activityContext.getResources().getColor(android.R.color.darker_gray));
        pViewHolder.ownerName.setTextColor(activityContext.getResources().getColor(android.R.color.darker_gray));
        pViewHolder.forkIcon.setImageResource(R.drawable.fork);
        pViewHolder.thumbnailMask.setImageResource(R.drawable.repo_picture_mask);
    }

    @Override
    public int getItemCount() {
        return repoList.size();
    }
}