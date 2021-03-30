
package com.bt;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Dict {

    @SerializedName("pos")
    @Expose
    private String pos;
    @SerializedName("terms")
    @Expose
    private List<String> terms = null;
    @SerializedName("entry")
    @Expose
    private List<Entry> entry = null;
    @SerializedName("base_form")
    @Expose
    private String baseForm;
    @SerializedName("pos_enum")
    @Expose
    private Integer posEnum;

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public List<String> getTerms() {
        return terms;
    }

    public void setTerms(List<String> terms) {
        this.terms = terms;
    }

    public List<Entry> getEntry() {
        return entry;
    }

    public void setEntry(List<Entry> entry) {
        this.entry = entry;
    }

    public String getBaseForm() {
        return baseForm;
    }

    public void setBaseForm(String baseForm) {
        this.baseForm = baseForm;
    }

    public Integer getPosEnum() {
        return posEnum;
    }

    public void setPosEnum(Integer posEnum) {
        this.posEnum = posEnum;
    }

}
