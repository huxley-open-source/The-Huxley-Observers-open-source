package com.thehuxley.observer.data.dao.mysql;
import com.thehuxley.observer.data.dao.CPDQueueDao;

public class CPDQueueDaoMySQL  extends CPDQueueDao {


        public CPDQueueDaoMySQL() {

            LIST_CPDQUEUE = "Select * from cpdqueue";
            DELETE_CPDQUEUE = "Delete from cpdqueue where id = ?";
            DELETE_CPDQUEUE_BY_LANGUAGE_AND_PROBLEM = "Delete from cpdqueue where problem_id = ? and language = ?";
        }

}
