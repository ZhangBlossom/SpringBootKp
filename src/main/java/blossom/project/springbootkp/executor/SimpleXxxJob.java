//package blossom.project.springbootkp.executor;
//
//import cn.hutool.extra.mail.MailException;
//import com.towelove.common.core.constant.SecurityConstants;
//import com.towelove.common.core.domain.R;
//import com.towelove.common.core.utils.bean.BeanUtils;
//import com.towelove.msg.task.domain.MailMsg;
//import com.towelove.msg.task.domain.MsgTask;
//import com.towelove.msg.task.mq.producer.MailMessageProducer;
//import com.towelove.msg.task.mq.producer.SysMessageProducer;
//import com.towelove.msg.task.service.MsgTaskService;
//import com.towelove.system.api.RemoteSysMailAccountService;
//import com.towelove.system.api.RemoteSysUserService;
//import com.towelove.system.api.model.LoginUser;
//import com.towelove.system.api.model.MailAccountRespVO;
//import com.xxl.job.core.context.XxlJobHelper;
//import com.xxl.job.core.handler.annotation.XxlJob;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Objects;
//
///**
// * @author 季台星
// * @version 1.0
// */
//@Component
//public class SimpleXxxJob {
//
//
//    private static final Logger logger = LoggerFactory.getLogger(SimpleXxxJob.class);
//    //使用XXLJOB注解定义一个job
//    @XxlJob(value = "myJobHander", init = "initHandler", destroy = "destroyHandler")
//    public void myJobHander() {
//        //做查询数据库操作
//        //使用远程调用方法
//        try {
//            if (Objects.isNull(userResult)) {
//                //自定义返回给调度中心的失败原因
//                XxlJobHelper.handleFail("任务执行失败，请检查用户模块服务器");
//            //}
//            System.out.println(userResult.getData().getSysUser().getUserName());
//            //这个方法底层是给HandlerContext设置返回值以及消息
//            XxlJobHelper.handleSuccess("任务执行成功");
//        } catch (Exception e) {
//            //在控制台终止程序的时候是使用InterruptException异常来停止，我们需要对这个异常进行抛出，否则无法停止任务
//            if (e instanceof InterruptedException) {
//                throw e;
//            }
//            logger.error("{}", e);
//        }
//    }
//
//    //任务初始化方法
//    public void initHandler() {
//        logger.info("task init ...");
//        System.out.println("任务调用初始化方法执行");
//    }
//
//    public void destroyHandler() {
//        System.out.println("任务执行器被销毁");
//    }
//
//}
