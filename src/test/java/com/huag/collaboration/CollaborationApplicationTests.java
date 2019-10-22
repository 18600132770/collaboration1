package com.huag.collaboration;

import com.huag.collaboration.entities.Project;
import com.huag.collaboration.entities.mapping.UserProjectMapping;
import com.huag.collaboration.mapper.ProfessionProjectMapper;
import com.huag.collaboration.mapper.ProjectMapper;
import com.huag.collaboration.mapper.UserProjectMapper;
import com.huag.collaboration.utils.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CollaborationApplicationTests {

    @Autowired
    ProfessionProjectMapper professionProjectMapper;

    @Autowired
    ProjectMapper projectMapper;

    @Autowired
    UserProjectMapper userProjectMapper;

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


}
