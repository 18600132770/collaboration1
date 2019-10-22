package com.huag.collaboration.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * json解析工具
 * @Author huag
 * 2019-07-11
 */
public class JSONUtils implements Serializable {

    private static final long serialVersionUID = 7941940164142488546L;

    /**
     * 获取json字符串中某一个filed字段的数组集合
     * "filterFields": ["lng(lng)","id(id)"]
     * @param jsonString
     * @param field
     * @return
     */
    public static String[] getArrayStringToArray(String jsonString, String field){

        if(StringUtils.isBlank(jsonString)){
            return null;
        }

        JSONObject jsonObject = JSON.parseObject(jsonString);
        if(!jsonObject.containsKey(field)){
            return null;
        }
        String arrayString = jsonObject.get(field).toString();//["lng(lng)","id(id)"]
        String[] array = arrayString.substring(1, arrayString.length()-1).split(",");
        for (int i = 0; i< array.length; i++){
            if(array[i].startsWith("\"")){
                array[i] = array[i].substring(1);
            }
            if(array[i].endsWith("\"")){
                array[i] = array[i].substring(0, array[i].length()-1);
            }
        }
        return  array;
    }

    /**
     * 获取jsonString字符串的field字段嵌套的json数组
     * @param jsonString
     * @param field
     * @return
     */
    public static JSONArray getFieldJSONArray(String jsonString, String field){
        if(StringUtils.isNotBlank(jsonString)){
            String fieldString = JSON.parseObject(jsonString).get(field).toString();
            return new JSONArray(fieldString);
        }else {
            return null;
        }

    }


    /**
     * 根据参数arg4，解析出本节点的数据源文件名集合
     * @param previousCpNodeJsonString    source节点的Map<id, type>集合的json格式
     * @return
     */
    public static List<String> getInputFileNameList(String previousCpNodeJsonString){
        Map<String, String> previousCpNodeMap = JSON.parseObject(previousCpNodeJsonString, Map.class);
        List<String> list = new ArrayList<>();
        if(previousCpNodeMap != null && previousCpNodeMap.size() > 0){
            previousCpNodeMap.entrySet().parallelStream().forEach(previousCpNode ->{
                String id = previousCpNode.getKey();
                String value = (String)previousCpNode.getValue();
                System.out.println("id = " + id);
                list.add(id.split("spark-")[1] + ".csv");
            });
        }
        return list;
    }

    /**
     * 获取本节点的源节点ids
     * @param previousCpNodeJsonString
     * @return
     */
    public static List<String> getPreviousCpNodeIds(String previousCpNodeJsonString){
        Map<String, String> previousCpNodeMap = JSON.parseObject(previousCpNodeJsonString, Map.class);
        List<String> list = new ArrayList<>();
        if(previousCpNodeMap != null && previousCpNodeMap.size() > 0){
            previousCpNodeMap.entrySet().parallelStream().forEach(previousCpNode ->{
                String id = previousCpNode.getKey();
                String value = (String)previousCpNode.getValue();
                System.out.println("id = " + id);
                list.add(id.split("spark-")[1]);
            });
        }
        return list;
    }

