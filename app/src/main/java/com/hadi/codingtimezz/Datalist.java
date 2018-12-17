package com.hadi.codingtimezz;

public class Datalist {
    public String Name;
    public String Urls;
    public String Platform;
    public String StartTime;
    public String Duration;
    public String EndTime;

    public Datalist() {
    }

    public Datalist(String name, String urls, String platform, String startTime, String duration, String endTime) {
        Name = name;
        Urls = urls;
        Platform = platform;
        StartTime = startTime;
        Duration = duration;
        EndTime = endTime;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUrls() {
        return Urls;
    }

    public void setUrls(String urls) {
        Urls = urls;
    }

    public String getPlatform() {
        return Platform;
    }

    public void setPlatform(String platform) {
        Platform = platform;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }
}
