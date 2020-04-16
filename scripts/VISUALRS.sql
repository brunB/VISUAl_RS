-- TABLE SPACE POUR LE NOUVEAU USER
--drop tablespace tbs_visualrs INCLUDING CONTENTS AND DATAFILES;
CREATE TABLESPACE tbs_visualrs datafile 'D:\app\neo\oradata\VISUALRS\tbs_visualrs.dbf' size 80M AUTOEXTEND ON NEXT 10M MAXSIZE UNLIMITED EXTENT MANAGEMENT LOCAL AUTOALLOCATE SEGMENT SPACE MANAGEMENT AUTO;


grant connect,resource to DX ;

-- CREATION D'UN USER
CREATE USER DX 
    IDENTIFIED BY VisualRS250 
    DEFAULT TABLESPACE tbs_visualrs
    QUOTA 10M ON tbs_visualrs 
    TEMPORARY TABLESPACE temp
    PASSWORD EXPIRE;
    
-- STATS sur les tables du DX
EXECUTE DBMS_STATS.GATHER_SCHEMA_STATS(ownname => 'DX');
