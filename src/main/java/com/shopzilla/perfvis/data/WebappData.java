package com.shopzilla.perfvis.data;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.persistence.Column;

@RooJavaBean
@RooToString
@RooEntity
public class WebappData {

    @Column(name = "webapp_name")
    private String webappName;

    @Column(name = "webapp_uri")
    private String webappURI;

}
