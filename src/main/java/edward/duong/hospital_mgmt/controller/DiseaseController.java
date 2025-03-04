package edward.duong.hospital_mgmt.controller;

import edward.duong.hospital_mgmt.controller.mapper.DiseaseMapper;
import edward.duong.hospital_mgmt.controller.models.disease.DiseaseReq;
import edward.duong.hospital_mgmt.controller.models.disease.DiseaseRes;
import edward.duong.hospital_mgmt.domain.models.Pagination;
import edward.duong.hospital_mgmt.service.DiseaseService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/diseases")
public class DiseaseController {
    private final DiseaseService diseaseService;

    public DiseaseController(DiseaseService diseaseService) {
        this.diseaseService = diseaseService;
    }

    @GetMapping
    public List<DiseaseRes> getDiseases(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        page = page < 0 ? 0 : page;
        size = size < 0 ? 10 : size;

        return DiseaseMapper.INSTANCE.toResponseList(diseaseService.getDiseases(
                Pagination.builder().page(page).size(size).build()));
    }

    @PostMapping
    public DiseaseRes createDisease(@RequestBody DiseaseReq req) {
        return DiseaseMapper.INSTANCE.toResponse(diseaseService.createDisease(DiseaseMapper.INSTANCE.toModel(req)));
    }

    @PutMapping
    public DiseaseRes updateDisease(@RequestBody DiseaseReq req) {
        return DiseaseMapper.INSTANCE.toResponse(diseaseService.updateDisease(DiseaseMapper.INSTANCE.toModel(req)));
    }

    @DeleteMapping("/{id}")
    public void deleteDisease(@PathVariable String id) {
        diseaseService.deleteDisease(id);
    }
}
