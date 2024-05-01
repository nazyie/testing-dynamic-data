package com.example.demo.report.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
public class DataCollectorService {
    Logger logger = LoggerFactory.getLogger(DataCollectorService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<?> collectData(String storeProcedureName, Object objectData, Map<String, String> parameters) {
        logger.info("Collecting data from store procedure [{}]", storeProcedureName);

        SimpleJdbcCall simpleJdbcCall = setupJdbcCallTemplate(storeProcedureName, objectData);
        SqlParameterSource params = new EmptySqlParameterSource();

        if (parameters != null &&  !parameters.isEmpty())
            params = new MapSqlParameterSource().addValues(parameters);

        Map<String, Object> result = simpleJdbcCall.execute(params);

        return (List<?>) result.get("result");
    }

    private SimpleJdbcCall setupJdbcCallTemplate(String storeProcedureName, Object objectData) {
        Field[] fields = objectData.getClass().getDeclaredFields();

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate);
        simpleJdbcCall.withProcedureName(storeProcedureName);
        simpleJdbcCall.returningResultSet("result", (resultSet, rowNum) -> {
            try {
                Object object = objectData.getClass().newInstance();

                for (Field field : fields) {
                    field.setAccessible(true);
                    field.set(object, resultSet.getObject(field.getName()));
                }

                return object;
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        return simpleJdbcCall;
    }

    public List<?> collectDataByPagination() {
        return null;
    }

}
