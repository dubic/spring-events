package com.dubic.springevents.relational;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("PERSON")
public record Person(@Id int id, String name, int age) {
}
