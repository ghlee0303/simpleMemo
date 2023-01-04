package smm.simpleMemo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import smm.simpleMemo.domain.QUser;
import smm.simpleMemo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class UserDslResp {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private JPAQueryFactory queryFactory;
    private QUser qUser = new QUser("user");

    public Integer save(User user) {
        em.persist(user);
        em.flush();

        return user.getId();
    }

    public Optional<User> findByEmailOpt(String email) {
        return Optional.ofNullable(queryFactory.selectFrom(qUser)
                .where(qUser.email.eq(email))
                .fetchOne());
    }

    public Optional<User> findByIdOpt(int id) {
        return Optional.ofNullable(queryFactory.selectFrom(qUser)
                .where(qUser.id.eq(id))
                .fetchOne());
    }

}
