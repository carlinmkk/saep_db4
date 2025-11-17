package com.saep2025.saep_db1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.saep2025.saep_db1.model.Movimento;

import java.util.List;

public interface MovimentoRepository extends JpaRepository<Movimento, Long> {

    @Query("""
    SELECT m FROM Movimento m
    WHERE
        CAST(m.idtransacao AS string) LIKE CONCAT('%', :termo, '%')
        OR LOWER(m.produto.descproduto) LIKE LOWER(CONCAT('%', :termo, '%'))
""")
List<Movimento> buscar(@Param("termo") String termo);

}

