package com.omiplekevin.android.jmcdemo.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by OMIPLEKEVIN on Jun 11, 2016.
 */

public class UserModel {

    @SerializedName("id")
    public String id;

    @SerializedName("user_profile")
    public UserProfile user_profile;
}
