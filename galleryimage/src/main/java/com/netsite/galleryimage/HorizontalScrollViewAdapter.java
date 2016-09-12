package com.netsite.galleryimage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * 用来给ScrollView填充数据，模仿baseAdapter
 */
public class HorizontalScrollViewAdapter {

    private List<Integer> datas;

    public HorizontalScrollViewAdapter(Context context, List<Integer> datas){
        this.datas = datas;
    }

    public int getCount(){
       return datas==null?0:datas.size();
    }

    public Integer getItemId(int position){
        return position;
    }

    public Object getItem(int position){
        return datas.get(position);
    }

    public View getView(int positon,View convertView,ViewGroup parent){
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image,parent,false);
            viewHolder.imageView= (ImageView) convertView.findViewById(R.id.iv_item);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.imageView.setImageResource(datas.get(positon));
        return convertView;
    }

    public class ViewHolder{
        ImageView imageView;
    }
}
