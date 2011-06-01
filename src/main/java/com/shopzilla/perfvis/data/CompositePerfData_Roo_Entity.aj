// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.shopzilla.perfvis.data;

import com.shopzilla.perfvis.data.CompositePerfData;
import java.lang.Integer;
import java.lang.Long;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import org.springframework.transaction.annotation.Transactional;

privileged aspect CompositePerfData_Roo_Entity {
    
    declare @type: CompositePerfData: @Entity;
    
    @PersistenceContext
    transient EntityManager CompositePerfData.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long CompositePerfData.id;
    
    @Version
    @Column(name = "version")
    private Integer CompositePerfData.version;
    
    public Long CompositePerfData.getId() {
        return this.id;
    }
    
    public void CompositePerfData.setId(Long id) {
        this.id = id;
    }
    
    public Integer CompositePerfData.getVersion() {
        return this.version;
    }
    
    public void CompositePerfData.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void CompositePerfData.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void CompositePerfData.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            CompositePerfData attached = CompositePerfData.findCompositePerfData(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void CompositePerfData.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void CompositePerfData.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public CompositePerfData CompositePerfData.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        CompositePerfData merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager CompositePerfData.entityManager() {
        EntityManager em = new CompositePerfData().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long CompositePerfData.countCompositePerfDatas() {
        return entityManager().createQuery("SELECT COUNT(o) FROM CompositePerfData o", Long.class).getSingleResult();
    }
    
    public static List<CompositePerfData> CompositePerfData.findAllCompositePerfDatas() {
        return entityManager().createQuery("SELECT o FROM CompositePerfData o", CompositePerfData.class).getResultList();
    }
    
    public static CompositePerfData CompositePerfData.findCompositePerfData(Long id) {
        if (id == null) return null;
        return entityManager().find(CompositePerfData.class, id);
    }
    
    public static List<CompositePerfData> CompositePerfData.findCompositePerfDataEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM CompositePerfData o", CompositePerfData.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}