// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.shopzilla.perfvis.data;

import java.lang.String;

privileged aspect WebappData_Roo_ToString {
    
    public String WebappData.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("WebappName: ").append(getWebappName()).append(", ");
        sb.append("WebappPollInterval: ").append(getWebappPollInterval()).append(", ");
        sb.append("WebappURI: ").append(getWebappURI());
        return sb.toString();
    }
    
}