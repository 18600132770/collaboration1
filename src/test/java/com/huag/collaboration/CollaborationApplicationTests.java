package com.huag.collaboration;

import com.huag.collaboration.entities.ProfessionProject;
import com.huag.collaboration.entities.Project;
import com.huag.collaboration.mapper.ProfessionProjectMapper;
import com.huag.collaboration.mapper.ProjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CollaborationApplicationTests {

    @Autowired
    ProfessionProjectMapper professionProjectMapper;

    @Autowired
    ProjectMapper projectMapper;

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



}
