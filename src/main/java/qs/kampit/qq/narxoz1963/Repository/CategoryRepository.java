package qs.sukaworkplea.qq.narxoz1963.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qs.sukaworkplea.qq.narxoz1963.joins.Category;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
