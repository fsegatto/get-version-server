SELECT app_id     ,
       app_name   ,
       file_name  ,
       replace_now
  FROM sctb012_app
 WHERE app_id = ?