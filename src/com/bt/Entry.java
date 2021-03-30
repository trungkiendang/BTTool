
package com.bt;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Entry {

    @SerializedName("word")
    @Expose
    private String word;
    @SerializedName("reverse_translation")
    @Expose
    private List<String> reverseTranslation = null;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<String> getReverseTranslation() {
        return reverseTranslation;
    }

    public void setReverseTranslation(List<String> reverseTranslation) {
        this.reverseTranslation = reverseTranslation;
    }

}
