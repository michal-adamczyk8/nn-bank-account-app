package pl.nn.bankaccount.common.persistence;

import static jakarta.persistence.AccessType.FIELD;
import static pl.nn.bankaccount.BankAccountApplication.DEFAULT_ZONE_OFFSET;

import jakarta.persistence.Access;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@MappedSuperclass
@Access(FIELD)
public abstract class BaseEntity {
    @Id
    private UUID id = UUID.randomUUID();

    @Version
    private Integer version;

    protected OffsetDateTime createdDateTime = OffsetDateTime.now(DEFAULT_ZONE_OFFSET);

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BaseEntity that = (BaseEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
