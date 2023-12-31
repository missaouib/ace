CREATE UNIQUE INDEX "IDX_VEH_POUND_PARK_SPACE_REQ_2"
    ON "VEH_POUND_PARK_SPACE_REQ" (DECODE("STATUS",'S',"PPTY_DTL_ID",NULL))
    PCTFREE 10 INITRANS 2 MAXTRANS 255
    STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
    PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
    BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
    TABLESPACE "CIS2_IDX02" ;


select VEH_POUND_PARK_REQ_ID, PPTY_DTL_ID , STATUS,
       DECODE("STATUS",'S',"PPTY_DTL_ID",NULL) as pptyid  from VEH_POUND_PARK_SPACE_REQ;

select * from FORMATION_SEQ;