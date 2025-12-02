package com.Main.Ecommerce.product.filter;

import com.Main.Ecommerce.product.Product;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductSearchFilter {

    public Specification<Product> productSearchFilter(String stock, String search, BigDecimal min, BigDecimal max, Long categoryId) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (stock != null) {
                if (stock.equals("available")) {
                    predicates.add(criteriaBuilder.greaterThan(root.get("stock"), 0));
                } else if (stock.equals("notavailable")) {
                    predicates.add(criteriaBuilder.equal(root.get("stock"), 0));
                }

            }

            if (search != null) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + search + "%"));
            }
            if (min != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), min));
            }

            if (max != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), max));
            }

            if (categoryId != null) {
                predicates.add(criteriaBuilder.equal(root.join("category", JoinType.LEFT).get("id"), categoryId));
            }
            predicates.add(criteriaBuilder.equal(root.get("isActive"),true));
            root.join("images", JoinType.LEFT);


            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
