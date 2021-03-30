
package com.bt;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class LdResult {

    @SerializedName("srclangs")
    @Expose
    private List<String> srclangs = null;
    @SerializedName("srclangs_confidences")
    @Expose
    private List<Integer> srclangsConfidences = null;
    @SerializedName("extended_srclangs")
    @Expose
    private List<String> extendedSrclangs = null;

    public List<String> getSrclangs() {
        return srclangs;
    }

    public void setSrclangs(List<String> srclangs) {
        this.srclangs = srclangs;
    }

    public List<Integer> getSrclangsConfidences() {
        return srclangsConfidences;
    }

    public void setSrclangsConfidences(List<Integer> srclangsConfidences) {
        this.srclangsConfidences = srclangsConfidences;
    }

    public List<String> getExtendedSrclangs() {
        return extendedSrclangs;
    }

    public void setExtendedSrclangs(List<String> extendedSrclangs) {
        this.extendedSrclangs = extendedSrclangs;
    }

}
