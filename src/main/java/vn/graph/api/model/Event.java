package vn.graph.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Event {
    private String object;

    @JsonProperty("entry")
    private List<Entry> entryList;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public List<Entry> getEntryList() {
        return entryList;
    }

    public void setEntryList(List<Entry> entryList) {
        this.entryList = entryList;
    }
}
