package com.trackswiftly.utils.aspectj;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import com.trackswiftly.utils.annotations.ValidateOwnership;
import com.trackswiftly.utils.annotations.ValidateOwnership.ValidationType;
import com.trackswiftly.utils.annotations.ValidateOwnerships;
import com.trackswiftly.utils.exception.UnableToProccessIteamException;


import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;


@Aspect
@Component
@Slf4j
public class OwnershipValidationAspect {


    @PersistenceContext
    private EntityManager entityManager;



    @Before("@annotation(com.trackswiftly.utils.annotations.validateOwnership)")
    public void validateBatchOwnership(JoinPoint joinPoint, ValidateOwnership validateOwnership) 
        throws UnableToProccessIteamException {
        
        List<Long> ids = extractIdsFromArguments(joinPoint, validateOwnership.pathToId());
        if (!ids.isEmpty()) {
            validateEntities(
                validateOwnership.entity(),
                ids,
                validateOwnership.validationType()
            );
        }
    }



    @Before("@annotation(com.trackswiftly.utils.annotations.validateOwnerships)")
    public void validateMultipleOwnerships(JoinPoint joinPoint, ValidateOwnerships validateOwnerships) 
        throws UnableToProccessIteamException {
        
        for (ValidateOwnership annotation : validateOwnerships.value()) {
            validateBatchOwnership(joinPoint, annotation);
        }
    }


    private List<Long> extractIdsFromArguments(JoinPoint joinPoint, String path) {
        EvaluationContext context = new StandardEvaluationContext();
        Object[] args = joinPoint.getArgs();
        
        for (int i = 0; i < args.length; i++) {
            context.setVariable("arg" + i, args[i]);
        }
        
        ExpressionParser parser = new SpelExpressionParser();
        Object value = parser.parseExpression(path).getValue(context);

        return processPotentialIds(value);
    }


    private List<Long> processPotentialIds(Object value) {
        if (value instanceof Collection<?> collection) {
            return collection.stream()
                .filter(Objects::nonNull)
                .map(this::convertToLong)
                .toList();
        }
        return value != null ? 
            Collections.singletonList(convertToLong(value)) : 
            Collections.emptyList();
    }


    private Long convertToLong(Object value) {
        if (value instanceof Number number) {
            return number.longValue();
        }
        throw new IllegalArgumentException("ID must be a number");
    }


    private void validateEntities(Class<?> entityClass, List<Long> ids, ValidationType validationType) 
        throws UnableToProccessIteamException {
        
        if (ids.isEmpty()) return;

        String tenantCondition = validationType == ValidationType.BELONGS_TO_TENANT ? 
            " AND e.tenantId = :tenantId" : 
            "";
            
        String queryStr = """
            SELECT COUNT(e.id) 
            FROM %s e 
            WHERE e.id IN :ids%s
            """.formatted(entityClass.getSimpleName(), tenantCondition);

        Query query = entityManager.createQuery(queryStr)
            .setParameter("ids", new HashSet<>(ids));
        
        log.info("Query: {} 📕" , queryStr) ;

        // if (validationType == ValidationType.BELONGS_TO_TENANT) {
        //     query.setParameter("tenantId", TenantContext.getTenantId());
        // }

        Long count = (Long) query.getSingleResult();
        
        if (count != ids.size()) {
            throw new UnableToProccessIteamException(
                "Validation failed for %s entities".formatted(entityClass.getSimpleName())
            );
        }
    }
    
}
