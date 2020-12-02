package top.fulsun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.fulsun.entity.Task;
import top.fulsun.mapper.TaskMapper;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fulsun
 * @title: TaskCategory
 * @projectName springbootdemo
 * @description: TODO
 * @date 2020/11/3014:03:18
 */
@Controller
@RequestMapping("/task")
public class TaskCategory {
    @Autowired
    TaskMapper taskMapper;

    @PutMapping("/update")
    @ResponseBody
    public Map updateTaskDesc(Integer id, String description){
        Task task = new Task();
        task.setId(id);
        task.setDescription(description);
        int i = taskMapper.updateByPrimaryKeySelective(task);
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", i);
        return map;
    }

    @PostMapping("/updateTask")
    // public String updateTask(@RequestBody Task task){
    public String updateTask(Integer id, String name, @RequestParam("categoryId") Integer cid){
        Task task = new Task();
        task.setId(id);
        task.setName(name);
        taskMapper.updateByPrimaryKeySelective(task);
        //重定向到主页
        return "redirect:/category/"+cid;
    }

    @ResponseBody
    @DeleteMapping("/del/{id}")
    public HashMap<String, Object> delTaskById(@PathVariable("id") int id){
        // int i = taskMapper.deleteByPrimaryKey(id);
        Task record = new Task();
        record.setId(id);
        record.setRun((byte) 1);
        int i = taskMapper.updateByPrimaryKeySelective(record);
        String res= i != 0 ? "删除成功" : "删除失败";
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", res);
        return map;
    }

    @ResponseBody
    @PostMapping("/add")
    public HashMap<String, Object> addTask(@RequestParam Map<String, Object> params ){
        Task task = new Task();
        task.setCreateTime(LocalDateTime.now());
        task.setRun((byte)0);
        String categoryId = params.get("categoryId").toString();
        task.setCategoryId(Integer.valueOf(categoryId));
        task.setName((String) params.get("name"));
        task.setDescription((String) params.get("description"));
        int i = taskMapper.insertSelective(task);
        String res= i != 0 ? "添加成功" : "添加失败";
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", res);
        return map;
    }

}
