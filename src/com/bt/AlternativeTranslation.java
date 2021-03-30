
package com.bt;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class AlternativeTranslation {

    @SerializedName("src_phrase")
    @Expose
    private String srcPhrase;
    @SerializedName("alternative")
    @Expose
    private List<Alternative> alternative = null;
    @SerializedName("srcunicodeoffsets")
    @Expose
    private List<Srcunicodeoffset> srcunicodeoffsets = null;
    @SerializedName("raw_src_segment")
    @Expose
    private String rawSrcSegment;
    @SerializedName("start_pos")
    @Expose
    private Integer startPos;
    @SerializedName("end_pos")
    @Expose
    private Integer endPos;

    public String getSrcPhrase() {
        return srcPhrase;
    }

    public void setSrcPhrase(String srcPhrase) {
        this.srcPhrase = srcPhrase;
    }

    public List<Alternative> getAlternative() {
        return alternative;
    }

    public void setAlternative(List<Alternative> alternative) {
        this.alternative = alternative;
    }

    public List<Srcunicodeoffset> getSrcunicodeoffsets() {
        return srcunicodeoffsets;
    }

    public void setSrcunicodeoffsets(List<Srcunicodeoffset> srcunicodeoffsets) {
        this.srcunicodeoffsets = srcunicodeoffsets;
    }

    public String getRawSrcSegment() {
        return rawSrcSegment;
    }

    public void setRawSrcSegment(String rawSrcSegment) {
        this.rawSrcSegment = rawSrcSegment;
    }

    public Integer getStartPos() {
        return startPos;
    }

    public void setStartPos(Integer startPos) {
        this.startPos = startPos;
    }

    public Integer getEndPos() {
        return endPos;
    }

    public void setEndPos(Integer endPos) {
        this.endPos = endPos;
    }

}
