package events.event.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEvent is a Querydsl query type for Event
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QEvent extends EntityPathBase<Event> {

    private static final long serialVersionUID = 263447193L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEvent event = new QEvent("event");

    public final SetPath<Attendance, QAttendance> attendances = this.<Attendance, QAttendance>createSet("attendances", Attendance.class, QAttendance.class, PathInits.DIRECT2);

    public final NumberPath<Integer> availAbleParticipant = createNumber("availAbleParticipant", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> beginEnrollmentDateTime = createDateTime("beginEnrollmentDateTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> beginEventDateTime = createDateTime("beginEventDateTime", java.time.LocalDateTime.class);

    public final StringPath contents = createString("contents");

    public final DateTimePath<java.time.LocalDateTime> endEnrollmentDateTime = createDateTime("endEnrollmentDateTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> endEventDateTime = createDateTime("endEventDateTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath location = createString("location");

    public final StringPath name = createString("name");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final events.account.domain.QAccount register;

    public QEvent(String variable) {
        this(Event.class, forVariable(variable), INITS);
    }

    public QEvent(Path<? extends Event> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEvent(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEvent(PathMetadata metadata, PathInits inits) {
        this(Event.class, metadata, inits);
    }

    public QEvent(Class<? extends Event> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.register = inits.isInitialized("register") ? new events.account.domain.QAccount(forProperty("register")) : null;
    }

}

