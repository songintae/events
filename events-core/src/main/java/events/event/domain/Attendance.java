package events.event.domain;

import events.account.domain.Account;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import javax.persistence.*;

@EqualsAndHashCode(of = "id")
@Entity
public class Attendance {
    @Setter(value = AccessLevel.PACKAGE)
    @Id @GeneratedValue
    private Long id;

    @Setter
    @ManyToOne
    private Event event;

    @Setter
    @ManyToOne
    private Account account;
}
