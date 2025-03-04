package edward.duong.hospital_mgmt.controller;

import edward.duong.hospital_mgmt.controller.mapper.SpecialtyMapper;
import edward.duong.hospital_mgmt.controller.models.specialty.SpecialtyReq;
import edward.duong.hospital_mgmt.controller.models.specialty.SpecialtyRes;
import edward.duong.hospital_mgmt.domain.models.Pagination;
import edward.duong.hospital_mgmt.domain.models.spec.SpecialtyCriteria;
import edward.duong.hospital_mgmt.service.SpecialtyService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/specialties")
public class SpecialtyController {
    private final SpecialtyService specialtyService;

    public SpecialtyController(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @GetMapping
    public List<SpecialtyRes> getSpecialties(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam(value = "name", required = false) String name) {
        Pagination pagination = Pagination.builder()
                .page(page < 0 ? 0 : page)
                .size(size < 0 ? 10 : size)
                .build();
        SpecialtyCriteria criteria = SpecialtyCriteria.builder().name(name).build();

        return SpecialtyMapper.INSTANCE.toResponseList(specialtyService.getSpecialties(criteria, pagination));
    }

    @PostMapping
    public SpecialtyRes createSpecialty(@RequestBody SpecialtyReq req) {
        return SpecialtyMapper.INSTANCE.toResponse(
                specialtyService.createSpecialty(SpecialtyMapper.INSTANCE.toModel(req)));
    }

    @PutMapping
    public SpecialtyRes updateSpecialty(@RequestBody SpecialtyReq req) {
        return SpecialtyMapper.INSTANCE.toResponse(
                specialtyService.updateSpecialty(SpecialtyMapper.INSTANCE.toModel(req)));
    }

    @DeleteMapping("/{id}")
    public void deleteSpecialty(@PathVariable String id) {
        specialtyService.deleteSpecialty(id);
    }
}
