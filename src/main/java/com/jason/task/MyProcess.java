package com.jason.task;

import com.jason.model.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.scheduler.component.HashSetDuplicateRemover;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyProcess implements PageProcessor {
    Site site = Site.me().setCharset("GBK").setRetryTimes(3).setTimeOut(3000);

    public void process(Page page) {
        //解析页面，获取招聘信息详情的url地址
        List<Selectable> list = page.getHtml().css("div#resultList div.el").nodes();
        //判断集合是否为空
        if(list.size()==0){
            //如果为空，表示这是招聘详情页，解析页面，获取招聘详情信息，保存数据
            this.getJobInfo(page);
        }else {
            //如果不为空，表示这是列表页，解析出详情页的url地址，放到任务队列中
            for(Selectable selectable:list){
                String jobInfoUrl = selectable.links().toString();
                //把获取到的详情页的url地址放到任务队列中
                page.addTargetRequest(jobInfoUrl);
            }
            //获取下一页按钮的url
            String bkUrl=page.getHtml().css("div.p_in li.bk").nodes().get(1).links().toString();//get(1)拿到第二个
            //把下一页的url放到任务队列中
            page.addTargetRequest(bkUrl);
        }



    }

    private void getJobInfo(Page page) {
        Html html = page.getHtml();

        String jobUrl=page.getUrl().toString();

        String jobTitle=html.xpath("//div[@class=\"tHeader tHjob\"]//h1/text()").toString();

        String company=html.xpath("//div[@class=\"tHeader tHjob\"]//p[@class=\"cname\"]/a[@class=\"catn\"]/text()").toString();

        String[] split = html.xpath("//div[@class=\"tHeader tHjob\"]//p[@class=\"msg ltype\"]/@title").toString().split("\\|");


        List<String> types= new ArrayList<>();
        for (String s : split) {
            types.add(s.trim());
        }

        System.out.println();
        List<String> fares = html.xpath("//div[@class=\"tHeader tHjob\"]//div[@class=\"jtag\"]//span/text()").all();

        System.out.println();
        List<String> all = html.xpath("//div[@class=\"bmsg job_msg inbox\"]/p/text()").all();
        StringBuffer sb=new StringBuffer("");
        for (String s : all) {
            sb.append(s);
        }

        String jobdesc=sb.toString();

        String jobaddress=html.xpath("//div[@class=\"bmsg inbox\"]//p/text()").toString();

        String  companydesc=html.xpath("//div[@class=\"tmsg inbox\"]/text()").toString();

        Job job = new Job();
        job.setJobUrl(jobUrl);
        job.setJobTitle(jobTitle);
        job.setCompany(company);
        job.setTypes(types);
        job.setFares(fares);
        job.setJobaddress(jobaddress);
        job.setCompanydesc(companydesc);
        job.setJobdesc(jobdesc);

        System.out.println(job.getJobUrl());

        if(jobUrl!=null&&jobTitle!=null){
            page.getResultItems().put("job",job);
        }




    }

    public Site getSite() {
        return site;
    }
    @Autowired
    MyPipline myPipline;

    @Scheduled(initialDelay=500L,fixedDelay=3600000000L)
    public  void task() {
        Spider.create(new MyProcess())
                .addUrl("https://search.51job.com/list/040000,000000,0000,00,9,99,java,2,1.html?lang=c&stype=1&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare=")
                .setScheduler(new QueueScheduler()
                  .setDuplicateRemover(new HashSetDuplicateRemover()))
                .thread(Runtime.getRuntime().availableProcessors())
                .addPipeline(myPipline)
                .run();
    }
}
