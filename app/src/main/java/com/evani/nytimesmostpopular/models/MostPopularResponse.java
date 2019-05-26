
package com.evani.nytimesmostpopular.models;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MostPopularResponse implements Serializable {

    @NonNull
    private String status;

    @NonNull
    private String copyright;

    @NonNull
    @SerializedName("num_results")

    private int numResults;

    @NonNull
    private List<MostPopular> results;

    public MostPopularResponse(@NonNull String status, @NonNull String copyright, @NonNull int numResults, @NonNull List<MostPopular> results) {
        this.status = status;
        this.copyright = copyright;
        this.numResults = numResults;
        this.results = results;
    }

    @NonNull
    public String getStatus() {
        return status;
    }

    @NonNull
    public String getCopyright() {
        return copyright;
    }

    @NonNull
    public int getNumResults() {
        return numResults;
    }

    @NonNull
    public List<MostPopular> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "Response{" +
                "status='" + status + '\'' +
                ", copyright='" + copyright + '\'' +
                ", numResults=" + numResults +
                ", results=" + results +
                '}';
    }



}
