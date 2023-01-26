package smm.simpleMemo.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLoginToken is a Querydsl query type for LoginToken
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLoginToken extends EntityPathBase<LoginToken> {

    private static final long serialVersionUID = 1451865843L;

    public static final QLoginToken loginToken = new QLoginToken("loginToken");

    public final StringPath email = createString("email");

    public final DateTimePath<java.util.Date> last_used = createDateTime("last_used", java.util.Date.class);

    public final StringPath series = createString("series");

    public final StringPath token = createString("token");

    public QLoginToken(String variable) {
        super(LoginToken.class, forVariable(variable));
    }

    public QLoginToken(Path<? extends LoginToken> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLoginToken(PathMetadata metadata) {
        super(LoginToken.class, metadata);
    }

}

