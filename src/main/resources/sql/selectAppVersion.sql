SELECT t1.app_version_id,
       t1.app_id        ,
       t2.app_name      ,
	   t1.version_number,
	   t1.version_date  ,
	   t1.version_path
  FROM sctb013_app_version t1,
       sctb012_app         t2
 WHERE t1.app_version_id = ?
   AND t1.app_id         = t2.app_id