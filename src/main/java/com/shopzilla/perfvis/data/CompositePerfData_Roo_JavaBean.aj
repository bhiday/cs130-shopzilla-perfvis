// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.shopzilla.perfvis.data;

import java.lang.Long;
import java.lang.String;
import java.util.Date;

privileged aspect CompositePerfData_Roo_JavaBean {
    
    public String CompositePerfData.getWebappName() {
        return this.webappName;
    }
    
    public void CompositePerfData.setWebappName(String webappName) {
        this.webappName = webappName;
    }
    
    public String CompositePerfData.getMethodName() {
        return this.methodName;
    }
    
    public void CompositePerfData.setMethodName(String methodName) {
        this.methodName = methodName;
    }
    
    public Date CompositePerfData.getInvokeTime() {
        return this.invokeTime;
    }
    
    public void CompositePerfData.setInvokeTime(Date invokeTime) {
        this.invokeTime = invokeTime;
    }
    
    public Long CompositePerfData.getExecTime() {
        return this.execTime;
    }
    
    public void CompositePerfData.setExecTime(Long execTime) {
        this.execTime = execTime;
    }
    
}
