package com.evani.nytimesmostpopular.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {

    @NonNull
    private final String status;

    @NonNull
    private final String copyright;

    @NonNull
    @SerializedName("num_results")

    private final int numResults;

    @NonNull
    private final List<MostPopular> results;

    public Response(@NonNull String status, @NonNull String copyright, @NonNull int numResults, @NonNull List<MostPopular> results) {
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