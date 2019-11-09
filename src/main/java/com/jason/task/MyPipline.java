package com.jason.task;

import com.google.gson.Gson;
import com.jason.Service.JobService;
import com.jason.model.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@Component
public class MyPipline implements Pipeline {
    @Autowired
    @Qualifier("jobService")
    JobService jobService;
    public void process(ResultItems resultItems, Task task) {
        Job job = resultItems.get("job");
        Gson gson=new Gson();
        String s = gson.toJson(job);

        System.out.println(s);
        if(null!=job) {
            jobService.save(job);
        }

    }
}
