package com.example.demo.report.repository;

import com.example.demo.report.entity.ReportTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportTemplateRepository extends JpaRepository<ReportTemplate, String> {
}
