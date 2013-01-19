package com.zimmem.gae.wiki.dao;

import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zimmem.gae.wiki.model.AppVar;
import com.zimmem.gae.wiki.repository.AppVarRepository;

public class AppVarRepositryTest extends RepositoryTestBase {
    
    
    @Autowired
    private AppVarRepository repository = null;
    
    @Test
    public void testAppVarRepositry(){
        AppVar var = new AppVar("test", "value");
        repository.save(var);
        AppVar storedVar = repository.findOne("test");
        assertNotNull(storedVar);
        assertEquals(var.getKey(), storedVar.getKey());
        assertEquals(var.getValue(), storedVar.getValue());
    }
}
