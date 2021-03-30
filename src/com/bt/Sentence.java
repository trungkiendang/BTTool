
package com.bt;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Sentence {

    @SerializedName("trans")
    @Expose
    private String trans;
    @SerializedName("orig")
    @Expose
    private String orig;
    @SerializedName("backend")
    @Expose
    private Integer backend;

    public String getTrans() {
        return trans;
    }

    public void setTrans(String trans) {
        this.trans = trans;
    }

    public String getOrig() {
        return orig;
    }

    public void setOrig(String orig) {
        this.orig = orig;
    }

    public Integer getBackend() {
        return backend;
    }

    public void setBackend(Integer backend) {
        this.backend = backend;
    }

}
