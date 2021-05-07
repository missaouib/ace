SELECT pc.RN,
       pc.POL_CASE_ID,
       pc.IND_ACTIVITY_RN,
       pc.TXF_STATUS,
       pd.SEQ_NO_PPTY_DTL_DISPLAY                                          AS seq,
       pd.PPTY_DTL_ID,
       DECODE(pc.IND_CASE_STATUS, '1', '1=>Closed', '2', '2=>Ready to be consolidated', '7',
              '7=>Ready to be transferred', '3', '3=>Consolidated', '8', '8=>Transferred', '4', '4=>**NULL**', '5',
              '5=>Open', '6', '6=>Open-Inactive', pc.IND_CASE_STATUS)      AS ind_case_status,
       DECODE(pd.IND_TRANSFER, 'N', 'N: New', 'T', 'T: transferred', 'A',
              'A: pending to activity RN (pending out) / in ARN use', 'O', 'O: pending transfer out / use in tran,cons',
              'U', 'U: transferring out', 'G', 'G: transferring In', 'I', 'I: pending transferring In', 'R',
              'R: in activity RN', pd.IND_TRANSFER)                        AS ind_transfer,
       DECODE(pd.IND_PPTY_STATUS, 'A', 'A: Confirm Receipt by Property Office', 'D', 'D: Wrong Entry', 'N',
              'N: New create', 'U', 'To be verified', pd.IND_PPTY_STATUS)  AS ind_ppty_status,
       DECODE(pd.PPTY_POSSESSED_BY, 'P', 'P:kept by property office', 'T', 'T: kept by team', 'D', 'D: kept by DO', 'O',
              'O: kept by outside', pd.PPTY_POSSESSED_BY)                  AS ppty_possessed_by,
       DECODE(pd.IND_DISPOSAL, 'C', 'C: Completely Disposed', 'I', 'I: Instructed for Disposal', 'J',
              'J: Partially disposed with prepared items', 'N', 'N: Not Disposed', 'O',
              'O: Prepared for Disposal for CISv60 data', 'P', 'P: Partially Disposed without prepared items', 'R',
              'R: Disposal Prepared', 'V', 'V: Verified', pd.IND_DISPOSAL) AS ind_disposal,
       pd.IND_AMEND,
       pd.PPTY_DISPOSAL_VERIFICATN_ID,
       pd.IND_ON_HAND,
       pd.IND_DESTINATN,
       pd.IND_CANCEL_MATCH,
       pd.IND_PRIS_PPTY_AMEND,
       pd.IND_MATCHING,
       pd.IND_CONSENT_BY,
       pc.CODE_POL_FORMATN,
       pd.FORMATION_OF_STORAGE,
       pd.CODE_STORAGE,
       pd.version
FROM POLICE_CASE pc,
     PPTY_DTL pd
WHERE pc.POL_CASE_ID = pd.POL_CASE_ID
  AND pc.RN = UPPER(TRIM('RN_XXX'))
ORDER BY pc.rn, pd.SEQ_NO_PPTY_DTL_DISPLAY;

