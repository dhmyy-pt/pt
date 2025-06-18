package com.ruoyi.pt.mapper;

import com.ruoyi.pt.dao.TorrentInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TorrentInfoMapper {

    @Select("SELECT * FROM torrent_info ")
    List<TorrentInfo> selectAll();

    @Insert("INSERT INTO torrent_info(name, created_time, creator, seeder_count, creator_id, category_id) " +
            "VALUES(#{name}, #{createdTime}, #{creator}, #{seederCount}, #{creatorId}, #{categoryId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(TorrentInfo torrentInfo);

    @Select("SELECT * FROM torrent_info WHERE id = #{id}")
    TorrentInfo selectById(@Param("id") Long id);

    @Select("SELECT * FROM torrent_info WHERE  name LIKE CONCAT('%', #{name}, '%')")
    List<TorrentInfo>  selectByName(@Param("name") String name);

    @Delete("DELETE FROM torrent_info WHERE id = #{id}")
    int deleteById(@Param("id") Long id);

    @Update("UPDATE torrent_info SET name=#{name}, created_time=#{createdTime}, creator=#{creator}, " +
            "seeder_count=#{seederCount}, creator_id=#{creatorId}, category_id=#{categoryId} WHERE id=#{id}")
    int update(TorrentInfo torrentInfo);
    @Select("SELECT * FROM torrent_info WHERE category_id = #{category}")
    List<TorrentInfo> selectByCategory(Integer category);

    @Select("SELECT * FROM torrent_info WHERE  name LIKE CONCAT('%', #{name}, '%') and category_id = #{category}")
    List<TorrentInfo> selectByNameAndCategory(Integer category,String name);

    @Select("SELECT * FROM torrent_info WHERE creator_id = #{creatorId}")
    List<TorrentInfo>  selectOneBycreatorId(@Param("creatorId") String creatorId);

    @Select("SELECT * FROM torrent_info WHERE name = #{name}")
    TorrentInfo  selectOneByName(@Param("name") String name);

}