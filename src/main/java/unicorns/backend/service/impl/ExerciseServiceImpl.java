package unicorns.backend.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unicorns.backend.dto.request.ExerciseCreateRequest;
import unicorns.backend.dto.response.ExerciseDetailResponse;
import unicorns.backend.dto.response.ExerciseMetaResponse;
import unicorns.backend.dto.request.OptionCreateRequest;
import unicorns.backend.dto.request.QuestionCreateRequest;
import unicorns.backend.dto.response.OptionResponse;
import unicorns.backend.dto.response.QuestionResponse;
import unicorns.backend.entity.Exercise;
import unicorns.backend.entity.QuizOption;
import unicorns.backend.entity.QuizQuestion;
import unicorns.backend.repository.ExerciseRepository;
import unicorns.backend.service.ExerciseService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public ExerciseServiceImpl(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    @Transactional
    public ExerciseMetaResponse create(ExerciseCreateRequest req) {
        if (req == null) throw new IllegalArgumentException("Request rỗng.");
        if (req.getTitle() == null || req.getTitle().trim().isEmpty())
            throw new IllegalArgumentException("Title không được trống.");
        if (req.getQuestions() == null || req.getQuestions().isEmpty())
            throw new IllegalArgumentException("Exercise cần ít nhất 1 câu hỏi.");

        Exercise e = new Exercise();
        e.setTitle(req.getTitle());
        e.setDescription(req.getDescription());

        List<QuizQuestion> qEntities = new ArrayList<>();
        for (QuestionCreateRequest qReq : req.getQuestions()) {
            if (qReq == null) throw new IllegalArgumentException("Question không hợp lệ.");
            if (qReq.getText() == null || qReq.getText().trim().isEmpty())
                throw new IllegalArgumentException("Question.text không được trống.");
            if (qReq.getOptions() == null || qReq.getOptions().size() < 2)
                throw new IllegalArgumentException("Mỗi câu hỏi cần >= 2 phương án.");

            boolean hasCorrect = false;

            QuizQuestion qEntity = new QuizQuestion();
            qEntity.setText(qReq.getText());
            qEntity.setExercise(e);

            List<QuizOption> oEntities = new ArrayList<>();
            for (OptionCreateRequest oReq : qReq.getOptions()) {
                if (oReq == null) throw new IllegalArgumentException("Option không hợp lệ.");
                if (oReq.getText() == null || oReq.getText().trim().isEmpty())
                    throw new IllegalArgumentException("Option.text không được trống.");

                QuizOption oEntity = new QuizOption();
                oEntity.setText(oReq.getText());
                oEntity.setCorrect(oReq.isCorrect());
                oEntity.setQuestion(qEntity);

                if (oReq.isCorrect()) hasCorrect = true;

                oEntities.add(oEntity);
            }

            if (!hasCorrect)
                throw new IllegalArgumentException("Mỗi câu hỏi phải có ít nhất 1 đáp án đúng.");

            qEntity.setOptions(oEntities);
            qEntities.add(qEntity);
        }

        e.setQuestions(qEntities);

        Exercise saved = exerciseRepository.save(e);

        int total = (saved.getQuestions() == null) ? 0 : saved.getQuestions().size();
        return new ExerciseMetaResponse(saved.getId(), saved.getTitle(), saved.getDescription(), total);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExerciseMetaResponse> listAllMetas() {
        List<Exercise> entities = exerciseRepository.findAll();
        List<ExerciseMetaResponse> result = new ArrayList<>();

        for (Exercise e : entities) {
            int total = (e.getQuestions() == null) ? 0 : e.getQuestions().size();
            result.add(new ExerciseMetaResponse(e.getId(), e.getTitle(), e.getDescription(), total));
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public ExerciseDetailResponse getDetail(Long id) {
        Exercise e = exerciseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Exercise not found: " + id));

        List<QuestionResponse> qrs = new ArrayList<>();
        if (e.getQuestions() != null) {
            for (QuizQuestion q : e.getQuestions()) {
                List<OptionResponse> ors = new ArrayList<>();
                if (q.getOptions() != null) {
                    for (QuizOption o : q.getOptions()) {
                        ors.add(new OptionResponse(o.getId(), o.getText(), o.isCorrect()));
                    }
                }
                qrs.add(new QuestionResponse(q.getId(), q.getText(), ors));
            }
        }
        return new ExerciseDetailResponse(e.getId(), e.getTitle(), e.getDescription(), qrs);
    }
}
