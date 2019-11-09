package com.jason.service;

import com.google.gson.Gson;
import com.jason.Service.JobService;
import com.jason.model.Job;
import com.jason.task.MyProcess;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApp {

    @Autowired
    MyProcess  myProcess;
    @Test
    public void testTask(){

        myProcess.task();
    }

    @Autowired
    JobService jobService;

    @Test
    public void testSave(){
        String jsonStr="{\n" +
                "\t\"jobTitle\": \"Java开发工程师\",\n" +
                "\t\"company\": \"巧工匠（深圳）信息技术服务有限公司\",\n" +
                "\t\"types\": [\"深圳-南山区  \", \"  无工作经验  \", \"  大专  \", \"  招1人  \", \"  11-08发布\"],\n" +
                "\t\"fares\": [\"五险一金\", \"员工旅游\", \"餐饮补贴\", \"绩效奖金\", \"年终奖金\", \"弹性工作\", \"定期体检\"],\n" +
                "\t\"jobdesc\": \"1、根据设计文档或需求说明完成代码编写、调试、测试和维护；2、辅助进行系统的功能模块设计，编写API接口文档；3、协助测试工程师进行系统测试，处理测试发现的问题；4、配合领导完成相关开发工作；5、主要参与公司网页项目开发。任职资格：1、大专及以上学历，优秀者不限专业；\",\n" +
                "\t\"jobaddress\": \"南山区科苑路15号科兴科学园B座1单元706\",\n" +
                "\t\"companydesc\": \"巧工匠（深圳）信息技术服务有限公司是一家以通讯、软件开发为核心，专注于软件新技术研发、联络中心和融合通信产品研发、工业物联网研发的高科技企业。公司原始团队均源自国内外知名企业技术骨干，自成立以来，始终坚持以技术创新为原动力，以个性化的联络中心定制方案提高客户之间的沟通效率，通过帮助客户的成功促进公司快速成长与发展。公司一直紧跟技术发展方向，在产品第三方开发接口、联络中心集群管理与云语音通信能力等技术领域、物联网等领域均有不俗技术实力。依托精准的市场定位、优质的产品、全方位的服务及快速增长的市场需求，巧工匠信息完成了超过100%的复合增长率，迅速走出了公司创业初期的困境，步入了快速发展的轨道。\"\n" +
                "}";
        Gson gson = new Gson();
        Job job = gson.fromJson(jsonStr, Job.class);

        jobService.save(job);
    }




}
