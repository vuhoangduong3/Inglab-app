package unicorns.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import unicorns.backend.util.Schema;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(schema = Schema.BACKEND_APP)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TokenBlacklist extends BaseEntity {
    private String token;
    private long expiryDate;
}