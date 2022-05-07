package com.advait.chesscheatstats.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ArchivesDTO {

    private List<String> archives;

    public void setArchives(List<String> archives) {
        this.archives = archives;
    }

    public List<String> getArchives() {
        return archives;
    }
}
