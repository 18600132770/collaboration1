package com.huag.collaboration.controller;

import com.huag.collaboration.entities.Project;
import com.huag.collaboration.entities.mapping.UserProjectMapping;
import com.huag.collaboration.entities.query.BaseResponse;
import com.huag.collaboration.mapper.UserProjectMapper;
import com.huag.collaboration.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.*;

/**
 * @author huag
 * @date 2019/10/16 22:46
 */
@Controller
public class UserProjectController {

    @Autowired
    UserProjectMapper userProjectMapper;

    /**
     * 页面跳转
     * @param model
     * @return
     */
    @GetMapping("/userProjects")
    public String  list(Model model){
        return "userProject/list";
    }


    /**
     * ajax请求，查询所有数据
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/userProject/findUserProjectByProjectNameAndDeptId")
    public BaseResponse<List<UserProjectMapping>> findUserProjectByProjectNameAndDeptId(HttpServletRequest request) throws Exception{
        BaseResponse<List<UserProjectMapping>> result = new BaseResponse<>();

        String searchKey = String.valueOf(request.getParameter("searchKey"));
        searchKey = URLDecoder.decode(searchKey, "UTF-8");
        String departmentId = String.valueOf(request.getParameter("departmentId"));

        System.out.println(searchKey);

        List<UserProjectMapping> userProjects = userProjectMapper.findUserProjectByProjectNameAndDeptId(searchKey, Integer.valueOf(departmentId));
        final int index = Math.abs(Long.valueOf(System.currentTimeMillis()).intValue());

        for(int i = 0; i < userProjects.size(); i++){
            userProjects.get(i).setId(index - i);
        }

        userProjects.forEach(project ->{
            if(project.getStopTime() != null && project.getStartTime() != null){
                Long dateDifferenceByDay = DateUtils.getDateDifferenceByDay(project.getStopTime(), project.getStartTime());
                project.setLeftTime(dateDifferenceByDay + "");
            }
            if(StringUtils.isNotBlank(project.getRole()) && "principal".equals(project.getRole())){
                project.setRole("负责人");
            }
            if(StringUtils.isNotBlank(project.getRole()) && "chiefEngineer".equals(project.getRole())){
                project.setRole("总工");
            }
        });

        //统计，将相同用户名的放在一起，userProjects已经是按照username排序后的数据
        List<UserProjectMapping> userProjectMappingList = new ArrayList<>();
        userProjects.forEach(project ->{
            if(userProjectMappingList == null || userProjectMappingList.size() == 0
                    || !userProjectMappingList.get(userProjectMappingList.size() - 1).getUsername().equals(project.getUsername())
            ){
                UserProjectMapping userProjectMapper = new UserProjectMapping();
                userProjectMapper.setId(Integer.MAX_VALUE-project.getId());
                userProjectMapper.setHidden(true);
                userProjectMapper.setUsername(project.getUsername());
                userProjectMapper.setTripState(project.getTripState());
                userProjectMapper.setHasChildren(true);
                if("完成".equals(project.getState())){
                    userProjectMapper.setFinishedWorkload(userProjectMapper.getFinishedWorkload() == null?1:(userProjectMapper.getFinishedWorkload()+1));
                }else{
                    userProjectMapper.setCurrentWorkload(userProjectMapper.getCurrentWorkload() == null?1:(userProjectMapper.getCurrentWorkload()+1));
                }
                userProjectMapper.setChildren(Arrays.asList(project));
                userProjectMappingList.add(userProjectMapper);
            }else{
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



        System.out.println(userProjectMappingList);
        result.code = 200;
        result.setData(userProjectMappingList);
        return result;
    }

    /**
     * ajax请求，查询所有数据
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/userProject/findUserProjectsByDeptId")
    public BaseResponse<List<UserProjectMapping>> findUserProjectsByDeptId(HttpServletRequest request) throws Exception {
        BaseResponse<List<UserProjectMapping>> result = new BaseResponse<>();
        String departmentId = String.valueOf(request.getParameter("departmentId"));

        System.out.println("departmentId: " + departmentId);

        List<String> usernameList = new ArrayList<>();
        Set<String> set = new HashSet<>();

        List<UserProjectMapping> userProjects = userProjectMapper.findUserProjectByProjectNameAndDeptId("", Integer.valueOf(departmentId));
        userProjects.forEach(project ->{
            if(project.getStopTime() != null && project.getStartTime() != null){
                Long dateDifferenceByDay = DateUtils.getDateDifferenceByDay(project.getStopTime(), project.getStartTime());
                project.setLeftTime(dateDifferenceByDay + "");
            }
            if(StringUtils.isNotBlank(project.getRole()) && "principal".equals(project.getRole())){
                project.setRole("负责人");
            }
            if(StringUtils.isNotBlank(project.getRole()) && "chiefEngineer".equals(project.getRole())){
                project.setRole("总工");
            }
            set.add(project.getUsername());
        });

        usernameList.addAll(set);

        String[] usernameArray = new String[usernameList.size()];
        usernameList.toArray(usernameArray);

        List<UserProjectMapper> userWithoutProjectsWhereUsersNotIn = userProjectMapper.findUserWithoutProjectsWhereUsersNotIn(Integer.valueOf(departmentId), usernameArray);
        userProjects.addAll((Collection)userWithoutProjectsWhereUsersNotIn);

        System.out.println(departmentId);
        result.setData(userProjects);
        result.code = 200;

        return result;

    }



}
