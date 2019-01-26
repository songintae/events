package events.account.domain;

import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@EqualsAndHashCode(of = "id")
@Entity
public class Account {
    @Id @GeneratedValue
    private Long id;
}
