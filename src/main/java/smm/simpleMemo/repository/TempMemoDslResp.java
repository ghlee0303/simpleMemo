package smm.simpleMemo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import smm.simpleMemo.domain.Memo;
import smm.simpleMemo.domain.QTempMemo;
import smm.simpleMemo.domain.TempMemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class TempMemoDslResp {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private JPAQueryFactory queryFactory;
    private QTempMemo qTempMemo = new QTempMemo("tempMemo");

    public Integer save(TempMemo tempMemo) {
        em.persist(tempMemo);
        em.flush();
        return tempMemo.getId();
    }

    public Optional<TempMemo> findByIdOpt(int tempMemoId) {
        return Optional.ofNullable(queryFactory.selectFrom(qTempMemo)
                .where(qTempMemo.id.eq(tempMemoId))
                .fetchOne());
    }

    public List<TempMemo> findByMemoList(Memo memo) {
        return queryFactory.selectFrom(qTempMemo)
                .where(qTempMemo.memo.eq(memo))
                .fetch();
    }
}
