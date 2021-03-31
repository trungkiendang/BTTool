
package com.bt;

import java.util.List;
import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class GObj {

    @SerializedName("sentences")
    @Expose
    private List<Sentence> sentences = null;
//    @SerializedName("dict")
//    @Expose
//    private List<Dict> dict = null;
//    @SerializedName("src")
//    @Expose
//    private String src;
//    @SerializedName("alternative_translations")
//    @Expose
//    private List<AlternativeTranslation> alternativeTranslations = null;
//
//    @SerializedName("ld_result")
//    @Expose
//    private LdResult ldResult;

    public List<Sentence> getSentences() {
        return sentences;
    }

    public void setSentences(List<Sentence> sentences) {
        this.sentences = sentences;
    }

//    public List<Dict> getDict() {
//        return dict;
//    }
//
//    public void setDict(List<Dict> dict) {
//        this.dict = dict;
//    }
//
//    public String getSrc() {
//        return src;
//    }
//
//    public void setSrc(String src) {
//        this.src = src;
//    }
//
//    public List<AlternativeTranslation> getAlternativeTranslations() {
//        return alternativeTranslations;
//    }
//
//    public void setAlternativeTranslations(List<AlternativeTranslation> alternativeTranslations) {
//        this.alternativeTranslations = alternativeTranslations;
//    }
//
//
//    public LdResult getLdResult() {
//        return ldResult;
//    }
//
//    public void setLdResult(LdResult ldResult) {
//        this.ldResult = ldResult;
//    }

}
