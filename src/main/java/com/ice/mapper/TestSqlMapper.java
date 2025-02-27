package com.ice.mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ice.entity.Tenant;

/**
 * @author : eclair
 * @date : 2024/8/7 07:20
 */
public interface TestSqlMapper {
    @Update("update t_tenant set name = 'test' where id = 2")
    int updateTenantNameById2();

    @Update("update t_tenant set name = 'test' where id = 5")
    int updateTenantNameById5();

    @Select("select * from t_tenant where id = 1")
    Tenant selectOne();

    @Select("select sleep(10), id from t_third_access_record where id=13198143")
    Object selectLong();
}
