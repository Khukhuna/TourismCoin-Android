package com.toursimcoin.tourismcoin_android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiResponse {
    @SerializedName("message")
    @Expose
    public String message;
}
