package com.example.android.poupularmoviesstage1.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mostafa on 4/18/2018.
 */

public class Reviews implements Parcelable{
    String author;
    String content;

    public Reviews(){

    }

    public Reviews(Parcel parcel){
        this.author = parcel.readString();
        this.content = parcel.readString();
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.author);
        dest.writeString(this.content);

    }

    public static final Parcelable.Creator<Reviews> CREATOR =new Parcelable.Creator<Reviews>(){

        @Override
        public Reviews createFromParcel(Parcel source) {
            return new Reviews(source);
        }

        @Override
        public Reviews[] newArray(int size) {
            return new Reviews[size];
        }
    };
}
