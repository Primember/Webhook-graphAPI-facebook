package vn.graph.api.model;

import java.util.List;

public class Entry {

	private String id;
    private List<Messaging> messagingList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Messaging> getMessagingList() {
        return messagingList;
    }

    public void setMessagingList(List<Messaging> messagingList) {
        this.messagingList = messagingList;
    }

}
