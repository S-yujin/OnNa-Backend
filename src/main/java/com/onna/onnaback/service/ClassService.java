package com.onna.onnaback.service;

import com.onna.onnaback.domain.OneDayClass;
import com.onna.onnaback.dto.CreateClassRequest;

import java.util.List;

public interface ClassService {

    List<OneDayClass> getClasses(String region, String category);

    OneDayClass getClassDetail(Long classId);

    OneDayClass createClass(CreateClassRequest request);
}
