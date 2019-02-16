package events.event.domain;

import events.account.domain.Account;
import events.common.BaseEntity;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
public class Attendance extends BaseEntity {
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
