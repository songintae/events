package events.common;

import events.account.domain.Account;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {
    @CreatedBy
    @OneToOne
    protected Account createdBy;
    @CreatedDate
    protected LocalDateTime createDate;
    @LastModifiedBy
    @OneToOne
    protected Account lastModifiedBy;
    @LastModifiedDate
    protected LocalDateTime lastModifiedDate;
    @Column(nullable = false)
    protected Boolean deleted = false;

    protected void delete() {
        deleted = true;
    }
}
