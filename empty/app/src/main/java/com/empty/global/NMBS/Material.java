package com.empty.global.NMBS;

import com.empty.global.Enum.Orientation;

public class Material {
    private String parentType;
    private String subType;
    private Orientation orientation;

    public Material(String parentType, String subType, Orientation orientation) {
        this.parentType = parentType;
        this.subType = subType;
        this.orientation = orientation;
    }

    public String getParentType() {
        return parentType;
    }

    public void setParentType(String parentType) {
        this.parentType = parentType;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }
}
