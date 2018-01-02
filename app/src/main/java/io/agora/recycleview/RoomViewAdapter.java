package io.agora.recycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.agora.openvcall.R;

/**
 * Created by tianjq1 on 2017/12/12.
 */

public class RoomViewAdapter extends RecyclerView.Adapter<RoomViewAdapter.ViewHolder>{
    public Context context;
    private List<RoomInfo> list;
    private LayoutInflater layoutInflater;
    private OnItemClickListener mListener; // Item点击事件
    public interface OnItemClickListener{
        void onItemClick(View view);
    }


    public RoomViewAdapter(List<RoomInfo> list)
    {
        //this.context = context;
        this.list = list;
        //layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RoomViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_item,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(RoomViewAdapter.ViewHolder holder, int position) {
        holder.rName.setText(list.get(position).getName());
        holder.rTime.setText(list.get(position).getTime());
        holder.rPeople.setText(list.get(position).getPeople());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView rName;
        public TextView rTime;
        public TextView rPeople;


        public ViewHolder(View view){
            super(view);
            rName = (TextView) view.findViewById(R.id.rName);
            rTime = (TextView) view.findViewById(R.id.rTime);
            rPeople = (TextView) view.findViewById(R.id.rPeople);
            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v);
            }
        }
    }
}
