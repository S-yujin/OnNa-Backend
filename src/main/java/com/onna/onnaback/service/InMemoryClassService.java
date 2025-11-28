package com.onna.onnaback.service;

import com.onna.onnaback.domain.OneDayClass;
import com.onna.onnaback.dto.CreateClassRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * 현재는 DB 없이 사용하는 "임시 인메모리 구현체".
 * - ClassService 인터페이스를 구현하고 있고
 * - 나중에 JPA + DB를 도입하면 이 클래스는 삭제하거나
 *   @Profile("local") 등으로 로컬 테스트용으로만 남길 예정.
 *
 * 나중에 DB 붙일 때:
 * 1) OneDayClass 엔티티 + OneDayClassRepository(JpaRepository) 추가
 * 2) InMemoryClassService 대신 JpaClassService 같은 구현체를 만들어서
 *    ClassService를 구현하도록 수정하면 됨.
 */
@Service
public class InMemoryClassService implements ClassService {

    /**
     * ⚠ [임시 저장소]
     * 지금은 DB가 없어서 메모리(Map)에만 저장함.
     * 나중에 DB 붙이면 이 두 필드는 삭제하고
     * Repository를 주입받는 방식으로 변경 예정.
     *
     * 예)
     *  private final OneDayClassRepository repository;
     *
     *  public JpaClassService(OneDayClassRepository repository) {
     *      this.repository = repository;
     *  }
     */
    private final Map<Long, OneDayClass> classes = new HashMap<>();
    private final AtomicLong seq = new AtomicLong(0);

    public InMemoryClassService() {
        // TODO: DB 붙인 후에는 이 생성자에서 더미 데이터 넣는 부분은 전부 삭제

        // 테스트용 더미 데이터 하나
        OneDayClass c = new OneDayClass();
        c.setId(seq.incrementAndGet());
        c.setTitle("집밥 김치찌개 배우기");
        c.setDescription("수영구에서 여유롭게 집밥 배우는 원데이 클래스");
        c.setCategory("요리");
        c.setLocation("부산 수영구 망미동");
        c.setDate(LocalDate.now().plusDays(7));
        c.setStartTime(LocalTime.of(14, 0));
        c.setEndTime(LocalTime.of(16, 0));
        c.setCapacity(5);
        c.setPrice(0);
        c.setHostId(1L);

        classes.put(c.getId(), c);
    }

    @Override
    public List<OneDayClass> getClasses(String region, String category) {
        /**
         * 현재 구현:
         *  - 메모리에 있는 Map에서 필터링
         *
         * 나중에 DB 붙일 때 예상 코드 (예시):
         *  - repository에서 조건에 맞게 조회하거나
         *  - Query Method / @Query 사용
         *
         * 예)
         *  return repository.findByRegionAndCategory(region, category)
         *          .stream()
         *          .map(entity -> entity) // 또는 DTO로 변환
         *          .toList();
         */
        return classes.values().stream()
                .filter(c -> region == null || region.isBlank() || c.getLocation().contains(region))
                .filter(c -> category == null || category.isBlank() || c.getCategory().contains(category))
                .sorted(Comparator.comparing(OneDayClass::getDate))
                .collect(Collectors.toList());
    }

    @Override
    public OneDayClass getClassDetail(Long classId) {
        /**
         * 현재 구현:
         *  - Map에서 id로 가져옴
         *
         * 나중에 DB 붙일 때 예상 코드 (예시):
         *
         *  return repository.findById(classId)
         *          .orElseThrow(() -> new NoSuchElementException("Class not found: " + classId));
         */
        OneDayClass c = classes.get(classId);
        if (c == null) throw new NoSuchElementException("Class not found: " + classId);
        return c;
    }

    @Override
    public OneDayClass createClass(CreateClassRequest req) {
        /**
         * 현재 구현:
         *  - 요청값으로 OneDayClass 객체 만들어서 Map에 저장
         *
         * 나중에 DB 붙일 때 예상 코드 (예시):
         *
         *  OneDayClassEntity entity = new OneDayClassEntity();
         *  entity.setTitle(req.getTitle());
         *  ...
         *  OneDayClassEntity saved = repository.save(entity);
         *  return saved; // 또는 엔티티를 도메인/DTO로 변환해서 반환
         */

        OneDayClass c = new OneDayClass();
        c.setId(seq.incrementAndGet());
        c.setTitle(req.getTitle());
        c.setDescription(req.getDescription());
        c.setCategory(req.getCategory());
        c.setLocation(req.getLocation());
        c.setDate(LocalDate.parse(req.getDate()));           // "yyyy-MM-dd"
        c.setStartTime(LocalTime.parse(req.getStartTime())); // "HH:mm"
        c.setEndTime(LocalTime.parse(req.getEndTime()));
        c.setCapacity(req.getCapacity());
        c.setPrice(req.getPrice());
        c.setHostId(req.getHostId());

        classes.put(c.getId(), c);
        return c;
    }
}
