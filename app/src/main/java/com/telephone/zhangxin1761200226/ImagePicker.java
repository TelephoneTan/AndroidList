package com.telephone.zhangxin1761200226;

public class ImagePicker {
    private int[] ids;
    private int index = -1;

    public ImagePicker(int[] ids) {
        this.ids = ids;
    }

    public int next(){
        index++;
        if (index >= ids.length){
            index = 0;
        }
        return ids[index];
    }
}
