package com.xtrasoft.collegeserver.repository;

import com.xtrasoft.collegeserver.models.Teacher;
import com.xtrasoft.collegeserver.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * by xtr@soft  on 23/10/2020
 *
 * @author Landry
 **/
@Repository
public interface TeacherRepository  extends JpaRepository<Teacher,Long> {

    Teacher findByUser(User user);

    @Query("FROM Teacher AS t LEFT JOIN t.user AS u WHERE u.userName = ?1")
    Teacher findByUserName(String username);
}
