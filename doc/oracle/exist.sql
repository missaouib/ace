SELECT *
FROM POLICE_CASE
WHERE exists(SELECT * FROM POLICE_CASE WHERE cn IS NOT NULL)
  AND rownum < 20;

