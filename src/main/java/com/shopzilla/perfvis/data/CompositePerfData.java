package com.shopzilla.perfvis.data;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.persistence.Column;
import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

@RooJavaBean
@RooToString
@RooEntity
public class CompositePerfData {

    @Column(name = "webapp_name")
    private String webappName;

    @Column(name = "method_name")
    private String methodName;

    @Column(name = "invoke_time")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date invokeTime;

    @Column(name = "exec_time")
    private Long execTime;

    public static void storeCompositePerfData(CompositePerfData compositePerfData) {
        entityManager().persist(compositePerfData);   
    }
    
    @SuppressWarnings("unchecked")
	public static List<CompositePerfData> getCompositePerfDatabyWebapp(String webappName) {
    	return entityManager().createQuery("SELECT o FROM CompositePerfData o WHERE webappName = :webappName").setParameter("webappName", webappName).getResultList();
    }

}