    /**
     * 获得jsonObject对象中field字段的字符串数据
     * @param jsonObject
     * @param field
     * @return
     */
    public static String getField(JSONObject jsonObject, String field){
        if(jsonObject != null && jsonObject.containsKey(field)){
            Object o = jsonObject.get(field);
            if(o != null){
                return o.toString();
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    /**
     * 获得jsonString字符串中field字段的字符串数据
     * @param jsonString
     * @param field
     * @return
     */
    public static String getField(String jsonString, String field){
        if(StringUtils.isNotBlank(jsonString)){
            JSONObject jsonObject = JSON.parseObject(jsonString);
            return getField(jsonObject, field);
        }else{
            return null;
        }
    }

    /**
     * 获取hdfsUploadUrl
     * hdfs计算结果保存路径
     * @param jsonString
     * @return
     */
    public static String getHdfsUploadUrl(String jsonString){
        JSONObject jsonObject = JSON.parseObject(jsonString);
        return (String) jsonObject.get("output_path");
    }

    /**
     * 获取hdfsDownloadUrl
     * hdfs数据源路径
     * @param jsonString
     * @return
     */
    public static String getHdfsDownloadUrl(String jsonString){
        JSONObject jsonObject = JSON.parseObject(jsonString);
        return (String) jsonObject.get("input_path");
    }

    /**
     * 获取组件前端名字
     * @param jsonString
     * @return
     */
    public static String getCpNodeName(String jsonString){
        JSONObject jsonObject = JSON.parseObject(jsonString);
        return (String) jsonObject.get("cp_name");
    }

    /**
     * 获取node_id
     * 本节点id
     * @param jsonString
     * @return
     */
    public static String getCpNodeId(String jsonString){
        JSONObject jsonObject = JSON.parseObject(jsonString);
        return ((String) jsonObject.get("node_id")).split("spark-")[1];
    }

    /**
     * 获取batch_id
     * 本次任务批次号
     * @param jsonString
     * @return
     */
    public static String getBatchId(String jsonString){
        JSONObject jsonObject = JSON.parseObject(jsonString);
        return (String) jsonObject.get("batch_id");
    }

    /**
     * 获取DataAnalyseID
     * @param jsonString
     * @return
     */
    public static Integer getDataAnalyseID(String jsonString){
        JSONObject jsonObject = JSON.parseObject(jsonString);
        String input_path = jsonObject.getString("input_path");
        String[] split = input_path.split("/");
        return Integer.valueOf(split[split.length-2]);
    }

    /**
     * 获取isUploadHfs
     * 是否需要输出保存到 HFS
     * @param jsonString
     * @return
     */
    public static String getIsUploadHfs(String jsonString){
        JSONObject jsonObject = JSON.parseObject(jsonString);
        return (String) jsonObject.get("is_output");
    }

    /**
     * 获取json字符串中field数值，没有值则为默认值
     * @param jsonString
     * @param field
     * @param defaults
     * @return
     */
    public static int getParameter(String jsonString, String field, int defaults){
        if(StringUtils.isNotBlank(jsonString) && !"null".equals(jsonString)){
            JSONObject jsonObject = JSON.parseObject(jsonString);
            String fieldValue = jsonObject.getString(field);
            if(StringUtils.isNotBlank(fieldValue)){
                try {
                    return Integer.valueOf(fieldValue);
                }catch (Exception e){
                    e.printStackTrace();
                    return defaults;
                }
            }else {
                return defaults;
            }
        }
        return defaults;
    }

    /**
     * 获取json字符串中field数值，没有值则为默认值
     * @param jsonString
     * @param field
     * @param defaults
     * @return
     */
    public static String getParameter(String jsonString, String field, String defaults){
        if(StringUtils.isNotBlank(jsonString) && !"null".equals(jsonString)){
            JSONObject jsonObject = JSON.parseObject(jsonString);
            String fieldValue = jsonObject.getString(field);
            if(StringUtils.isNotBlank(fieldValue)){
                return fieldValue;
            }else {
                return defaults;
            }
        }
        return defaults;
    }

    /**
     * 获取json字符串中field数值，没有值则为默认值
     * @param jsonString
     * @param field
     * @param defaults
     * @return
     */
    public static Double getParameter(String jsonString, String field, Double defaults){
        if(StringUtils.isNotBlank(jsonString) && !"null".equals(jsonString)){
            JSONObject jsonObject = JSON.parseObject(jsonString);
            String fieldValue = jsonObject.getString(field);
            if(StringUtils.isNotBlank(fieldValue)){
                try {
                    return Double.valueOf(fieldValue);
                }catch (Exception e){
                    e.printStackTrace();
                    return defaults;
                }
            }else {
                return defaults;
            }
        }
        return defaults;
    }

    /**
     * 获取json字符串中field数值，没有值则为默认值
     * @param jsonString
     * @param field
     * @param defaults
     * @return
     */
    public static Boolean getParameter(String jsonString, String field, boolean defaults){
        if(StringUtils.isNotBlank(jsonString) && !"null".equals(jsonString)){
            JSONObject jsonObject = JSON.parseObject(jsonString);
            String fieldValue = jsonObject.getString(field);
            if(StringUtils.isNotBlank(fieldValue) && "true".equals(fieldValue)){
                return true;
            }else if(StringUtils.isNotBlank(fieldValue) && "false".equals(fieldValue)){
                return false;
            }else {
                return defaults;
            }
        }
        return defaults;
    }

    /**
     * 获取json字符串中field数值，没有值则为默认值
     * @param jsonString
     * @param field
     * @param defaults
     * @return
     */
    public static int[] getParameter(String jsonString, String field, int[] defaults){
        if(StringUtils.isNotBlank(jsonString) && !"null".equals(jsonString)){
            JSONObject jsonObject = JSON.parseObject(jsonString);
            String fieldValue = jsonObject.getString(field);
            if(StringUtils.isNotBlank(fieldValue)){
                try {
                    try {
                        String[] split = fieldValue.split(",");
                        List<String> list = Arrays.asList(split);
                        List<Integer> integerList = new ArrayList<>();
                        list.forEach(numStr -> {
                            integerList.add(Integer.valueOf(numStr));
                        });
                        return ArrayUtils.toPrimitive(integerList.toArray(new Integer[0]));
                    }catch (Exception e){
                        e.printStackTrace();
                        return defaults;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    return defaults;
                }
            }else {
                return defaults;
            }
        }
        return defaults;
    }

    /**
     * 获取json字符串中field数值，没有值则为默认值
     * @param jsonString
     * @param field
     * @param defaults
     * @return
     */
    public static String getParameter(String jsonString, String field, com.alibaba.fastjson.JSONArray defaults){
        if(StringUtils.isNotBlank(jsonString) && !"null".equals(jsonString)){
            JSONObject jsonObject = JSON.parseObject(jsonString);
            com.alibaba.fastjson.JSONArray jsonArray = jsonObject.getJSONArray(field);
            StringBuilder sb = new StringBuilder();
            if(jsonArray != null && jsonArray.size() > 0){
                jsonArray.forEach(jsonEle -> {
                    sb.append(jsonEle).append(",");
                });
                return sb.toString().substring(0, sb.toString().length()-1);
            }
            return null;

        }
        return null;
    }

    /**
     * 从json数据中获得Double数组
     * @param jsonString
     * @param field
     * @return
     */
    public static Double[] getDoubleArrayFromJson(String jsonString, String field){
        JSONObject object = JSON.parseObject(jsonString);
        com.alibaba.fastjson.JSONArray jsonArray = object.getJSONArray(field);
        if(jsonArray != null && jsonArray.size() > 0){
            Object[] objectArray = JSONObject.parseArray(jsonArray.toJSONString(), double.class).toArray();
            return Arrays.copyOf(objectArray, objectArray.length, Double[].class);
        }
        return null;

    }

}
