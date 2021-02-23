package com.Assignment.Restaurant.dao;

import com.Assignment.Restaurant.entity.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<Table, Integer> {
    @Query(value = "SELECT tables.table_id, tables.table_size FROM reservations right  join tables on reservations.table_id = tables.table_id and " +
            "((reservations.reservation_start_time >= :startTime and reservations.reservation_start_time < :endTime ) or (reservations.reservation_start_time < :startTime and reservations.reservation_end_time > :startTime ))" +
            " where reservations.table_id is null  and tables.table_size >= :size order by tables.table_size limit 1 ", nativeQuery = true)
    public Table getAvailableTable(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("size") Integer size);


    @Query(value = "SELECT tables.table_id, tables.table_size FROM reservations right  join tables on reservations.table_id = tables.table_id and " +
            "((reservations.reservation_start_time >= :startTime and reservations.reservation_start_time < :endTime ) or (reservations.reservation_start_time < :startTime and reservations.reservation_end_time > :startTime ))" +
            " where reservations.table_id is null order by tables.table_size ", nativeQuery = true)
    public List<Table> getAvailableTables(@Param("startTime") String startTime, @Param("endTime") String endTime);
}
