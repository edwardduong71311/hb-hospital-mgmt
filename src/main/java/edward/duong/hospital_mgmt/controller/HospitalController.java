package edward.duong.hospital_mgmt.controller;

import edward.duong.hospital_mgmt.controller.mapper.HospitalMapper;
import edward.duong.hospital_mgmt.controller.models.hospitals.HospitalReq;
import edward.duong.hospital_mgmt.controller.models.hospitals.HospitalRes;
import edward.duong.hospital_mgmt.domain.models.Pagination;
import edward.duong.hospital_mgmt.service.HospitalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/hospitals")
@Slf4j
public class HospitalController {
    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @GetMapping("/{id}")
    public HospitalRes getHospitalById(@PathVariable String id) {
        return HospitalMapper.INSTANCE.toResponse(hospitalService.getHospitalById(id));
    }

    @GetMapping
    public List<HospitalRes> getHospitals(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        page = page < 0 ? 0 : page;
        size = size < 0 ? 10 : size;

        return HospitalMapper.INSTANCE.toResponseList(hospitalService.getHospital(
                Pagination.builder().page(page).size(size).build()
        ));
    }

    @PostMapping
    public HospitalRes createHospital(@RequestBody HospitalReq hospital) {
        return HospitalMapper.INSTANCE.toResponse(
                hospitalService.createHospital(HospitalMapper.INSTANCE.toModel(hospital)));
    }

    @PutMapping
    public HospitalRes updateHospital(@RequestBody HospitalReq hospital) {
        return HospitalMapper.INSTANCE.toResponse(
                hospitalService.updateHospital(HospitalMapper.INSTANCE.toModel(hospital))
        );
    }
}
