package com.huag.collaboration.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huag.collaboration.entities.Project;
import com.huag.collaboration.entities.TaskAssignment;
import com.huag.collaboration.entities.base.PageBaseResponse;
import com.huag.collaboration.entities.mapping.UserProjectMapping;
import com.huag.collaboration.entities.query.BaseResponse;
import com.huag.collaboration.mapper.UserProjectMapper;
import com.huag.collaboration.utils.DateUtils;
import com.huag.collaboration.utils.RequestUtils;
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
    public BaseResponse<Object> findUserProjectByProjectNameAndDeptId(HttpServletRequest request) throws Exception{
        BaseResponse<Object> result = new BaseResponse<>();

        String searchKey = String.valueOf(request.getParameter("searchKey"));
        searchKey = URLDecoder.decode(searchKey, "UTF-8");
        String departmentId = String.valueOf(request.getParameter("departmentId"));

        int currentPage = RequestUtils.getCurrentPage(request);
        int pageSize = RequestUtils.getPageSize(request);

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

        userProjectMappingList.forEach(userProjectMapping -> {
            int finishedProcess = 0;
            List<UserProjectMapping> children = userProjectMapping.getChildren();
            if(children != null && children.size() > 0){
                for(int i = 0; i < children.size(); i++){
                    Integer currentProcess = children.get(i).getCurrentProcess();
                    finishedProcess += currentProcess;
                    UserProjectMapping child = children.get(i);
                    child.setReverseHidden(true);
                    children.set(i, child);
                }
                userProjectMapping.setFinishedWorkload(finishedProcess/children.size());
            }else{
                userProjectMapping.setFinishedWorkload(0);
            }
        });


        long total = userProjectMappingList.size();
        List<UserProjectMapping> list =  userProjectMappingList.subList((currentPage-1)*pageSize, currentPage*pageSize > userProjectMappingList.size()?userProjectMappingList.size():currentPage*pageSize);

//        PageBaseResponse response = new PageBaseResponse(list, currentPage, pageSize, pages, total);
        PageBaseResponse response = new PageBaseResponse(list, currentPage, pageSize, pageSize, total);

        result.code = 200;
        result.setData(response);
        return result;
    }


    /**
     * 根据部门id和分工角色查询可用员工
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/userProject/findUserProjectsByDeptIdAndRole")
    public BaseResponse<Object> findUserProjectsByDeptIdAndRole(HttpServletRequest request) throws Exception {
        BaseResponse<Object> result = new BaseResponse<>();
        int currentPage = RequestUtils.getCurrentPage(request);
        int pageSize = RequestUtils.getPageSize(request);

        String departmentId = String.valueOf(request.getParameter("departmentId"));
        String selectUserRole = String.valueOf(request.getParameter("selectUserRole"));

        List<String> usernameList = new ArrayList<>();
        Set<String> set = new HashSet<>();

        List<UserProjectMapping> userProjects = userProjectMapper.findUserProjectsByDeptIdAndRole(Integer.valueOf(departmentId), selectUserRole);

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
        List<UserProjectMapping> userWithoutProjectsWhereUsersNotIn = userProjectMapper.findUserProjectsByDeptIdAndRoleWhereUsersNotIn(Integer.valueOf(departmentId), usernameArray, selectUserRole);
        userProjects.addAll((Collection)userWithoutProjectsWhereUsersNotIn);

        long total = userProjects.size();
        List<UserProjectMapping> list =  userProjects.subList((currentPage-1)*pageSize, currentPage*pageSize > userProjects.size()?userProjects.size():currentPage*pageSize);

        PageBaseResponse response = new PageBaseResponse(list, currentPage, pageSize, pageSize, total);

        result.code = 200;
        result.setData(response);
        return result;

    }



}
