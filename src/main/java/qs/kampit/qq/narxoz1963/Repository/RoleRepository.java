package qs.sukaworkplea.qq.narxoz1963.Repository;

import org.apache.tomcat.jni.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qs.sukaworkplea.qq.narxoz1963.joins.Roles;
import qs.sukaworkplea.qq.narxoz1963.joins.Users;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface RoleRepository extends JpaRepository<Roles,Long> {
Roles findByRole(String role);
}
