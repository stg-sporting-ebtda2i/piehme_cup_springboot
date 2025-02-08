package com.stgsporting.piehmecup.repositories;

import com.stgsporting.piehmecup.entities.Price;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PriceRepository extends JpaRepository<Price, Long> {
    @Modifying
    @Query("update PRICES p set p.coins = :price where p.name = :name")
    void updatePrice(@Param("name") String name, @Param("price") Integer price);

    @Modifying(clearAutomatically = true)
    @Query("delete from PRICES p where p.name = :name")
    void deletePricesByName(@Param("name") String name);

    @Query("select p from PRICES p where p.name = :name")
    Optional<Price> findPricesByName(String name);

    @NonNull
    List<Price> findAll();
}
