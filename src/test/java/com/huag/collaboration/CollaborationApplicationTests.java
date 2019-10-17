package com.huag.collaboration;

import com.huag.collaboration.entities.ProfessionProject;
import com.huag.collaboration.mapper.ProfessionProjectMapper;
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

    @Test
    public void contextLoads() {
        System.out.println("测试OK");
        List<ProfessionProject> professionProjectList = professionProjectMapper.findByProjectId(1);
        System.out.println(professionProjectList);
    }

}
