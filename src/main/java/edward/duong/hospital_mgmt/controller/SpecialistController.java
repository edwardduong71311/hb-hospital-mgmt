package edward.duong.hospital_mgmt.controller;

import edward.duong.hospital_mgmt.controller.mapper.SpecialistMapper;
import edward.duong.hospital_mgmt.controller.models.specialist.SpecialistReq;
import edward.duong.hospital_mgmt.controller.models.specialist.SpecialistRes;
import edward.duong.hospital_mgmt.domain.models.Pagination;
import edward.duong.hospital_mgmt.service.SpecialistService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/specialists")
public class SpecialistController {
    private final SpecialistService specialistService;

    public SpecialistController(SpecialistService specialistService) {
        this.specialistService = specialistService;
    }

    @GetMapping
    public List<SpecialistRes> getSpecialties(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        page = page < 0 ? 0 : page;
        size = size < 0 ? 10 : size;

        return SpecialistMapper.INSTANCE.toResponseList(specialistService.getSpecialists(
                Pagination.builder().page(page).size(size).build()));
    }

    @PostMapping
    public SpecialistRes createSpecialty(@RequestBody SpecialistReq req) {
        return SpecialistMapper.INSTANCE.toResponse(
                specialistService.createSpecialist(SpecialistMapper.INSTANCE.toModel(req)));
    }

    @PutMapping
    public SpecialistRes updateSpecialty(@RequestBody SpecialistReq req) {
        return SpecialistMapper.INSTANCE.toResponse(
                specialistService.updateSpecialist(SpecialistMapper.INSTANCE.toModel(req)));
    }

    @DeleteMapping("/{id}")
    public void deleteSpecialty(@PathVariable String id) {
        specialistService.deleteSpecialist(id);
    }
}
