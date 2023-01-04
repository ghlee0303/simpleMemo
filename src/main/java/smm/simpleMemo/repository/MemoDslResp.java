package smm.simpleMemo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import smm.simpleMemo.domain.Memo;
import smm.simpleMemo.domain.QMemo;
import smm.simpleMemo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class MemoDslResp {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private JPAQueryFactory queryFactory;
    private final QMemo qMemo = new QMemo("memo");

    public Integer save(Memo memo) {
        em.persist(memo);
        em.flush();

        return memo.getId();
    }

    public Optional<Memo> findByIdOpt(int id) {
        return Optional.ofNullable(queryFactory.selectFrom(qMemo)
                .where(qMemo.id.eq(id), qMemo.del_date.isNull())
                .fetchOne());
    }

    public Optional<Memo> findByIdAndWriterOpt(int id, User writer) {
        return Optional.ofNullable(queryFactory.selectFrom(qMemo)
                .where(qMemo.id.eq(id), qMemo.writer.eq(writer))
                .fetchOne());
    }

    public List<Memo> findMemoList() {
        return queryFactory.selectFrom(qMemo)
                .where(qMemo.del_date.isNull())
                .fetch();
    }

    public List<Memo> findByWriterList(User writer) {
        return queryFactory.selectFrom(qMemo)
                .where(qMemo.writer.eq(writer))
                .fetch();
    }

    public List<Memo> findViewedList(User writer, int offset, int limit) {
        return queryFactory.selectFrom(qMemo)
                .where(qMemo.writer.eq(writer), qMemo.del_date.isNull(), qMemo.read_date.isNotNull())
                .orderBy(qMemo.read_date.desc())
                .offset(offset)
                .limit(limit)
                .fetch();
    }

    public List<Memo> findMemoPageList(User writer, int offset, int limit) {
        return queryFactory.selectFrom(qMemo)
                .where(qMemo.writer.eq(writer), qMemo.del_date.isNull())
                .orderBy(qMemo.reg_date.desc())
                .offset(offset)
                .limit(limit)
                .fetch();
    }


    public Integer findWrittenCount(User writer) {
        return queryFactory.select(qMemo.id.count())
                .from(qMemo)
                .where(qMemo.writer.eq(writer), qMemo.del_date.isNull())
                .fetchFirst()
                .intValue();
    }

}
