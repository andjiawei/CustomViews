package com.netsite.customview.model;

/**
 * Created by QYer on 2016/8/26.
 */
public class RowModel extends BaseModel {

    //单行View的内容 包括ImageViewIcon和text

    public int resId;
    public String text;

    public RowModel(Integer resId,String text){
        this.resId = resId;
        this.text = text;
    }



}
