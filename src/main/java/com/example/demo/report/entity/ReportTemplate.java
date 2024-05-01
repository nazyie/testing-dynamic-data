package com.example.demo.report.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ReportTemplate {

    @Id
    private String id;
    private String storeProcedure;
    private String templatePath;
    private boolean isPagination;
    private String joinReport;
    private String parameter;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoreProcedure() {
        return storeProcedure;
    }

    public void setStoreProcedure(String storeProcedure) {
        this.storeProcedure = storeProcedure;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public boolean isPagination() {
        return isPagination;
    }

    public void setPagination(boolean pagination) {
        isPagination = pagination;
    }

    public String getJoinReport() {
        return joinReport;
    }

    public void setJoinReport(String joinReport) {
        this.joinReport = joinReport;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
}
