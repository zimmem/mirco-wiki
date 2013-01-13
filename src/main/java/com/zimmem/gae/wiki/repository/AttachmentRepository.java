package com.zimmem.gae.wiki.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zimmem.gae.wiki.model.Attachment;

public interface AttachmentRepository extends PagingAndSortingRepository<Attachment, Long> {

    @Query("select  a from Attachment a where a.name = ?1")
    Attachment findByName(String name);

}
