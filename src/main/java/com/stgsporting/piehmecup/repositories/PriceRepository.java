package com.stgsporting.piehmecup.repositories;

import com.stgsporting.piehmecup.entities.Price;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PriceRepository extends JpaRepository<Price, Long> {
    @Modifying
    @Query("update PRICES p set p.coins = :price where p.name = :name And p.schoolYear.id = :schoolYearId")
    void updatePrice(@Param("name") String name, @Param("price") Integer price, @Param("schoolYearId") Long schoolYearId);

    @Modifying(clearAutomatically = true)
    @Query("delete from PRICES p where p.name = :name")
    void deletePricesByName(@Param("name") String name);

    @Query("select p from PRICES p where p.name = :name And p.schoolYear.id= :schoolYearId")
    Optional<Price> findPricesByNameAndSchoolYear(@Param("name")String name, @Param("schoolYearId") Long schoolYearId);

    @NonNull
    @Query("select p from PRICES p where p.schoolYear.id= :schoolYearId")
    List<Price> findAllBySchoolYearId(@Param("schoolYearId") Long schoolYearId);

    @Query("select p from PRICES p where p.id != :id And p.schoolYear.id= :schoolYearId")
    List<Price> findAllExcept(@Param("id")Long id, @Param("schoolYearId") Long schoolYearId);

    @Query("select p from PRICES p where p.schoolYear.id= :schoolYearId")
    Page<Price> findAll(Pageable pageable, Long schoolYearId);
}
