
package com.naxa.nepal.sudurpaschimanchal.model.rest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.naxa.nepal.sudurpaschimanchal.model.local.Bussiness;

import java.util.List;

public class Data {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private List<Bussiness> data = null;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Bussiness> getData() {
        return data;
    }

    public void setData(List<Bussiness> data) {
        this.data = data;
    }

}
