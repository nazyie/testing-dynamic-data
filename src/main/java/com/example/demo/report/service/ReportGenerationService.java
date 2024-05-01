package com.example.demo.report.service;

import com.example.demo.report.entity.ReportTemplate;
import com.example.demo.report.model.ReportTypeA;
import com.example.demo.report.repository.ReportTemplateRepository;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportGenerationService {
    Logger logger = LoggerFactory.getLogger(ReportGenerationService.class);

    @Autowired
    ReportTemplateRepository repository;

    @Autowired
    DataCollectorService dataCollectorService;

    public void generateReport(String reportName) {
        logger.info("Generating report [{}]", reportName);

        constructTestingReportValue();

        repository.findById("report-user").ifPresent(
            reportTemplate -> {
                logger.info("Report template found: [{}]", reportTemplate);

                // Generate report
                generatePdfReport(reportTemplate);
            }
        );

    }

    // generic this method to handle different report type

    // allow the custom mapper to be passed in

    private void generatePdfReport(ReportTemplate reportTemplate) {
        ReportTypeA reportType = new ReportTypeA();

        List<ReportTypeA> retrievedRecord= (List<ReportTypeA>) dataCollectorService.collectData(
                reportTemplate.getStoreProcedure(),
                new ReportTypeA(),
                null);

        logger.info("Retrieved record: [{}]", retrievedRecord);
    }

    // TODO: testing purpose only. Need to remove this !!!
    private void constructTestingReportValue() {
        List<ReportTemplate> reportTemplateList = new ArrayList<>();

        ReportTemplate reportTemplate = new ReportTemplate();
        reportTemplate.setId("report-user");
        reportTemplate.setStoreProcedure("GetUserList");
        reportTemplate.setTemplatePath("testing only");
        reportTemplate.setPagination(false);
        reportTemplate.setParameter(null);
        reportTemplateList.add(reportTemplate);

        ReportTemplate reportTemplate1 = new ReportTemplate();
        reportTemplate1.setId("report-user-pagination");
        reportTemplate1.setStoreProcedure("GetUserListWithPagination");
        reportTemplate1.setTemplatePath("testing only");
        reportTemplate1.setPagination(true);
        reportTemplate1.setParameter(null);
        reportTemplateList.add(reportTemplate1);

        ReportTemplate reportTemplate2 = new ReportTemplate();
        reportTemplate2.setId("report-user-by-username-prefix");
        reportTemplate2.setStoreProcedure("GetUserListByUsernamePrefix");
        reportTemplate2.setTemplatePath("testing only");
        reportTemplate2.setPagination(false);
        reportTemplate2.setParameter(null);
        reportTemplateList.add(reportTemplate2);

        ReportTemplate reportTemplate3 = new ReportTemplate();
        reportTemplate3.setId("report-user-by-username-prefix-pagination");
        reportTemplate3.setStoreProcedure("GetUserListByUsernamePrefixWithPagination");
        reportTemplate3.setTemplatePath("testing only");
        reportTemplate3.setPagination(true);
        reportTemplate3.setParameter(null);
        reportTemplateList.add(reportTemplate3);


        repository.saveAllAndFlush(reportTemplateList);

    }


}
