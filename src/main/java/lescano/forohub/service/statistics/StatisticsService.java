package lescano.forohub.service.statistics;

import lescano.forohub.dto.statistics.CountDTO;
import lescano.forohub.repository.StatisticsRepository;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {

    private final StatisticsRepository statisticsRepository;

    public StatisticsService(StatisticsRepository statisticsRepository){
        this.statisticsRepository=statisticsRepository;
    }

    public CountDTO coutEntities() {
        Long categoryCount = statisticsRepository.countCategories();
        Long subCategoryCount = statisticsRepository.countSubCategories();
        Long courseCount = statisticsRepository.countCourses();
        Long topicCount = statisticsRepository.countTopics();
        Long responseCount = statisticsRepository.countResponses();
        return  new CountDTO(categoryCount,subCategoryCount,courseCount,topicCount,responseCount);
    }
}
