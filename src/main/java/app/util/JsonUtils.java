package app.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

public class JsonUtils {

    public static String obj2json(Object obj){
        return JSON.toJSONString(obj);
    }

    public static Map<String,Object> obj2Map(Object obj){
        Object jsonObject = JSON.toJSON(obj);
        return jsonObject instanceof JSONObject ? ((JSONObject)jsonObject).getInnerMap() : null;
    }

    public static <T> List<T> json2List(String jsonStr, Class<T> cls){
        return JSON.parseArray(jsonStr,cls);
    }

    public static <T> T json2obj(String jsonStr,Class<T> cls){
        return JSON.parseObject(jsonStr,cls);
    }

    public static Map json2Map(String jsonStr){
        return JSON.parseObject(jsonStr,Map.class);
    }

}
