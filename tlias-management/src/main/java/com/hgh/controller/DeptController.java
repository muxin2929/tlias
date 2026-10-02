package com.hgh.controller;

import com.hgh.anno.Log;
import com.hgh.pojo.Dept;
import com.hgh.pojo.Result;
import com.hgh.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//上传Git仓库
@Slf4j
@RequestMapping("/depts")
@RestController
public class DeptController {
    //private static final Logger log= LoggerFactory.getLogger(DeptController.class);

    @Autowired
    private DeptService deptService;

    /*=========查询部门========*/

    //@RequestMapping(value="/depts",method = RequestMethod.GET)
    @GetMapping
    public Result list(){
        //System.out.println("查询全部部门");
        log.info("查询全部部门");
        List<Dept> list = deptService.findAll();
        return Result.success(list);
    }

    //根据id查询部门
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id){
        log.info("查询部门{}", id);
        Dept dept = deptService.getById(id);
        return Result.success(dept);
    }

    /*==========删除部门=========*/

    //方法一：HttpServletRequest获取请求参数
    /*@DeleteMapping
    public Result delete(HttpServletRequest request){
        String idStr = request.getParameter("id");
        int id = Integer.parseInt(idStr);
        log.info("删除部门{}", id);
        return Result.success();
    }*/

    //方法二：@RequestBody获取请求体参数
    //一旦使用了@RequestParam，就必须传递id参数，否则会报错（默认required是true）
    /*@DeleteMapping
    public Result delete(@RequestParam(value = "id",required = false) Integer deptId){
        log.info("删除部门{}", deptId);
        return Result.success();
    }*/

    //方法三：省略@RequestParam注解
    //前端传递的请求参数与服务器端方法的参数名必须一致[推荐]
    @Log
    @DeleteMapping
    public Result delete(Integer id){
        log.info("删除部门{}", id);
        deptService.deleteById(id);
        return Result.success();
    }

    /*==========新增部门=========*/

    @Log
    @PostMapping
    public Result add(@RequestBody Dept dept){
        log.info("新增部门{}", dept);
        deptService.add(dept);
        return Result.success();
    }

    /*==========修改部门=========*/

    @Log
    @PutMapping
    public Result update(@RequestBody Dept dept){
        log.info("修改部门{}", dept);
        deptService.update(dept);
        return Result.success();
    }
}