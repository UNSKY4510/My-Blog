package com.unsky.myblog.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author UNSKY
 * @date 2022年4月14日 00:03
 */
@Component
public class RedisUtil {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /*---------------------------------key相关操作-----------------------------*/

    /**
     * @Description: 设置键失效的时间
     * @Param: key Redis 键
     * @Param: time 时间
     * @Param: timeUnit 时间单位
     * @return: true=设置成功  false=设置失败
     * @author: UNSKY
     * @date: 2022年4月14日
     */
    public boolean expire(String key, long time, TimeUnit timeUnit) {
        try {
            if (time >= 0) {
                redisTemplate.expire(key, time, timeUnit);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @Description: 获取 key 的过期时间
     * @Param: key Redis 键
     * @return: 时间（秒）  返回0代表永久有效
     * @author: UNSKY
     * @date: 2022年4月14日
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * @Description: 判断 key 是否存在
     * @Param: key Redis 键
     * @return: true=存在  false=不存在
     * @author: UNSKY
     * @date: 2022年4月14日
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @Description: 删除 key
     * @Param: key 可以传一个或多个
     * @author: UNSKY
     * @date: 2022年4月14日
     */
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(key));
            }
        }
    }

    /*========================================String=============================================*/
    /**
    * @Description:  获取 key 的值
    * @Param: key Redis 键
    * @return: 值
    * @author: UNSKY
    * @date: 2022年4月14日
    */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
    * @Description:  String 类型添加缓存
    * @Param: key Redis 键
    * @Param: value 值
    * @return: true=添加成功 false=添加失败
    * @author: UNSKY
    * @date: 2022年4月14日
    */
    public boolean set(String key, Object value){
        try {
            redisTemplate.opsForValue().set(key,value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
    * @Description:  添加缓存并设置过期时间
    * @Param: key Redis 键
    * @Param: value 值
    * @Param: timeOut 过期的时间
    * @Param: timeUnit 时间单位
    * @return: true=添加成功 false=添加失败
    * @author: UNSKY
    * @date: 2022年4月14日
    */
    public boolean set(String key, Object value, long timeOut, TimeUnit timeUnit){
        try {
            if (timeOut > 0){
                redisTemplate.opsForValue().set(key, value, timeOut, timeUnit);
            }else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
    * @Description:  值加一
    * @Param: key Redis 键
    * @return: 返回加一后的值
    * @author: UNSKY
    * @date: 2022年4月14日
    */
    public long incr(String key){
        return redisTemplate.opsForValue().increment(key);
    }

    /**
    * @Description:  值加上 delta
    * @Param: key Redis 键
    * @Param: delta 增加的值
    * @return: 返回增加后的值
    * @author: UNSKY
    * @date: 2022年4月14日
    */
    public long incrby(String key,long delta){
        if (delta < 0){
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key,delta);
    }

    /**
     * @Description:  值减一
     * @Param: key Redis 键
     * @return: 返回减一后的值
     * @author: UNSKY
     * @date: 2022年4月14日
     */
    public long decr(String key){
        return redisTemplate.opsForValue().decrement(key);
    }

    /**
     * @Description:  值减去 delta
     * @Param: key Redis 键
     * @Param: delta 减去的值
     * @return: 返回减去后的值
     * @author: UNSKY
     * @date: 2022年4月14日
     */
    public long decrby(String key,long delta){
        if (delta < 0){
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().decrement(key,delta);
    }

    /*======================================Hash============================================*/
    /**
    * @Description:  获取存储在哈希表中指定字段的值
    * @Param: key Redis 键
    * @Param: field 指定的字段
    * @return: 返回值
    * @author: UNSKY
    * @date: 2022年4月14日
    */
    public Object hget(String key, String field){
        return redisTemplate.opsForHash().get(key,field);
    }

    /**
    * @Description:  获取hashKey对应的所有键值
    * @Param: key Redis 键
    * @return: 获取key对应的所有键值
    * @author: UNSKY
    * @date: 2022年4月14日
    */
    public Map<Object, Object> hmget(String key){
        return redisTemplate.opsForHash().entries(key);
    }

    /**
    * @Description:  同时将多个 field-value (域-值)对设置到哈希表 key 中
    * @Param: key Redis 键
    * @Param: map (域-值)对
    * @return: true=设置成功 false=设置失败
    * @author: UNSKY
    * @date: 2022年4月14日
    */
    public boolean hmset(String key, Map<String, Object> map){
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
    * @Description:  同时将多个 field-value (域-值)对设置到哈希表 key 中,并设置过期时间
    * @Param: key Redis 键
    * @Param: map (域-值)对
    * @Param: timeOut 过期时间
    * @Param: timeUnit 时间单位
    * @return: true=设置成功 false=设置失败
    * @author: UNSKY
    * @date: 2022年4月14日
    */
    public boolean hmset(String key, Map<String, Object> map, long timeOut, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (timeOut > 0) {
                expire(key, timeOut, timeUnit);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
    * @Description: 将哈希表 key 中的字段 field 的值设为 value
    * @Param: key Redis键
    * @Param: field 字段
    * @Param: value 字段的值
    * @return: true=设置成功 false=设置失败
    * @author: UNSKY
    * @date: 2022年4月14日
    */
    public boolean hset(String key, String field, Object value){
        try {
            redisTemplate.opsForHash().put(key, field, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
    * @Description: 将哈希表 key 中的字段 field 的值设为 value,并且设置过期时间
    * @Param: key Redis键
    * @Param: field 字段
    * @Param: value 字段的值
    * @Param: timeOut 过期时间 注意:如果已存在的hash表有时间,这里将会替换原有的时间
    * @Param: timeUnit 时间单位
    * @return: true=设置成功 false=设置失败
    * @author: UNSKY
    * @date: 2022年4月14日
    */
    public boolean hset(String key, String field, Object value, long timeOut, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForHash().put(key, field, value);
            if (timeOut > 0) {
                expire(key, timeOut, timeUnit);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
    * @Description: 删除一个或多个哈希表字段
    * @Param: key Redis 键
    * @Param: field 字段
    * @author: UNSKY
    * @date: 2022年4月14日
    */
    public void hdel(String key, Object... field) {
        redisTemplate.opsForHash().delete(key, field);
    }

    /**
    * @Description: 为哈希表 key 中的指定字段的整数值加上增量
    * @Param: key Redis 键
    * @Param: field 字段
    * @Param: delta 增量>0
    * @return: 增加后的值
    * @author: UNSKY
    * @date: 2022年4月14日
    */
    public long hincrby(String key, String field, long delta){
        return redisTemplate.opsForHash().increment(key, field, delta);
    }

    /**
     * @Description: 为哈希表 key 中的指定字段的浮点数值加上增量
     * @Param: key Redis 键
     * @Param: field 字段
     * @Param: delta 增量>0
     * @return: 增加后的值
     * @author: UNSKY
     * @date: 2022年4月14日
     */
    public double hincrbydouble(String key, String field, double delta){
        return redisTemplate.opsForHash().increment(key, field, delta);
    }

    /**
     * @Description: 为哈希表 key 中的指定字段的整数值减去增量
     * @Param: key Redis 键
     * @Param: field 字段
     * @Param: delta 增量>0
     * @return: 减少后的值
     * @author: UNSKY
     * @date: 2022年4月14日
     */
    public long hdecrby(String key, String field, long delta){
        return redisTemplate.opsForHash().increment(key, field, -delta);
    }

    /**
     * @Description: 为哈希表 key 中的指定字段的浮点数值减去增量
     * @Param: key Redis 键
     * @Param: field 字段
     * @Param: delta 增量>0
     * @return: 减少后的值
     * @author: UNSKY
     * @date: 2022年4月14日
     */
    public double hdecrbydouble(String key, String field, double delta){
        return redisTemplate.opsForHash().increment(key, field, -delta);
    }

    /*===============================Set====================================*/

    /**
    * @Description: 返回集合中的所有成员
    * @Param: key Redis 键
    * @return: 返回成员
    * @author: UNSKY
    * @date: 2022年4月14日
    */
    public Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
    * @Description:  判断 member 元素是否是集合 key 的成员
    * @Param: key Redis 键
    * @Param: value 元素
    * @return: true=是  false=不是
    * @author: UNSKY
    * @date: 2022年4月14日
    */
    public boolean sHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
    * @Description: 向集合添加一个或多个成员
    * @Param: key Redis键
    * @Param: members 成员
    * @return: 成功插入成员的个数
    * @author: UNSKY
    * @date: 2022年4月14日
    */
    public long sSet(String key, Object... members) {
        try {
            return redisTemplate.opsForSet().add(key, members);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
    * @Description:  向集合添加一个或多个成员,并且设置过期时间
    * @Param: key Redis键
    * @Param: timeOut 过期时间
    * @Param: timeUnit 时间单位
    * @Param: members 成员
    * @return: 成功插入成员的个数
    * @author: UNSKY
    * @date: 2022年4月14日
    */
    public long sSetAndTime(String key,long timeOut, TimeUnit timeUnit, Object... members) {
        try {
            Long count = redisTemplate.opsForSet().add(key, timeOut, timeUnit, members);
            if (timeOut > 0){
                expire(key, timeOut,timeUnit);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
    * @Description:  获取集合的成员数
    * @Param: key Redis 键
    * @return: 成员数
    * @author: UNSKY
    * @date: 2022年4月14日
    */
    public long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
    * @Description:  移除集合中一个或多个成员
    * @Param: key Redis 键
    * @Param: members 成员
    * @return: 移除成员的个数
    * @author: UNSKY
    * @date: 2022年4月14日
    */
    public long setRemove(String key, Object... members) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, members);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /*===================================List=========================================*/

    /**
    * @Description: 将一个值插入到列表头部
    * @Param: key Redis 键
    * @Param: value 插入的值
    * @return: true=插入成功 false=插入失败
    * @author: UNSKY
    * @date: 2022年4月14日
    */
    public boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().leftPush(key,value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
    * @Description:  将一个值插入到列表头部，并设置过期时间
    * @Param: key Redis 键
    * @Param: value 插入的值
    * @Param: timeOut 过期时间
    * @Param: timeUnit 时间单位
    * @return: true=插入成功 false=插入失败
    * @author: UNSKY
    * @date: 2022年4月14日
    */
    public boolean lSet(String key,Object value, long timeOut, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForList().leftPush(key, value);
            if (timeOut > 0){
                expire(key, timeOut, timeUnit);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
    * @Description:  将多个值插入到列表头部
    * @Param: key Redis 键
    * @Param: values 多个值
    * @return: true=插入成功 false=插入失败
    * @author: UNSKY
    * @date: 2022年4月14日
    */
    public boolean lSet(String key, List<Object> values) {
        try {
            redisTemplate.opsForList().leftPushAll(key, values);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @Description:  将多个值插入到列表头部，并设置过期时间
     * @Param: key Redis 键
     * @Param: values 插入的值
     * @Param: timeOut 过期时间
     * @Param: timeUnit 时间单位
     * @return: true=插入成功 false=插入失败
     * @author: UNSKY
     * @date: 2022年4月14日
     */
    public boolean lSet(String key,List<Object> values, long timeOut, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForList().leftPush(key, values);
            if (timeOut > 0){
                expire(key, timeOut, timeUnit);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
    * @Description:  获取列表指定范围内的元素
    * @Param: key Redis 键
    * @Param: start 开始位置
    * @Param: end 结束位置   0 -1 代表所有元素
    * @return: 列表元素
    * @author: UNSKY
    * @date: 2022年4月14日
    */
    public List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
    * @Description:  获取列表长度
    * @Param: key Redis 键
    * @return: 列表长度
    * @author: UNSKY
    * @date: 2022年4月14日
    */
    public long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
    * @Description: 通过索引获取列表中的元素
    * @Param: key Rdis 键
    * @Param: index 索引
    * @return: 返回指定索引的元素
    * @author: UNSKY
    * @date: 2022年4月14日
    */
    public Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
    * @Description:  根据索引修改list中的某条数据
    * @Param: key Redis 键
    * @Param: index 索引
    * @Param: value 新的值
    * @return: true=修改成功 false=修改失败
    * @author: UNSKY
    * @date: 2022年4月14日
    */
    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
    * @Description: 移除N个值为value
    * @Param: key Redis 键
    * @Param: count 值为value的个数
    * @Param: value 要删除的值
    * @return: 删除的个数
    * @author: UNSKY
    * @date: 2022年4月14日
    */
    public long lRemove(String key, long count, Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    //================================Zset=========================================//
    /**
    * @Description: 添加元素,有序集合是按照元素的score值由小到大排列
    * @author: UNSKY
    * @date: 2022/5/27
    */
    public Boolean zAdd(String key, String value, double score) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
    * @Description: 删除元素
    * @author: UNSKY
    * @date: 2022/5/27
    */
    public Long zRemove(String key, Object... values) {
        return redisTemplate.opsForZSet().remove(key, values);
    }

    /**
    * @Description: 增加元素的score值，并返回增加后的值
    * @author: UNSKY
    * @date: 2022/5/27
    */
    public Double zIncrementScore(String key, String value, double delta) {
        return redisTemplate.opsForZSet().incrementScore(key, value, delta);
    }

    /**
    * @Description:
    * @author: UNSKY
    * @date: 2022/5/27
    */
    public Set<Object> zReverseRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    public Double zScore(String key, Object value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

}
