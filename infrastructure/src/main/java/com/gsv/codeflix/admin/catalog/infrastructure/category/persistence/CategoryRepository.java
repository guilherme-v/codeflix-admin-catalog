package com.gsv.codeflix.admin.catalog.infrastructure.category.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface CategoryRepository extends JpaRepository<CategoryJpaEntity, String > {
}
