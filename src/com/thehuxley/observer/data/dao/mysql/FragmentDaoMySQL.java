package com.thehuxley.observer.data.dao.mysql;

import com.thehuxley.observer.data.dao.FragmentDao;


/** 
 * @author Marcio Augusto Guimarães
 * @version 1.0.0
 * @since huxley-avaliador 1.0.0
 */
public class FragmentDaoMySQL extends FragmentDao {

	public FragmentDaoMySQL() {

        CREATE_FRAGMENT = "Insert into fragment (version,number_of_lines,percentage,plagium_id,start_line1,start_line2,fragments_idx) values (1,?,?,?,?,?,?)";


	}
}
