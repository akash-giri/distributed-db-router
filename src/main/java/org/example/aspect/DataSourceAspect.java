package org.example.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.example.config.RoutingDataSource;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(0) // Ensure this runs before Transactional
public class DataSourceAspect {

    @Before("@annotation(com.example.annotation.ReadOnlyConnection)")
    public void setReplica() {
        RoutingDataSource.setDataSourceKey("REPLICA");
    }

    @After("@annotation(com.example.annotation.ReadOnlyConnection)")
    public void clear() {
        RoutingDataSource.clearDataSourceKey();
    }
}