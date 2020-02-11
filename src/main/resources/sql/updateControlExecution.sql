UPDATE sctb011_control_execution
   SET routine_id           = ?,
       routine_situation_id = ?,
       start_time           = ?,
       end_time             = ?,
       description          = ?
 WHERE control_execution_id = ?