package smm.simpleMemo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

@DataJpaTest
public class DslTest {
    private JPAQueryFactory factory;
    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    public void init() {
        this.factory = new JPAQueryFactory(entityManager);
    }

    @Test
    void qqq() {
    }


}
