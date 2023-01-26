package qs.sukaworkplea.qq.narxoz1963.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qs.sukaworkplea.qq.narxoz1963.joins.Music;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface MusicRepository extends JpaRepository<Music, Long> {
    public List<Music> findAllByOrderBySingerAsc();
    public List<Music> findAllByOrderBySongnameAsc();
    public List<Music> findAllByOrderByDateDesc();
    public List<Music> findAllByOrderByCategoryAsc();
}