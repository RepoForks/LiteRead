package com.wenen.literead.adapter.image;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wenen.literead.ImageLoaderConfig.ImageLoaderConfig;
import com.wenen.literead.R;
import com.wenen.literead.adapter.ClickResponseListener;
import com.wenen.literead.api.APIUrl;
import com.wenen.literead.model.image.ImageListModel;
import com.wenen.literead.activity.image.ThumbleActivity;

import java.util.ArrayList;

/**
 * Created by Wen_en on 16/8/15.
 */
public class ImageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ImageListModel.TngouEntity> list;

    public ImageListAdapter(ArrayList<ImageListModel.TngouEntity> list) {
        this.list = list;
        setHasStableIds(true);
    }

    public void setList(ArrayList<ImageListModel.TngouEntity> list) {
        this.list = list;
    }

    public void updateList(ArrayList<ImageListModel.TngouEntity> list) {
        setList(list);
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
            return list.get(position).hashCode();

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        View itemView = null;

            itemView = LayoutInflater.from(context).inflate(R.layout.image_list_item, parent, false);
            return new CardViewHolder(itemView, new ClickResponseListener() {
                @Override
                public void onWholeClick(int position) {
                    if (position != -1) {
                        Log.e("size", list.size() + "");
                        Intent intent = new Intent(context, ThumbleActivity.class);
                        intent.putExtra("id", list.get(position).id);
                        intent.putExtra("title", list.get(position).title);
                        context.startActivity(intent);
                    }
                }
            });
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CardViewHolder && getItemCount() > 1) {
            ImageLoaderConfig.imageLoader.displayImage(APIUrl.imgUrl + list.get(position).img, ((CardViewHolder) holder).thumbleImg, ImageLoaderConfig.options, ImageLoaderConfig.animateFirstListener);
            ((CardViewHolder) holder).thumbleCount.setText("照片数量:" + list.get(position).size + "");
            ((CardViewHolder) holder).thumbleTitle.setText(list.get(position).title);
        }

    }

    @Override
    public int getItemCount() {
        if (!list.isEmpty())
            return list.size();
        else return 0;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView thumbleImg;
        public TextView thumbleTitle;
        public TextView thumbleCount;
        private ClickResponseListener mClickResponseListener;

        public CardViewHolder(View itemView, ClickResponseListener clickResponseListener) {
            super(itemView);
            this.mClickResponseListener = clickResponseListener;
            thumbleImg = (ImageView) itemView.findViewById(R.id.iv_thumble_img);
            thumbleTitle = (TextView) itemView.findViewById(R.id.tv_thumble_title);
            thumbleCount = (TextView) itemView.findViewById(R.id.tv_thumble_count);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClickResponseListener.onWholeClick(getAdapterPosition());
        }
    }

}
