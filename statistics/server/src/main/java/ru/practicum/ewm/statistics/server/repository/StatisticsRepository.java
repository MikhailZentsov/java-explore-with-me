package ru.practicum.ewm.statistics.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.statistics.dto.ResponseHitDto;
import ru.practicum.ewm.statistics.server.model.HitEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticsRepository extends JpaRepository<HitEntity, Long> {

    @Query("select new ru.practicum.ewm.statistics.dto.ResponseHitDto(" +
            "   h.app, " +
            "   h.uri, " +
            "   case when :unique = true " +
            "       then count(distinct(h.ip)) " +
            "       else count(h.ip) " +
            "   end " +
            ") " +
            "from HitEntity h " +
            "where h.timestamp between :start and :end" +
            "   and (coalesce(:uris, null) is null or h.uri in :uris) " +
            "group by h.app, h.uri " +
            "order by 3 desc")
    List<ResponseHitDto> getStats(LocalDateTime start,
                                  LocalDateTime end,
                                  List<String> uris,
                                  Boolean unique);
}
