package com.omiplekevin.android.jmcdemo.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by OMIPLEKEVIN on Jun 13, 2016.
 */

public class UserProfile {

    @SerializedName("first_name")
    public String first_name;

    @SerializedName("middle_initial")
    public String middle_initial;

    @SerializedName("last_name")
    public String last_name;
}
