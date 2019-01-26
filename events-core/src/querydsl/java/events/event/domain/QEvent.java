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

    public QEvent(String variable) {
        super(Event.class, forVariable(variable));
    }

    public QEvent(Path<? extends Event> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEvent(PathMetadata metadata) {
        super(Event.class, metadata);
    }

}

