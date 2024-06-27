package com.dubic.springevents.relational;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("EMP")
public record Employment(@Id @Column("PERSON_ID") int personId, String company, @Version int version) {

}
