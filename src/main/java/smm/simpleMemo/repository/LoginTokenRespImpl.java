package smm.simpleMemo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.transaction.annotation.Transactional;
import smm.simpleMemo.domain.LoginToken;
import smm.simpleMemo.domain.QLoginToken;
import smm.simpleMemo.exception.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.Optional;

@Transactional
public class LoginTokenRespImpl implements PersistentTokenRepository {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private JPAQueryFactory queryFactory;
    private final QLoginToken qLoginToken = new QLoginToken("loginToken");

    public String save(LoginToken loginToken) {
        System.out.println("라디오 가가");
        em.persist(loginToken);
        em.flush();

        return loginToken.getSeries();
    }

    public Optional<LoginToken> findById(String series) {
        return Optional.ofNullable(queryFactory.selectFrom(qLoginToken)
                .where(qLoginToken.series.eq(series))
                .fetchOne());
    }

    public void deleteByEmail(String email) {
        queryFactory.delete(qLoginToken)
                .where(qLoginToken.email.eq(email))
                .execute();
    }

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        // TODO Auto-generated method stub
        LoginToken newToken = new LoginToken();
        newToken.setEmail(token.getUsername());
        newToken.setToken(token.getTokenValue());
        newToken.setLast_used(token.getDate());
        newToken.setSeries(token.getSeries());

        save(newToken);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        // TODO Auto-generated method stub
        LoginToken updateToken = findById(series)
                .orElseThrow(()-> new NotFoundException("해당 토큰은 존재하지 않습니다.", 801));
        updateToken.setToken(tokenValue);
        updateToken.setLast_used(lastUsed);
        updateToken.setSeries(series);

        save(updateToken);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String series) {
        // TODO Auto-generated method stub
        LoginToken token = findById(series)
                .orElseThrow(()-> new NotFoundException("해당 토큰은 존재하지 않습니다.", 801));
        PersistentRememberMeToken persistentRememberMeToken
                = new PersistentRememberMeToken(
                        token.getEmail(),
                        series,
                        token.getToken(),
                        token.getLast_used()
                );

        return persistentRememberMeToken;
    }

    @Override
    public void removeUserTokens(String username) {
        // TODO Auto-generated method stub
        deleteByEmail(username);
    }
}
