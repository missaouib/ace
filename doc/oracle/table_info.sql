--查所有table和col
SELECT  TABLE_NAME, COLUMN_NAME, DATA_TYPE, DATA_LENGTH, DATA_DEFAULT, DATA_PRECISION
FROM ALL_TAB_COLUMNS
WHERE 1 = 1
  AND owner = 'CIS2'
  --and COLUMN_NAME = 'CODE_POL_FORMATN'
  AND TABLE_NAME LIKE 'PPTY_INSPECTN';


select * from all_indexes where table_name = 'PPTY_INSPECTN';