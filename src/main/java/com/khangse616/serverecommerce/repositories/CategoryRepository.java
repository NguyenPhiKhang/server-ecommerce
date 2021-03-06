package com.khangse616.serverecommerce.repositories;

import com.khangse616.serverecommerce.models.Category;
import com.khangse616.serverecommerce.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("select c from Category c where (c.id=:id1 and c.level = 1) or ( c.id = :id2 and c.level=2) or (c.id=:id3 and c.level=3)")
    List<Category> findAllCategoryByProduct(@Param("id1") int id1, @Param("id2") int id2, @Param("id3") int id3);

//    @Query("select c.products from Category c where c.categoryPath=:path")
//    List<Product> findNProduct(@Param("path") String path, Pageable pageable);

    List<Category> findByLevel(int level);
    @Query(value = "select * from categories where parent_id=:parentId",nativeQuery = true)
    List<Category> findByParentId(@Param("parentId") int parentId);
    Category findFirstByCategoryPathAndAndIconIsNull(String categoryPath);
}
