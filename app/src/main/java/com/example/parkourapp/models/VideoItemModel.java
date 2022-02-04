package com.example.parkourapp.models;

public class VideoItemModel {

    String title, description,videoId;
    String image;

    public VideoItemModel( String title,String description,String videoId,String image) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.videoId = videoId;
    }
    public VideoItemModel(){

    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
