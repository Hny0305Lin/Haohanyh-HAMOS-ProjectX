// 受Haohanyh Computer Software Products Open Source LICENSE保护 https://git.haohanyh.top:3001/Haohanyh/LICENSE
package com.haohanyh.hamos.projectx;

import java.util.List;

public class Datastreams {
    private List<Datapoints> datapoints;
    private String id;

    public void setDatapoints(List<Datapoints> datapoints) {
        this.datapoints = datapoints;
    }

    public List<Datapoints> getDatapoints() {
        return datapoints;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}


