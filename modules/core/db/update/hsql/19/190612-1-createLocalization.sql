create table DDCDL_LOCALIZATION (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    PACK varchar(4000),
    KEY_ varchar(4000) not null,
    LOCALE varchar(255) not null,
    VALUE_ longvarchar,
    --
    primary key (ID)
);