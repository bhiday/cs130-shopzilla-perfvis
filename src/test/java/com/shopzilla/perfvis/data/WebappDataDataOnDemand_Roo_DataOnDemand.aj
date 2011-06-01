// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.shopzilla.perfvis.data;

import com.shopzilla.perfvis.data.WebappData;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Component;

privileged aspect WebappDataDataOnDemand_Roo_DataOnDemand {
    
    declare @type: WebappDataDataOnDemand: @Component;
    
    private Random WebappDataDataOnDemand.rnd = new java.security.SecureRandom();
    
    private List<WebappData> WebappDataDataOnDemand.data;
    
    public WebappData WebappDataDataOnDemand.getNewTransientWebappData(int index) {
        com.shopzilla.perfvis.data.WebappData obj = new com.shopzilla.perfvis.data.WebappData();
        setWebappName(obj, index);
        setWebappURI(obj, index);
        setWebappPollInterval(obj, index);
        return obj;
    }
    
    public void WebappDataDataOnDemand.setWebappName(WebappData obj, int index) {
        java.lang.String webappName = "webappName_" + index;
        obj.setWebappName(webappName);
    }
    
    public void WebappDataDataOnDemand.setWebappURI(WebappData obj, int index) {
        java.lang.String webappURI = "webappURI_" + index;
        obj.setWebappURI(webappURI);
    }
    
    public void WebappDataDataOnDemand.setWebappPollInterval(WebappData obj, int index) {
        java.lang.Long webappPollInterval = new Integer(index).longValue();
        obj.setWebappPollInterval(webappPollInterval);
    }
    
    public WebappData WebappDataDataOnDemand.getSpecificWebappData(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        WebappData obj = data.get(index);
        return WebappData.findWebappData(obj.getId());
    }
    
    public WebappData WebappDataDataOnDemand.getRandomWebappData() {
        init();
        WebappData obj = data.get(rnd.nextInt(data.size()));
        return WebappData.findWebappData(obj.getId());
    }
    
    public boolean WebappDataDataOnDemand.modifyWebappData(WebappData obj) {
        return false;
    }
    
    public void WebappDataDataOnDemand.init() {
        data = com.shopzilla.perfvis.data.WebappData.findWebappDataEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'WebappData' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new java.util.ArrayList<com.shopzilla.perfvis.data.WebappData>();
        for (int i = 0; i < 10; i++) {
            com.shopzilla.perfvis.data.WebappData obj = getNewTransientWebappData(i);
            obj.persist();
            obj.flush();
            data.add(obj);
        }
    }
    
}