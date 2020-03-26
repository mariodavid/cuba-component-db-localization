-- begin DDCDL_LOCALIZATION
create table DDCDL_LOCALIZATION (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    KEY_GROUP varchar(255),
    KEY_ varchar(4000) not null,
    LOCALE varchar(255) not null,
    VALUE_ longtext,
    --
    primary key (ID)
)^
-- end DDCDL_LOCALIZATION
