package org.delivery.db.acoount;


import lombok.Data;
import lombok.EqualsAndHashCode;

import lombok.experimental.SuperBuilder;
import org.delivery.db.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@SuperBuilder // 부모 변수도 빌더에 포함
@Data
@EqualsAndHashCode(callSuper = true) // 부모 값까지 같이 포함
@Entity
@Table(name = "account")

public class AccountEntity extends BaseEntity {

}
