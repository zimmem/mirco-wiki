package com.zimmem.gae.wiki.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zimmem.gae.wiki.model.AppVar;

public interface AppVarRepository extends JpaRepository<AppVar, String> {

}
