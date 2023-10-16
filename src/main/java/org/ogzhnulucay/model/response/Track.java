package org.ogzhnulucay.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Track {

    private String trackName;
    private List<String> trackSchedule;

    public Track(String trackName, List<String> trackSchedule) {
        this.trackName = trackName;
        this.trackSchedule = trackSchedule;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public List<String> getTrackSchedule() {
        return trackSchedule;
    }

    public void setTrackSchedule(List<String> trackSchedule) {
        this.trackSchedule = trackSchedule;
    }
}
