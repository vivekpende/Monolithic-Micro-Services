package com.softcell.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softcell.model.Quiz;

@Repository
public interface QuizDao extends JpaRepository<Quiz, Integer>{

}
