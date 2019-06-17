-- begin DDCDL_LOCALIZATION
create table DDCDL_LOCALIZATION (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    KEY_GROUP varchar(255),
    KEY_ varchar(4000) not null,
    LOCALE varchar(255) not null,
    VALUE_ text,
    --
    primary key (ID)
)^
-- end DDCDL_LOCALIZATION
