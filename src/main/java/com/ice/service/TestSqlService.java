package com.ice.service;

import com.ice.mapper.TestSqlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : eclair
 * @date : 2024/8/7 07:20
 */
@Service
public class TestSqlService {
    @Autowired
    TestSqlMapper testSqlMapper;
    
    public void testUpdateSQl1() {
        testSqlMapper.updateTenantNameById2();
        System.out.println("test");
    }
    
    @Transactional
    public void testUpdateLong() {
        testSqlMapper.updateTenantNameById2();
        System.out.println("test long");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void testUpdateSqlLock() {
        testSqlMapper.updateTenantNameById5();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("testLock");
        testSqlMapper.updateTenantNameById2();
    }

    public void testSelect() {
        testSqlMapper.selectOne();
    }
    

    public void testSelectListLong() {
        testSqlMapper.selectLong();
    }

}
