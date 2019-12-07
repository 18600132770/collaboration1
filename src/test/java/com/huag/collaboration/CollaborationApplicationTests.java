package com.huag.collaboration;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huag.collaboration.entities.ProfessionalProfile;
import com.huag.collaboration.entities.Project;
import com.huag.collaboration.entities.TaskAssignment;
import com.huag.collaboration.entities.User;
import com.huag.collaboration.entities.mapping.UserProjectMapping;
import com.huag.collaboration.mapper.*;
import com.huag.collaboration.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CollaborationApplicationTests {

    @Autowired
    ProfessionProjectMapper professionProjectMapper;

    @Autowired
    ProjectMapper projectMapper;

    @Autowired
    UserProjectMapper userProjectMapper;

    @Autowired
    TaskAssignmentMapper taskAssignmentMapper;

    @Autowired
    ProfessionalProfileMapper professionalProfileMapper;

    @Autowired
    UserMapper userMapper;

    @Test
    public void test1() {
        List<Project> projectList = projectMapper.findAll();
        projectList.forEach(project -> System.out.println(project));
    }

    @Test
    public void test2() {
        Project project = projectMapper.findById(1);
        System.out.println(project);
    }

    @Test
    public void test3(){
        List<Project> projectList = projectMapper.findProjectByDepartmentId(1);
        projectList.forEach(project -> System.out.println(project));
    }

    @Test
    public void test4(){
        List<Project> projectList = projectMapper.findByProjectNameAndDeptId("马", 1);
        for (Project project : projectList) {
            System.out.println(project);
        }
    }

    @Test
    public void test5(){
        List<UserProjectMapping> userProjectMapperAll = userProjectMapper.findUserProjectByProjectNameAndDeptId("马", 1);
        userProjectMapperAll.forEach(userProjectMapping -> System.out.println(userProjectMapping));
    }




    @Test
    public void test7(){
        List<UserProjectMapping> userProjects = userProjectMapper.findUserProjectByProjectNameAndDeptId("", Integer.valueOf(1));
        userProjects.forEach(project ->{
            if(project.getStopTime() != null && project.getStartTime() != null){
                Long dateDifferenceByDay = DateUtils.getDateDifferenceByDay(project.getStopTime(), project.getStartTime());
                project.setLeftTime(dateDifferenceByDay + "");
            }
            System.out.println(project);
        });

        //统计，将相同用户名的放在一起
        List<UserProjectMapping> userProjectMappingList = new ArrayList<>();
        userProjects.forEach(project ->{
            if(userProjectMappingList == null || userProjectMappingList.size() == 0
                    || !userProjectMappingList.get(userProjectMappingList.size() - 1).getUsername().equals(project.getUsername())
            ){
                UserProjectMapping userProjectMapper = new UserProjectMapping();
                userProjectMapper.setUsername(project.getUsername());
                userProjectMapper.setTripState(project.getTripState());
                if("完成".equals(project.getState())){
                    userProjectMapper.setFinishedWorkload(userProjectMapper.getFinishedWorkload() == null?1:(userProjectMapper.getFinishedWorkload()+1));
                }else{
                    userProjectMapper.setCurrentWorkload(userProjectMapper.getCurrentWorkload() == null?1:(userProjectMapper.getCurrentWorkload()+1));
                }
                userProjectMapper.setProjectName(project.getProjectName());
                userProjectMapper.setDesignPhase(project.getDesignPhase());
                userProjectMapper.setTaskLevel(project.getTaskLevel());
                userProjectMapper.setStartTime(project.getStartTime());
                userProjectMapper.setStopTime(project.getStopTime());
                userProjectMapper.setLeftTime(project.getLeftTime());
                userProjectMapper.setCurrentProcess(project.getCurrentProcess());
                userProjectMapper.setRole(project.getRole());
                userProjectMapper.setChildren(Arrays.asList(project));
                userProjectMappingList.add(userProjectMapper);
            }else{
                System.out.print("发现重名数据：");
                System.out.println(project);
                UserProjectMapping userProjectMapping = userProjectMappingList.get(userProjectMappingList.size() - 1);
                List<UserProjectMapping> children = new ArrayList<>();
                children.addAll(userProjectMapping.getChildren());
                children.add(project);
                userProjectMapping.setChildren(children);
                if("完成".equals(project.getState())){
                    userProjectMapping.setFinishedWorkload(userProjectMapping.getFinishedWorkload() == null?1:(userProjectMapping.getFinishedWorkload()+1));
                }else{
                    userProjectMapping.setCurrentWorkload(userProjectMapping.getCurrentWorkload() == null?1:(userProjectMapping.getCurrentWorkload()+1));
                }
                userProjectMappingList.set(userProjectMappingList.size() - 1, userProjectMapping);
            }
        });

        userProjectMappingList.forEach(project -> {
            System.out.println(project);
        });


    }



    @Test
    public void test8(){
        String str = "2019-10-07T16:00:00.000Z";
        //yyyy-MM-dd'T'HH:mm:ss.SSS Z
        str = str.replace("Z", " UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parse = df.parse(str);
            String format = df2.format(parse);
            System.out.println(format);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void test9(){
        Long currentTimeMillis = System.currentTimeMillis();
        int i = currentTimeMillis.intValue();
        int abs = Math.abs(i);
        System.out.println(i);
        System.out.println(abs);
    }

    @Test
    public void test10(){
        List<Project> projectList = projectMapper.findProjectsByProjectSummaryId(1);
        projectList.forEach(project -> {
            System.out.println(project);
        });
    }


    @Test
    public void test11(){
        List<TaskAssignment> taskAssignmentList = taskAssignmentMapper.findByProjectId(16);
        taskAssignmentList.forEach(taskAssignment -> {
            System.out.println(taskAssignment);
        });
    }

    @Test
    public void test12(){
        String usernameArray = "卢俊义, 史进, 吴用, 呼延灼, 宋江, 李应, 李逵, 杨志,";
        if(StringUtils.isNotBlank(usernameArray) && usernameArray.endsWith(",")){
            usernameArray = usernameArray.substring(0, usernameArray.length() - 1);
        }
        System.out.println(usernameArray);
    }

    /**
     * 读取properties配置文件key value
     * @throws Exception
     */
    @Test
    public void test13() throws Exception{
        String endpoint = PropertiesLoaderUtils.loadAllProperties("application.properties").getProperty("oss.endpoint");
        System.out.println(endpoint);
    }

    @Test
    public void test14(){
        List<ProfessionalProfile> myReceivedPreviousProfile = professionalProfileMapper.findMyReceivedPreviousProfile(Integer.valueOf(39));
        System.out.println(myReceivedPreviousProfile);
    }

    @Test
    public void test15(){
        List<Project> projectList = projectMapper.findProjectByDepartmentId(1);
        //传入要连续显示多少页
        PageInfo<Project> info = new PageInfo<>(projectList, 2);

        System.out.println("当前页码："+info.getPageNum());
        System.out.println("总记录数："+info.getTotal());
        System.out.println("每页的记录数："+info.getPageSize());
        System.out.println("总页码："+info.getPages());
        System.out.println("是否第一页："+info.isIsFirstPage());
        System.out.println("连续显示的页码：");
        int[] nums = info.getNavigatepageNums();
        for (int i = 0; i < nums.length; i++) {
            System.out.println(nums[i]);
        }

        projectList.forEach(project -> {
            System.out.println(project);
        });

    }


    @Test
    public void test16(){
        Page<Object> page = PageHelper.startPage(2, 5);
        List<User> userList = userMapper.findAll();
        PageInfo<User> info = new PageInfo<>(userList, 5);

        userList.forEach(user -> {
            System.out.println(user);
        });

        System.out.println("当前页码："+info.getPageNum());
        System.out.println("总记录数："+info.getTotal());
        System.out.println("每页的记录数："+info.getPageSize());
        System.out.println("总页码："+info.getPages());
        System.out.println("是否第一页："+info.isIsFirstPage());
        System.out.println("连续显示的页码：");

    }

    @Test
    public void test17(){
        long round = Math.round(1.0 / 7 * 100);
        System.out.println(round);
    }

}
