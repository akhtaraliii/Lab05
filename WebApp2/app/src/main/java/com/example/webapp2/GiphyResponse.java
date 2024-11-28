package com.example.webapp2;

import java.util.List;

public class GiphyResponse {

    private List<Data> data; // List of Data objects

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data {
        private String url;  // URL of the GIF

        // Getter method for url
        public String getUrl() {
            return url;
        }

        // Setter method for url
        public void setUrl(String url) {
            this.url = url;
        }
    }
}
