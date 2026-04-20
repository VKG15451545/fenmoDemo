package com.example.fenmoDemo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnumUserType {
    ADMIN(1L,"Admin"),STUDENT(2L,"student"),TEACHER(3L,"teacher"),SCHOOL_MANAGER(4L,"schoolManager");
    private final Long id;
    private final String name;

    public static EnumUserType getEnumUserTypeById(Long id){
        for(EnumUserType enumUserType: EnumUserType.values()){
            if(enumUserType.getId().equals(id)){
                return enumUserType;
            }
        }
        return null;
    }
}
