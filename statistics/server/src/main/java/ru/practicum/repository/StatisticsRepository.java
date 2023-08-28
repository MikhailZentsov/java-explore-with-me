package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.dto.ResponseHitDto;
import ru.practicum.model.HitEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticsRepository extends JpaRepository<HitEntity, Long> {

    @Query("select new ru.practicum.dto.ResponseHitDto(" +
            "   h.app, " +
            "   h.uri, " +
            "   case when :unique = true " +
            "       then count(disctinct(h.ip)) " +
            "       else count(h.ip) " +
            "   end " +
            ") " +
            "from HitEntity h " +
            "where (h.timestamp between :start and :end)" +
            "   and h.uri in :uris " +
            "group by h.app, h.uri " +
            "order by 3 desc")
    List<ResponseHitDto> getStats(@Param("start") LocalDateTime start,
                                  @Param("end") LocalDateTime end,
                                  @Param("uris") List<String> uris,
                                  @Param("unique") Boolean unique);
}
