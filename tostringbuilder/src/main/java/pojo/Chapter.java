package pojo;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Chapter {
    private String topic;
    private int index;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).build();
    }
}
