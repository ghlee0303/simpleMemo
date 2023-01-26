package smm.simpleMemo.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTempMemo is a Querydsl query type for TempMemo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTempMemo extends EntityPathBase<TempMemo> {

    private static final long serialVersionUID = 692044497L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTempMemo tempMemo = new QTempMemo("tempMemo");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> del_date = _super.del_date;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QMemo memo;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> reg_date = _super.reg_date;

    public final StringPath text = createString("text");

    public final StringPath title = createString("title");

    public QTempMemo(String variable) {
        this(TempMemo.class, forVariable(variable), INITS);
    }

    public QTempMemo(Path<? extends TempMemo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTempMemo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTempMemo(PathMetadata metadata, PathInits inits) {
        this(TempMemo.class, metadata, inits);
    }

    public QTempMemo(Class<? extends TempMemo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.memo = inits.isInitialized("memo") ? new QMemo(forProperty("memo"), inits.get("memo")) : null;
    }

}

