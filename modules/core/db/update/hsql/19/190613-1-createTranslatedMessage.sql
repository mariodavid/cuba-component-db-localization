create table DDCDL_TRANSLATED_MESSAGE (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    LOCALE varchar(255),
    VALUE_ longvarchar,
    LOCALIZATION_ID varchar(36) not null,
    --
    primary key (ID)
);