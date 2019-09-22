package com.example.cloudfinal;

/*
Make sure to implement Recyclerview required methods
(onCreateViewHolder(), onBindViewHolder(), and getItemCount()),
based on the error that you see below FunctionViewHolder you need to create a new class called (FunctionViewHolder).
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amazonaws.http.HttpMethodName;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory;
import com.amazonaws.mobileconnectors.apigateway.ApiRequest;
import com.amazonaws.mobileconnectors.apigateway.ApiResponse;
import com.amazonaws.util.IOUtils;
import com.amazonaws.util.StringUtils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MyAdapter extends RecyclerView.Adapter< FunctionViewHolder > {

    private Context mContext;
    private List< menu > mFunctionList;


    //this constructor will be used to pass data from MainActivity.java to this adapter.
    MyAdapter(Context mContext, List< menu > mFunctionList) {
        this.mContext = mContext;
        this.mFunctionList = mFunctionList;
    }

    /*
    Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.
    This new ViewHolder should be constructed with a new View that can represent the items of the given type.
    You can either create a new View manually or inflate it from an XML layout file.
    ViewGroup: The ViewGroup into which the new View will be added after it is bound to an adapter position.
     */

    @Override
    public FunctionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // you need to inflate the custom layout file recyclerview_row_item.xml.
        View mView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_row_item, parent, false);
        return new FunctionViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final FunctionViewHolder holder, int position) {
        // where you will initialize (tempImage) and (tempTitle)
        holder.mImage.setImageResource(mFunctionList.get(position).getFunctionImage());
        holder.mTitle.setText(mFunctionList.get(position).getFunctionName());

        // set clickable
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.getAdapterPosition()==0){
                    //溫度
                    Intent mIntent = new Intent(mContext, temperature.class);
                    mIntent.putExtra("Title", mFunctionList.get(0).getFunctionName());
                    //Log.e("position:", Integer.toString(holder.getAdapterPosition()));
                    mContext.startActivity(mIntent);
                }
                else if (holder.getAdapterPosition()==1){
                    //水濁
                    Intent mIntent = new Intent(mContext, turbid.class);
                    mIntent.putExtra("Title", mFunctionList.get(1).getFunctionName());
                    mContext.startActivity(mIntent);
                }
                else if (holder.getAdapterPosition()==2){
                    //水位
                    Intent mIntent = new Intent(mContext, level.class);
                    mIntent.putExtra("Title", mFunctionList.get(2).getFunctionName());
                    mContext.startActivity(mIntent);
                }
                else if (holder.getAdapterPosition()==3){
                    //PH
                    Intent mIntent = new Intent(mContext, ph.class);
                    mIntent.putExtra("Title", mFunctionList.get(3).getFunctionName());
                    mContext.startActivity(mIntent);
                }
                else if (holder.getAdapterPosition()==4){
                    //即時影像
                    Intent mIntent = new Intent(mContext, set.class);
                    mIntent.putExtra("Title", mFunctionList.get(4).getFunctionName());
                    mContext.startActivity(mIntent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        // actually return the size of the (mFunctionList) instead of null value.
        return mFunctionList.size();
    }

}

class FunctionViewHolder extends RecyclerView.ViewHolder {

    ImageView mImage;
    TextView mTitle;
    CardView mCardView;

    //  you need to declare and initialize Android ImageView (tempImage) and TextView (tempTitle).
    FunctionViewHolder(View itemView) {
        super(itemView);

        mImage = itemView.findViewById(R.id.Image);
        mTitle = itemView.findViewById(R.id.Title);

        // set clickable
        mCardView = itemView.findViewById(R.id.cardview);

    }
}