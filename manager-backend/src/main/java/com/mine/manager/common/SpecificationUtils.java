package com.mine.manager.common;


import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationUtils {

    public static <T> Specification<T> createSpecification(Map<String, Object> filterFields) {
        Map<String, Object> likeFields = (Map<String, Object>) filterFields.get("likeFields");
        Map<String, Object> equalsFields = (Map<String, Object>) filterFields.get("equalsFields");
        Map<String, Object> dateFields = (Map<String, Object>) filterFields.get("dateFields");
        Map<String, List<?>> inFields = (Map<String, List<?>>) filterFields.get("inFields");
        Map<String, List<?>> inSomeFields = (Map<String, List<?>>) filterFields.get("inSomeFields");

        return (root, query, builder) -> {
            Predicate predicate = builder.conjunction();

            if (likeFields != null) {
                predicate = handleLikeFields(likeFields, root, builder, predicate);
            }

            if (equalsFields != null) {
                predicate = handleEqualsFields(equalsFields, root, builder, predicate);
            }

            if (dateFields != null) {
                predicate = handleDateFields(dateFields, root, builder, predicate);
            }

            if (inFields != null) {
                predicate = handleInFields(inFields, root, builder, predicate);
            }

            if (inSomeFields != null) {
                predicate = handleInSomeFields(inSomeFields, root, builder, predicate);
            }

            return predicate;
        };
    }
    private static <T> Predicate handleLikeFields(Map<String, Object> likeFields, Root<T> root, CriteriaBuilder builder, Predicate predicate) {
        for (Map.Entry<String, Object> entry : likeFields.entrySet()) {
            String fieldName = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof String && value != null && !((String) value).isEmpty()) {
                predicate = builder.and(predicate, builder.like(builder.upper(root.get(fieldName)), "%" + ((String) value).toUpperCase() + "%"));
            } else if (value instanceof Integer) {
                Expression<String> castedField = builder.function("CONCAT", String.class, builder.literal(""), root.get(fieldName));
                Expression<String> castedValue = builder.literal("%" + value + "%");
                predicate = builder.and(predicate, builder.like(builder.upper(castedField), castedValue));
            }
        }
        return predicate;
    }
    private static <T> Predicate handleEqualsFields(Map<String, Object> equalsFields, Root<T> root, CriteriaBuilder builder, Predicate predicate) {
        for (Map.Entry<String, Object> entry : equalsFields.entrySet()) {
            String fieldName = entry.getKey();
            Object value = entry.getValue();

            String[] parts = fieldName.split("\\.");
            if (fieldName !=null &&parts.length == 2 && value != null) {
                String relationName = parts[0];
                String field = parts[1];
                predicate = builder.and(predicate, builder.equal(root.get(relationName).get(field), value));
            }
            if (fieldName !=null &&parts.length == 1 && value != null && value != "") {
                predicate = builder.and(predicate, builder.equal(root.get(fieldName), value));
            }
        }
        return predicate;
    }
    private static <T> Predicate handleDateFields(Map<String, Object> dateFields, Root<T> root, CriteriaBuilder builder, Predicate predicate) {
        for (Map.Entry<String, Object> entry : dateFields.entrySet()) {
            String fieldName = entry.getKey();
            Object value = entry.getValue();

            String[] parts = fieldName.split("\\.");
            if (fieldName != null && parts.length == 2 && value instanceof LocalDate) {
                String dateName = parts[0];
                String dateField = parts[1];

                LocalDate startDate = (LocalDate) dateFields.get(dateName + ".startDate");
                LocalDate endDate = (LocalDate) dateFields.get(dateName + ".endDate");

                Predicate datePredicate = builder.conjunction();

                if (startDate != null) {
                    datePredicate = builder.and(datePredicate, builder.greaterThanOrEqualTo(root.get(dateName), startDate));
                }
                if (endDate != null) {
                    datePredicate = builder.and(datePredicate, builder.lessThanOrEqualTo(root.get(dateName), endDate));
                }

                predicate = builder.and(predicate, datePredicate);
            }
        }
        return predicate;
    }
    private static <T> Predicate handleInFields(Map<String, List<?>> inFields, Root<T> root, CriteriaBuilder builder, Predicate predicate) {
        for (Map.Entry<String, List<?>> entry : inFields.entrySet()) {
            String fieldName = entry.getKey();
            List<?> values = entry.getValue();
            if (values != null && !values.isEmpty()) {
                predicate = builder.and(predicate, root.get(fieldName).in(values));
            }
        }
        return predicate;
    }
    private static <T> Predicate handleInSomeFields(Map<String, List<?>> inSomeFields, Root<T> root, CriteriaBuilder builder, Predicate predicate) {
        for (Map.Entry<String, List<?>> entry : inSomeFields.entrySet()) {
            String fieldName = entry.getKey();
            List<?> values = entry.getValue();
            if (values != null) {
                predicate = builder.and(predicate, root.get(fieldName).in(values));
            }
        }
        return predicate;
    }
}
