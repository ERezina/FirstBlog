package com.personal.diplom.Servise;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.diplom.api.response.TagsResponse;
import com.personal.diplom.api.response.TagsWeightResponse;
import com.personal.diplom.model.CountTags;
import com.personal.diplom.model.Tag;
import com.personal.diplom.repository.PostRepository;
import com.personal.diplom.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.Map;

@Service
public class TagServise {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private PostRepository postRepository;

    public TagsResponse getTagsWeight(String query){
        TagsResponse tagsResponse = new TagsResponse();
       // Iterable<?> tags = tagRepository.findTags();
        Collection<CountTags> tags = tagRepository.findTags(query);
        Integer countPost = postRepository.countPost();
        Double dWeight;
        int countTag = 0;
        Double k = null;

        try {
            for (CountTags co :tags){
                countTag++;

                TagsWeightResponse tagsWeightResponse = new TagsWeightResponse();
                dWeight = co.getWeight()/countPost;
                if (countTag == 1){ //у самого часто встречающегося найдём коэф к
                    k = 1/dWeight;
                    dWeight = 1.0;
                }else {
                    dWeight = k*dWeight;
                }
                tagsWeightResponse.setWeight(dWeight);
                tagsWeightResponse.setName(co.getName());
                tagsResponse.addTag(tagsWeightResponse);
            }
        } catch (Exception ex){
            System.out.println("*********ОШИБКА!!!!!!!!!!!!!**************************");
        }
      return tagsResponse;
    }
}
