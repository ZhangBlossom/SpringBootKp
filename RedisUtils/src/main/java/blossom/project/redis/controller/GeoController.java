package blossom.project.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.domain.geo.Metrics;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 张锦标
 * @date: 2023/9/7 16:40
 * GeoController类
 */
@RestController
public class GeoController {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/radiusByxy")
    public GeoResults radisByxy(){
        Circle circle = new Circle(113.9410499639,22.5461508801,
                Metrics.KILOMETERS.getMultiplier());
        RedisGeoCommands.GeoRadiusCommandArgs args =
                RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                        .includeCoordinates().sortAscending().limit(50);
        GeoResults<RedisGeoCommands.GeoLocation<String>> geoResults
                =this.redisTemplate.opsForGeo().radius("geo:key",circle,args);
        return geoResults;
    }

    @GetMapping("/radiusByMember")
    public GeoResults radisByMember(){
        String member = "世界之窗";
        RedisGeoCommands.GeoRadiusCommandArgs args =
                RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                        .includeDistance().sortAscending().limit(50);
        Distance distance = new Distance(10,Metrics.KILOMETERS);
        GeoResults<RedisGeoCommands.GeoLocation<String>> geoResults
                =this.redisTemplate.opsForGeo().radius("geo:key",member,distance,args);
        return geoResults;
    }
}
