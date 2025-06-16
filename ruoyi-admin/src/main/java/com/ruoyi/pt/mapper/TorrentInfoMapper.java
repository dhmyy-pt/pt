package com.ruoyi.pt.mapper;

import com.ruoyi.pt.dao.TorrentInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TorrentInfoMapper {

    @Select("SELECT * FROM torrent_info ORDER BY last_active DESC")
    List<TorrentInfo> selectAll();

    @Insert("INSERT INTO torrent_info(name, created_time, creator, seeder_count, last_active, category_id) " +
            "VALUES(#{name}, #{createdTime}, #{creator}, #{seederCount}, #{lastActive}, #{categoryId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(TorrentInfo torrentInfo);

    @Select("SELECT * FROM torrent_info WHERE id = #{id}")
    TorrentInfo selectById(@Param("id") Long id);

    @Delete("DELETE FROM torrent_info WHERE id = #{id}")
    int deleteById(@Param("id") Long id);

    @Update("UPDATE torrent_info SET name=#{name}, created_time=#{createdTime}, creator=#{creator}, " +
            "seeder_count=#{seederCount}, last_active=#{lastActive}, category_id=#{categoryId} WHERE id=#{id}")
    int update(TorrentInfo torrentInfo);
}