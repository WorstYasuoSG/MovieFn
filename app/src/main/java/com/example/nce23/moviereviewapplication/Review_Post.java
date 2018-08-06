package com.example.nce23.moviereviewapplication;

import java.sql.Timestamp;

public class Review_Post {
    public String user_id,image_url,desc;
    public Timestamp timestamp;

    public Review_Post(){}


    public Review_Post(String user_id,String image_url,String desc,Timestamp timestamp){
        this.user_id = user_id;
        this.image_url = image_url;
        this.desc = desc;
        this.timestamp = timestamp;
    }

    public String getUser_id(){
        return user_id;
    }
    public void setUser_id(String user_id){
        this.user_id = user_id;
    }
    public String getImage_url() {
        return image_url;
    }
    public void setImage_url(String image_url){
        this.image_url = image_url;
    }
    public String getDesc(){
        return desc;
    }

}

