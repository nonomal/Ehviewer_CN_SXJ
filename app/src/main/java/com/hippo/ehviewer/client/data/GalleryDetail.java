/*
 * Copyright 2016 Hippo Seven
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hippo.ehviewer.client.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.hippo.util.DataUtils;

import java.util.Arrays;

/**
 * 画廊参数存储对象
 */
public class GalleryDetail extends GalleryInfo {

    public long apiUid = -1L;
    public String apiKey;
    public int torrentCount;
    public String torrentUrl;
    public String archiveUrl;
    public String parent;
    public String visible;
    public String language;
    public String size;
//    public String updateUrl;
//    public int pages;
    public int SpiderInfoPages;

    public int favoriteCount;
    public boolean isFavorited;
    public int ratingCount;
    public GalleryTagGroup[] tags;
    public GalleryCommentList comments;
    public int previewPages;
    public int SpiderInfoPreviewPages;
    public PreviewSet previewSet;
    public PreviewSet SpiderInfoPreviewSet;

//    public String body;
//    @Nullable
//    public GalleryDetail oldDetail;

    public NewVersion[] newVersions;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.torrentCount);
        dest.writeString(this.torrentUrl);
        dest.writeString(this.archiveUrl);
        dest.writeString(this.parent);
        dest.writeString(this.visible);
        dest.writeString(this.language);
        dest.writeString(this.size);
        dest.writeInt(this.pages);
        dest.writeInt(this.SpiderInfoPages);
        dest.writeInt(this.favoriteCount);
        dest.writeByte(isFavorited ? (byte) 1 : (byte) 0);
        dest.writeInt(this.ratingCount);
        dest.writeParcelableArray(this.tags, flags);
        dest.writeParcelable(this.comments, flags);
        dest.writeInt(this.previewPages);
        dest.writeInt(this.SpiderInfoPreviewPages);
        dest.writeParcelable(this.previewSet, flags);
        dest.writeParcelable(this.SpiderInfoPreviewSet, flags);
//        dest.writeString(this.body);
//        dest.writeParcelable(oldDetail,flags);
        dest.writeParcelableArray(this.newVersions,flags);
    }

    public GalleryDetail() {
    }

    protected GalleryDetail(Parcel in) {
        super(in);
        this.torrentCount = in.readInt();
        this.torrentUrl = in.readString();
        this.archiveUrl = in.readString();
        this.parent = in.readString();
        this.visible = in.readString();
        this.language = in.readString();
        this.size = in.readString();
        this.pages = in.readInt();
        this.SpiderInfoPages = in.readInt();
        this.favoriteCount = in.readInt();
        this.isFavorited = in.readByte() != 0;
        this.ratingCount = in.readInt();
        Parcelable[] array = in.readParcelableArray(GalleryTagGroup.class.getClassLoader());
        if (array != null) {
            this.tags = Arrays.copyOf(array, array.length, GalleryTagGroup[].class);
        } else {
            this.tags = null;
        }
        this.comments = in.readParcelable(getClass().getClassLoader());
        this.previewPages = in.readInt();
        this.SpiderInfoPreviewPages = in.readInt();
        this.previewSet = in.readParcelable(PreviewSet.class.getClassLoader());
        this.SpiderInfoPreviewSet = in.readParcelable(PreviewSet.class.getClassLoader());
//        this.body = in.readString();
//        this.oldDetail = in.readParcelable(GalleryDetail.class.getClassLoader());
        Parcelable[] newVersionArray = in.readParcelableArray(NewVersion.class.getClassLoader());
        if (newVersionArray != null) {
            this.newVersions = Arrays.copyOf(newVersionArray, newVersionArray.length, NewVersion[].class);
        } else {
            this.newVersions = null;
        }
    }

    public static final Creator<GalleryDetail> CREATOR = new Creator<>() {
        @Override
        public GalleryDetail createFromParcel(Parcel source) {
            return new GalleryDetail(source);
        }

        @Override
        public GalleryDetail[] newArray(int size) {
            return new GalleryDetail[size];
        }
    };

    public GalleryDetail getNewGalleryDetail(int index) {
       try{
           GalleryDetail n = DataUtils.copy(this);
           if (newVersions==null){
               return n;
           }
           String updateUrl = newVersions[index].versionUrl;
           String[] params = updateUrl.split("/");
           int length = params.length;
           n.token = params[length-1];
           n.gid = Long.parseLong(params[length-2]);
           n.newVersions = null;
           return n;
       }catch (Throwable e){
           return this;
       }
    }

    public String[] getUpdateVersionName(){
        String[] result = new String[newVersions.length];
        for (int i = 0; i < newVersions.length; i++) {
            result[i] = newVersions[i].versionName;
        }
        return result;
    }
}
