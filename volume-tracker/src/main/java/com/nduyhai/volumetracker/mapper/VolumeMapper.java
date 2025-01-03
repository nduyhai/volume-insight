package com.nduyhai.volumetracker.mapper;

import com.nduyhai.volumetracker.dto.KeywordSearchVolume;
import com.nduyhai.volumetracker.entity.KeywordVolumeProjection;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface VolumeMapper {

  KeywordSearchVolume toKeywordSearchVolume(KeywordVolumeProjection entity);


  List<KeywordSearchVolume> toKeywordSearchVolumes(List<KeywordVolumeProjection> entities);
}
