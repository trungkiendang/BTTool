
package com.bt;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Alternative {

    @SerializedName("word_postproc")
    @Expose
    private String wordPostproc;

    @SerializedName("has_preceding_space")
    @Expose
    private Boolean hasPrecedingSpace;
    @SerializedName("attach_to_next_token")
    @Expose
    private Boolean attachToNextToken;

    public String getWordPostproc() {
        return wordPostproc;
    }

    public void setWordPostproc(String wordPostproc) {
        this.wordPostproc = wordPostproc;
    }


    public Boolean getHasPrecedingSpace() {
        return hasPrecedingSpace;
    }

    public void setHasPrecedingSpace(Boolean hasPrecedingSpace) {
        this.hasPrecedingSpace = hasPrecedingSpace;
    }

    public Boolean getAttachToNextToken() {
        return attachToNextToken;
    }

    public void setAttachToNextToken(Boolean attachToNextToken) {
        this.attachToNextToken = attachToNextToken;
    }

}
