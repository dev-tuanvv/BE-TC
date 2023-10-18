package com.tutorcenter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tutorcenter.dto.clazz.SearchResDto;
import com.tutorcenter.model.Clazz;

@Repository
public interface ClazzRepository extends JpaRepository<Clazz, Integer> {
    @Query(value = "select * from tbl_class limit :offset , :limit", nativeQuery = true)
    public List<SearchResDto> search(int limit, int offset);
}
