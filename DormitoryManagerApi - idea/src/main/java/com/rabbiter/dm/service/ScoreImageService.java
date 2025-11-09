package com.rabbiter.dm.service;

import com.rabbiter.dm.config.Constant;
import com.rabbiter.dm.dao.ScoreImageMapper;
import com.rabbiter.dm.entity.ScoreImage;
import com.rabbiter.dm.exception.HttpException;
import com.rabbiter.dm.utils.HttpCode;
import com.rabbiter.dm.utils.MD5Util;
import com.rabbiter.dm.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class ScoreImageService {

    @Autowired
    private ScoreImageMapper scoreImageMapper;

    public String selectFileName(long score_id){
        return scoreImageMapper.selectByScoreId(score_id);
    }

    public Result<?> uploadFile(MultipartFile file,long room_id){
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        if (file!=null){
            try {
                String md5 = MD5Util.getFileMD5(file.getInputStream());
                if (md5 == null) {
                    throw new HttpException(HttpCode.FAILED, "上传失败");
                }
                // 确认是否存在文件夹，不存在则创建
                File folder = new File(Constant.UPLOAD_PATH);
                if  (!folder.exists()) {
                    folder.mkdir();
                }
//                校验MD5防止重复图片上传
                List<ScoreImage> list = scoreImageMapper.selectByMd5(md5);
                if (list.size() == 0) {
                    file.transferTo(new File(Constant.UPLOAD_PATH, fileName));
                    ScoreImage image = new ScoreImage();
                    image.setScore_id(room_id);
                    image.setOriginal_name(file.getOriginalFilename());
                    image.setFile_name(fileName);
                    image.setMd5(md5);
                    scoreImageMapper.save(image);
                } else {
                    throw new HttpException(HttpCode.FAILED,"图片重复上传失败");
                }

            } catch (IOException e) {
                throw new HttpException(HttpCode.FAILED, "上传失败");
            }
        }else {
            throw new HttpException(HttpCode.FAILED, "上传失败");
        }
        return Result.ok("上传成功");
    }

    public List<ScoreImage> selectByMd5(String md5) {
        return scoreImageMapper.selectByMd5(md5);
    }
}
