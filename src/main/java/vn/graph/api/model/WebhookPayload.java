package vn.graph.api.model;

import java.util.List;

public class WebhookPayload {
	private List<Entry> entryList;

    public List<Entry> getEntryList() {
        return entryList;
    }

    public void setEntryList(List<Entry> entryList) {
        this.entryList = entryList;
    }

}
