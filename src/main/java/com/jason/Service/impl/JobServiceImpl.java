package com.jason.Service.impl;

import com.jason.Service.JobService;
import com.jason.dao.JobRepository;
import com.jason.model.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "jobService")
@Transactional
public class JobServiceImpl implements JobService {
    @Autowired
    JobRepository jobRepository;
    @Override
    public void save(Job job) {
        jobRepository.save(job);
    }
}
