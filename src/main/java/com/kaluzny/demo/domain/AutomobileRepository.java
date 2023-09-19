package com.kaluzny.demo.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AutomobileRepository extends JpaRepository<Automobile, Long> {

    @Query(value = "SELECT * FROM automobile WHERE name = :name", nativeQuery = true)
    List<Automobile> findByName(String name);
    @Query(value = "SELECT * FROM automobile WHERE color = :color", nativeQuery = true)
    List<Automobile> findByColor(String color);

    @Query(value = "SELECT * FROM automobile WHERE name = :name AND color = :color", nativeQuery = true)
    List<Automobile> findByNameAndColor(String name, String color);


    @Query(value = "SELECT * FROM automobile WHERE color =?1", nativeQuery = true)
    List<Automobile> findByColorStartsWith(String carColor);

    @Query(value = """
            SELECT * FROM automobile WHERE deleted IS FALSE
            """, nativeQuery = true)
    List<Automobile> findAllExists();

    @Query(value = """
            UPDATE automobile SET name = :name, color = :color WHERE id = :id
            """, nativeQuery = true)
    Automobile updateAutomobile(String name, String color);

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "DELETE FROM automobile WHERE id = ?1", nativeQuery = true)
    void removedAuto(Long id);


}
