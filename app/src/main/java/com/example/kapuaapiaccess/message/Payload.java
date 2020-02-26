package com.example.kapuaapiaccess.message;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Payload {

    @SerializedName(value="metrics")
    public List<Metric> metrics;

    public List<Metric> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<Metric> metrics) {
        this.metrics = metrics;
    }



}
